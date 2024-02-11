package bricker.main;

import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
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

	// assets path
	public static final String BALL_IMG_PATH = "assets/ball.png";
	public static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
	public static final String PADDLE_IMG_PATH = "assets/paddle.png";


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
				inputListener);

		paddle.setCenter(
				new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-PADDLE_DISTANCE));
		gameObjects().addGameObject(paddle);
	}

	public static void main(String[] args) {
		GameManager gameManager = new BrickerGameManager(
				"Bouncing Ball",
				new Vector2(700, 500));
		gameManager.run();
	}
}
