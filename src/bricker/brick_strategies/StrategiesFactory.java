package bricker.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

import java.util.Random;

public class StrategiesFactory {

	//assets
	private final int puckBallRadius;
    private final GameManager gameManager;
    private final String ballTag;
    private final Vector2 windowDimensions;


	//helpers
	private static final Random rand = new Random();
	private final int DEFAULT_MIN = 1;
	private final int DEFAULT_MAX = 10;
	private int minNum = DEFAULT_MIN;
	private int maxNum = DEFAULT_MAX;

	private int doublesCounter = 0;


	//GameObjects
	private final GameObjectCollection gameObjects;
	private final int object1Layer;
	private final ImageReader imageReader;
    private Vector2 brickCenter;


    public StrategiesFactory(int puckBallRadius, Vector2 windowDimensions,
                             GameObjectCollection gameObjects, int object1Layer,
                             ImageReader imageReader, GameManager gameManager, String ballTag) {
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		this.object1Layer = object1Layer;
		this.imageReader = imageReader;
		//        this.puckBallRadius = (int) (0.75 * mainBallRadius);
		this.puckBallRadius = puckBallRadius;

        this.gameManager = gameManager;
        this.ballTag = ballTag;
    }

	public CollisionStrategy buildStrategy(Vector2 brickCenter) {
//        return getCameraStrategy();
        this.brickCenter = brickCenter;
		int randomNum = getRandomInt(minNum, maxNum);
		System.out.println(randomNum);
        if (randomNum == 4) {
            return getCameraStrategy();
        }
		if (randomNum == 6) {
			return createPuckStrategy(brickCenter);
		}
		if (randomNum == 7) {
			return createPaddleStrategy();
		}
		if (randomNum == 10) {
			doublesCounter++;
			if (doublesCounter >= 2) {
				this.maxNum = 9;
			}
			return createDoubleStrategy();
		} else {
			return new BasicCollisionStrategy(gameObjects, object1Layer);
		}
	}

//	public CollisionStrategy buildStrategy(Strategies strategy, Vector2 brickCenter) {
//		switch (strategy) {
//        case DOUBLE {
//
//        }
//		case PUCK -> {
//			return createPuckStrategy(brickCenter);
//		}
//        return getBasicStrategy();
//	}

	private CollisionStrategy createPuckStrategy (Vector2 brickCenter) {
		return new PuckCollisionStrategy(gameObjects, object1Layer, imageReader, puckBallRadius,
                brickCenter);
	}

	private CollisionStrategy createPaddleStrategy() {
		return new PaddleCollisionStrategy(gameObjects,
				object1Layer, windowDimensions);
	}

	private CollisionStrategy createDoubleStrategy() {
		this.minNum = 6;
		CollisionStrategy firstStrategy = buildStrategy(this.brickCenter);
		CollisionStrategy secondStrategy = buildStrategy(this.brickCenter);
		setDefaultMinMax();
		return new DoubleCollisionStrategy(gameObjects, object1Layer, firstStrategy, secondStrategy);
	}

    private CollisionStrategy getBasicStrategy() {
        return new BasicCollisionStrategy(gameObjects, object1Layer);
    }

    private CollisionStrategy getCameraStrategy() {
        return new CameraCollisionStrategy(
                gameObjects, object1Layer, gameManager, windowDimensions, ballTag);
    }

	private int getRandomInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

	private void setDefaultMinMax() {
		this.maxNum = DEFAULT_MAX;
		this.minNum = DEFAULT_MIN;
	}

}