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

import bricker.gameobjects.Ball;

import java.awt.*;
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


	// assets path
	public static final String BALL_IMG_PATH = "assets/ball.png";
	public static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
	public static final String PADDLE_IMG_PATH = "assets/paddle.png";
	public static final String BACKGROUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
	public static final String BRICK_IMG_PATH = "assets/brick.png";



	public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
		super(windowTitle, windowDimensions);
	}

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader,
							   UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		Vector2 windowDimensions = windowController.getWindowDimensions();

		// add ball
		createBall(imageReader, soundReader, windowController);

		//create paddle
		createPaddle(imageReader, inputListener, windowDimensions);

		//create border
		createBorders(windowDimensions);

		//create background
		createBackground(windowDimensions, imageReader);

		//addBrick
		CollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy();
		createBrick(windowDimensions, imageReader, Vector2.ZERO, basicCollisionStrategy);
	}

	private void createBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
		Renderable ballImage =
				imageReader.readImage(BALL_IMG_PATH, true);
		Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);
		Ball ball = new Ball(
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
				new RectangleRenderable(Color.CYAN));
		GameObject border_left = new GameObject(
				Vector2.ZERO,
				new Vector2(BORDER_WIDTH, (int)windowDimensions.y()),
				new RectangleRenderable(Color.CYAN));
		GameObject border_up = new GameObject(
				Vector2.ZERO,
				new Vector2((int)windowDimensions.x(), BORDER_WIDTH),
				new RectangleRenderable(Color.CYAN));
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

	private void createBrick(Vector2 windowDimensions, ImageReader imageReader,
							 Vector2 topLeftCorner, CollisionStrategy collisionStrategy) {
		Renderable brickImage = imageReader.readImage(
				BRICK_IMG_PATH, false);
		Brick brick = new Brick(
				topLeftCorner, new Vector2(windowDimensions.x(), BRICK_HEIGHT),
				brickImage, collisionStrategy);
		gameObjects().addGameObject(brick);

	}
	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager(
				"Bouncing Ball",
				new Vector2(700, 500));
		gameManager.run();
	}
}