package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import bricker.gameobjects.Ball;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {

	// sizes
	private static final int BALL_RADIUS = 20;
	private static final float BALL_SPEED = 150;
	private static final int PADDLE_HEIGHT = 15;
	private static final int PADDLE_WIDTH = 100;
	private static final int PADDLE_DISTANCE = 20;
	private static final int BORDER_WIDTH = 4;
	private static final int BRICK_HEIGHT = 15;
	private static final Integer DEFAULT_BRICK_ROW = 7;
	private static final Integer DEFAULT_BRICK_COLLUMN = 8;

	// Layers
	private static final int BRICK_LAYER = Layer.STATIC_OBJECTS;


	// assets paths
	public static final String BALL_IMG_PATH = "assets/ball.png";
	public static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
	public static final String PADDLE_IMG_PATH = "assets/paddle.png";
	public static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
	public static final String BRICK_IMG_PATH = "assets/brick.png";

	// GUI
	private static final String WINDOW_TITLE = "BRICKER!";
	private static final int WINDOW_X_DIMS = 700;
	private static final int WINDOW_Y_DIMS = 500;
	private static final Color BORDER_COLOR = Color.CYAN;

	// bricks number
	private Integer brickRows;
	private Integer brickColumn;

	// fields
	private Ball ball;
	private WindowController windowController;
	private Vector2 windowDimensions;

	private UserInputListener inputListener;



	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		this(windowTitle, windowDimensions, DEFAULT_BRICK_ROW, DEFAULT_BRICK_COLLUMN);
	}

	public BrickerGameManager(String windowTitle, Vector2 windowDimensions, Integer brickRows, Integer brickColumn) {
		super(windowTitle, windowDimensions);
		this.brickRows = brickRows;
		this.brickColumn = brickColumn;
	}


	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader,
							   UserInputListener inputListener, WindowController windowController)
							   {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.windowController = windowController;
		this.windowDimensions = windowController.getWindowDimensions();
		this.inputListener = inputListener;

		// add ball
		createBall(imageReader, soundReader, windowController);

		//create paddle
		createPaddle(imageReader, this.inputListener, windowDimensions);

		//create borders
		createBorders(windowDimensions);

		//create background
		createBackground(windowDimensions, imageReader);

		//addBricks
		CollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(gameObjects());
		createBricks(windowDimensions, imageReader, basicCollisionStrategy);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		checkForGameEnd();
	}

	private void checkForGameEnd() {
		double ballHeight = ball.getCenter().y();

		String prompt = "";  // TODO: change to update life counter
		if(ballHeight > windowDimensions.y()) {
			prompt = "You Lose!";
		}

		if (Brick.totalNumberOfBricks == 0 || this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
			prompt = "You Win!";
		}
		if(!prompt.isEmpty()) {
			prompt += " Play again?";
			if(windowController.openYesNoDialog(prompt))
				windowController.resetGame();
			else
				windowController.closeWindow();
		}
	}

	private void createBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
		Renderable ballImage =
				imageReader.readImage(BALL_IMG_PATH, true);
		Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);
		this.ball = new Ball(
				Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

		Vector2 windowDimensions = windowController.getWindowDimensions();
		ball.setCenter(windowDimensions.mult(0.5f));
		gameObjects().addGameObject(ball);

		float ballVelX = BALL_SPEED;
		float ballVelY = BALL_SPEED;
		Random rand = new Random();
		if(rand.nextBoolean())
			ballVelX *= -1;
		if(rand.nextBoolean())
			ballVelY *= -1;
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}

	private void createPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
		Renderable paddleImage = imageReader.readImage(
				PADDLE_IMG_PATH, true);
		GameObject paddle = new Paddle(
				Vector2.ZERO,
				new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
				paddleImage,
				inputListener, windowDimensions);

		paddle.setCenter(
				new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-PADDLE_DISTANCE));
		gameObjects().addGameObject(paddle);
	}

	private void createBorders(Vector2 windowDimensions) {
		GameObject border_right = new GameObject(
				new Vector2((int)windowDimensions.x() - BORDER_WIDTH, 0),
				new Vector2(BORDER_WIDTH, (int)windowDimensions.y()),
				new RectangleRenderable(BORDER_COLOR));
		GameObject border_left = new GameObject(
				Vector2.ZERO,
				new Vector2(BORDER_WIDTH, (int)windowDimensions.y()),
				new RectangleRenderable(BORDER_COLOR));
		GameObject border_up = new GameObject(
				Vector2.ZERO,
				new Vector2((int)windowDimensions.x(), BORDER_WIDTH),
				new RectangleRenderable(BORDER_COLOR));
		gameObjects().addGameObject(border_right);
		gameObjects().addGameObject(border_left);
		gameObjects().addGameObject(border_up);
	}

	private void createBackground(Vector2 windowDimensions, ImageReader imageReader) {
		Renderable backgroundImage = imageReader.readImage(
				BACKGROUND_IMG_PATH, false);
		GameObject background = new GameObject(
				Vector2.ZERO,
				new Vector2((int)windowDimensions.x(),(int)windowDimensions.y()),
				backgroundImage);
		gameObjects().addGameObject(background, Layer.BACKGROUND);
	}

	/**
	 * Method to create bricks and add them to the game.
	 *
	 * @param windowDimensions  The dimensions of the game window.
	 * @param imageReader       The image reader for loading brick images.
	 * @param collisionStrategy The collision strategy for the bricks.
	 *
	 */
	private void createBricks(Vector2 windowDimensions, ImageReader imageReader,
							 CollisionStrategy collisionStrategy) {
		Renderable brickImage = imageReader.readImage(
				BRICK_IMG_PATH, false);


		float brickWidth = ((windowDimensions.x()- (2 * BORDER_WIDTH) - this.brickColumn - 2) / this.brickColumn);
		Vector2 brickDimensions = new Vector2(brickWidth, BRICK_HEIGHT);
		for (int i = 0; i < this.brickRows; i++) {
			float brickLeftY = BRICK_HEIGHT * i + BORDER_WIDTH + (3 * i);
			for (int j = 0; j < this.brickColumn; j++) {
				Vector2 topLeftCorner = new Vector2(j * brickWidth + BORDER_WIDTH + j + 2, brickLeftY);
				Brick brick = new Brick(
						topLeftCorner, brickDimensions,
						brickImage, collisionStrategy, BRICK_LAYER);
				gameObjects().addGameObject(brick, BRICK_LAYER);

			}

		}

	}

	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager(
				WINDOW_TITLE,
				new Vector2(WINDOW_X_DIMS, WINDOW_Y_DIMS), 2, 3);
		gameManager.run();
	}
}
