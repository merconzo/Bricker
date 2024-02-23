package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.StrategiesFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class BrickerGameManager extends GameManager {

	// sizes
	private static final int BALL_RADIUS = 20;
	private static final float BALL_SPEED = 200;
	private static final int PADDLE_HEIGHT = 15;
	private static final int PADDLE_WIDTH = 100;
	private static final int BORDER_WIDTH = 4;
	private static final int BRICK_HEIGHT = 15;
	private static final int DEFAULT_BRICK_ROW = 7;
	private static final int DEFAULT_BRICK_COLUMN = 8;
	private static final int LIFE_COUNTER_SIZE = 20;
	private static final int COUNTER_DISTANCE = 10;
	private static final int PADDLE_DISTANCE = LIFE_COUNTER_SIZE + 3 * COUNTER_DISTANCE;
	private static final int EXTRA_PADDLE_MAX_HITS = 4;
	private static final int COLLISIONS_TO_CAMERA_RESET = 4;

	// Layers
	private static final int BRICK_LAYER = Layer.STATIC_OBJECTS;
	private static final int LIFE_COUNTER_LAYER = Layer.UI;


	// assets paths
	public static final String BALL_IMG_PATH = "assets/ball.png";
	public static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
	public static final String PADDLE_IMG_PATH = "assets/paddle.png";
	public static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
	public static final String BRICK_IMG_PATH = "assets/brick.png";
	public static final String HEART_IMG_PATH = "assets/heart.png";

	// GUI & constants
	private static final String WINDOW_TITLE = "BRICKER!";
	private static final int WINDOW_X_DIMS = 700;
	private static final int WINDOW_Y_DIMS = 500;
	private static final Color BORDER_COLOR = Color.BLUE;
	private static final String MAIN_BALL_TAG = "mainBall";
	private static final int NOT_STAMPED = -1;

	// bricks number
	private final int brickRows;
	private final int brickColumns;

	// fields
	private WindowController windowController;
	private static ImageReader imageReader;
	private static UserInputListener inputListener;
	private static Sound collisionSound;
	private static Paddle extraPaddle = null;
	private static final Random rand = new Random();
	private static final ArrayList<Ball> extraBallsList = new ArrayList<>();
	private final Vector2 windowDimensions;
	private Ball ball;
	private LifeNumericCounter lifeNumericCounter;
	private LifeHeartsCounter lifeHeartsCounter;
	private int lastCameraStampCounter = NOT_STAMPED;


	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		this(windowTitle, windowDimensions, DEFAULT_BRICK_ROW, DEFAULT_BRICK_COLUMN);
	}

	public BrickerGameManager(String windowTitle, Vector2 windowDimensions, Integer brickRows,
							  Integer brickColumn) {
		super(windowTitle, windowDimensions);
		this.windowDimensions = windowDimensions;
		this.brickRows = brickRows;
		this.brickColumns = brickColumn;
	}



	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader,
							   UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.windowController = windowController;
		BrickerGameManager.imageReader = imageReader;
		BrickerGameManager.inputListener = inputListener;
		collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);

		addMainBall();  // add mainBall
		addBorders(); // create borders
		addBackground(); // create background
		addBricks();  // add Bricks

		// create paddle
		Paddle mainPaddle = createPaddle(this.windowDimensions);
		gameObjects().addGameObject(mainPaddle);

		// add life counters
		addLifeNumericCounter();
		addLifeHeartsCounter();

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		checkRemoveExtraPaddle();
		checkGameEnd();
		checkFallingBalls();
		checkCamera();
	}

	private void checkCamera() {
		if (camera() == null)
			return;
		if (this.lastCameraStampCounter == NOT_STAMPED)
			this.lastCameraStampCounter = this.ball.getCollisionCounter();
		int collisionsSinceCameraStamp = this.ball.getCollisionCounter() - this.lastCameraStampCounter;
		if (collisionsSinceCameraStamp >= COLLISIONS_TO_CAMERA_RESET) { // reset
			setCamera(null);
			this.lastCameraStampCounter = NOT_STAMPED;
		}
	}

	private void checkFallingBalls() {
		if (isBallOut(this.ball)) {  // main ball
			this.lifeNumericCounter.minusLifeCount();
			this.lifeHeartsCounter.minusLifeCount();
			setBallToCenter(windowDimensions.mult(0.5f), ball);
		}
		for (Ball ball : extraBallsList) {  // puck balls
			if (isBallOut(ball)) {
				gameObjects().removeGameObject(ball);
				extraBallsList.remove(ball);
				return;
			}
		}
	}


	private boolean isBallOut(Ball ball) {
		return ball.getCenter().y() > windowDimensions.y();
	}

	private void checkGameEnd() {  // TODO change to prsf
		String prompt = "";
		if (this.lifeNumericCounter.getLifeCount() == 0) {
			prompt = "You Lose!";
		}
		if (Brick.totalNumberOfBricks <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
			prompt = "You Win!";
		}
		if (!prompt.isEmpty()) {
			prompt += " Play again?";
			if (windowController.openYesNoDialog(prompt)) {
				clearTableBeforeNewGame(); //removes all extra objects (pucks, extra paddle, bricks that
				// left)
				windowController.resetGame();
			} else
				windowController.closeWindow();
		}
	}

	/**
	 * clears all extra game objects before restart game
	 */
	private void clearTableBeforeNewGame() {
		if (extraPaddle != null) {
			gameObjects().removeGameObject(extraPaddle);
		}
		setExtraPaddle(null);
		Brick.totalNumberOfBricks = 0;
		for (Ball ball : extraBallsList) {
			gameObjects().removeGameObject(ball);
		}
		extraBallsList.clear();
	}


	private void addMainBall() {
		Renderable ballImage = imageReader.readImage(BALL_IMG_PATH, true);
		this.ball = addBall(ballImage, BALL_RADIUS, windowDimensions.mult(0.5f));
		this.ball.setTag(MAIN_BALL_TAG);
		gameObjects().addGameObject(ball);
	}

	public static Ball addBall(Renderable ballImage, int ballRadius, Vector2 center) {
		Ball ball = new Ball(
				Vector2.ZERO, new Vector2(ballRadius, ballRadius), ballImage, collisionSound);
		setBallToCenter(center, ball);
		return ball;
	}

	public static void addToExtraBallsList(Ball ball) {
		extraBallsList.add(ball);
	}

	private static void setBallToCenter(Vector2 center, Ball ball) {
		ball.setCenter(center);  // center location
		ball.setVelocity(new Vector2(getBallVelocity(), getBallVelocity()));  // set velocity
	}

	private static float getBallVelocity() {
		if (rand.nextBoolean())  // random direction
			return -1 * BALL_SPEED;
		return BALL_SPEED;
	}

	public static Paddle createPaddle(Vector2 windowDimensions) {
		Renderable paddleImage = imageReader.readImage(
				PADDLE_IMG_PATH, true);
		Paddle paddle = new Paddle(
				Vector2.ZERO,
				new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
				paddleImage,
				inputListener, windowDimensions, BORDER_WIDTH);

		paddle.setCenter(
				new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - PADDLE_DISTANCE));
		return paddle;
	}


	public static void setExtraPaddle(Paddle extraPaddle) {
		BrickerGameManager.extraPaddle = extraPaddle;
	}

	public static Paddle getExtraPaddle() {
		return extraPaddle;
	}

	private void checkRemoveExtraPaddle() {
		if (extraPaddle != null && extraPaddle.getCollisionCounter() >= EXTRA_PADDLE_MAX_HITS) {
			gameObjects().removeGameObject(extraPaddle);
			setExtraPaddle(null);
		}
	}

	private void addBorders() {
		GameObject border_right = new GameObject(
				new Vector2((int) windowDimensions.x() - BORDER_WIDTH, 0),
				new Vector2(BORDER_WIDTH, (int) windowDimensions.y()),
				new RectangleRenderable(BORDER_COLOR));
		GameObject border_left = new GameObject(
				Vector2.ZERO,
				new Vector2(BORDER_WIDTH, (int) windowDimensions.y()),
				new RectangleRenderable(BORDER_COLOR));
		GameObject border_up = new GameObject(
				Vector2.ZERO,
				new Vector2((int) windowDimensions.x(), BORDER_WIDTH),
				new RectangleRenderable(BORDER_COLOR));
		gameObjects().addGameObject(border_right);
		gameObjects().addGameObject(border_left);
		gameObjects().addGameObject(border_up);
	}

	private void addBackground() {
		Renderable backgroundImage = imageReader.readImage(
				BACKGROUND_IMG_PATH, false);
		GameObject background = new GameObject(
				Vector2.ZERO,
				new Vector2((int) windowDimensions.x(), (int) windowDimensions.y()),
				backgroundImage);
		gameObjects().addGameObject(background, Layer.BACKGROUND);
	}

	/**
	 * Method to create bricks and add them to the game.
	 */
	private void addBricks() {
		StrategiesFactory strategiesFactory = new StrategiesFactory(
				(int) (0.75 * BALL_RADIUS), windowDimensions, gameObjects(), BRICK_LAYER,
				imageReader, this, MAIN_BALL_TAG);
		Renderable brickImage = imageReader.readImage(
				BRICK_IMG_PATH, false);
		float brickWidth =
				((windowDimensions.x() - (2 * BORDER_WIDTH) - this.brickColumns - 2) / this.brickColumns);
		Vector2 brickDimensions = new Vector2(brickWidth, BRICK_HEIGHT);
		for (int i = 0; i < this.brickRows; i++) {
			float brickLeftY = BRICK_HEIGHT * i + BORDER_WIDTH + (3 * i);
			for (int j = 0; j < this.brickColumns; j++) {
				Vector2 topLeftCorner = new Vector2(j * brickWidth + BORDER_WIDTH + j + 2, brickLeftY);
				Brick brick = new Brick(
						topLeftCorner, brickDimensions,
						brickImage, null, BRICK_LAYER);
				CollisionStrategy brickStrategy = strategiesFactory.buildRandomStrategy(brick.getCenter());
				brick.setCollisionStrategy(brickStrategy);
				gameObjects().addGameObject(brick, BRICK_LAYER);

			}

		}

	}

	private void addLifeNumericCounter() {
		TextRenderable counterRenderable = new TextRenderable("0");
		this.lifeNumericCounter = new LifeNumericCounter(
				new Vector2(BORDER_WIDTH + COUNTER_DISTANCE,
						(int) windowDimensions.y() - LIFE_COUNTER_SIZE - COUNTER_DISTANCE),
				new Vector2(LIFE_COUNTER_SIZE, LIFE_COUNTER_SIZE),
				counterRenderable
		);
		gameObjects().addGameObject(this.lifeNumericCounter, LIFE_COUNTER_LAYER);
	}

	private void addLifeHeartsCounter() {
		Renderable heartImage = imageReader.readImage(
				HEART_IMG_PATH, true);
		int maxLife = this.lifeNumericCounter.getMaxLife();
		int lifeCount = this.lifeNumericCounter.getLifeCount();
		this.lifeHeartsCounter = new LifeHeartsCounter(
				gameObjects(), LIFE_COUNTER_LAYER,
				new Vector2(BORDER_WIDTH + 2 * COUNTER_DISTANCE + LIFE_COUNTER_SIZE,
						(int) windowDimensions.y() - LIFE_COUNTER_SIZE - COUNTER_DISTANCE),
				LIFE_COUNTER_SIZE, heartImage, maxLife, lifeCount
		);

	}


	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager(
				WINDOW_TITLE,
				new Vector2(WINDOW_X_DIMS, WINDOW_Y_DIMS), 2, 3);
		// TODO change numbers
		gameManager.run();
	}
}
