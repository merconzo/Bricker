package bricker.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategiesFactory {
	//assets
	private final int puckBallRadius;
	private final GameManager gameManager;
	private final String ballTag;
	private final Vector2 windowDimensions;

	//helpers
	private static final int MAX_DOUBLES_COUNTER = 2;
	private static final Random rand = new Random();
	private final Counter doublesCounter = new Counter();

	//GameObjects
	private final GameObjectCollection gameObjects;
	private final int object1Layer;
	private final ImageReader imageReader;


	public StrategiesFactory(int puckBallRadius, Vector2 windowDimensions,
							 GameObjectCollection gameObjects, int object1Layer,
							 ImageReader imageReader, GameManager gameManager, String ballTag) {
		this.windowDimensions = windowDimensions;
		this.gameObjects = gameObjects;
		this.object1Layer = object1Layer;
		this.imageReader = imageReader;
		this.puckBallRadius = puckBallRadius;

		this.gameManager = gameManager;
		this.ballTag = ballTag;
	}

	/**
	 * returns specific CollisionStrategy.
	 * @param strategy Enum of wanted Strategy.
	 * @param objectCenter collied object's center (usually brick).
	 * @return CollisionStrategy
	 */
	public CollisionStrategy buildStrategy(Strategy strategy, Vector2 objectCenter) {
		CollisionStrategy chosen = getNewCollisionStrategy(strategy, objectCenter);
		resetCounter();
		return chosen;
	}

	/**
	 * get a new random Strategy.
	 * @param objectCenter collied object's center (usually brick)
	 * @return new CollisionStrategy
	 */
	public CollisionStrategy buildRandomStrategy(Vector2 objectCenter) {
		CollisionStrategy chosen = getNewCollisionStrategy(getRandomStrategy(false), objectCenter);
		resetCounter();
		return chosen;
	}

	private CollisionStrategy getNewCollisionStrategy(Strategy strategy, Vector2 objectCenter) {
		switch (strategy) {
		case PADDLE:
			return createPaddleStrategy();
		case CAMERA:
			return createCameraStrategy();
		case DOUBLE:
			return createDoubleStrategy(objectCenter);
		case PUCK:
			return createPuckStrategy(objectCenter);
		default:
			return createBasicStrategy();
		}
	}

	private CollisionStrategy createPuckStrategy(Vector2 brickCenter) {
		return new PuckCollisionStrategy(gameObjects, object1Layer, imageReader, puckBallRadius,
				brickCenter);
	}

	private CollisionStrategy createPaddleStrategy() {
		return new PaddleCollisionStrategy(gameObjects,
				object1Layer, windowDimensions);
	}


	private CollisionStrategy createDoubleStrategy(Vector2 brickCenter) {
		CollisionStrategy firstStrategy = getNewCollisionStrategy(getRandomStrategy(true), brickCenter);
		CollisionStrategy secondStrategy = getNewCollisionStrategy(getRandomStrategy(true), brickCenter);
		return new DoubleCollisionStrategy(gameObjects, object1Layer, firstStrategy, secondStrategy);
	}

	private Strategy getRandomStrategy(boolean isSpecial) {
		List<Strategy> strategies = generateStrategiesToPick(isSpecial);
		int ind = rand.nextInt(strategies.size());
		Strategy chosen = strategies.get(ind);
		if (chosen == Strategy.DOUBLE)
			doublesCounter.increment();
		return chosen;
	}

	private List<Strategy> generateStrategiesToPick(boolean isSpecial) {
		List<Strategy> strategies = new ArrayList<>();
		for(Strategy strategy: Strategy.values()) {
			if (isSpecial && strategy == Strategy.BASIC)
				continue;
			if (strategy == Strategy.DOUBLE && doublesCounter.value() >= MAX_DOUBLES_COUNTER)
				continue;
			int count =  (int) (strategy.getProbability() * 10);
			for (int i = 0; i < count; i++)
				strategies.add(strategy);
		}
		return strategies;
	}


	private CollisionStrategy createBasicStrategy() {
		return new BasicCollisionStrategy(gameObjects, object1Layer);
	}

	private CollisionStrategy createCameraStrategy() {
		return new CameraCollisionStrategy(
				gameObjects, object1Layer, gameManager, windowDimensions, ballTag);
	}

	public void resetCounter() {
		this.doublesCounter.reset();
	}

}