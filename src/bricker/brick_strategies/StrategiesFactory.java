package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.util.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * factory for collision strategies
 */
public class StrategiesFactory {
	//assets

	private final BrickerGameManager gameManager;

	//helpers
	private static final int MAX_DOUBLES_COUNTER = 2;
	private static final Random rand = new Random();
	private final Counter doublesCounter = new Counter();

	//GameObjects
	private final int object1Layer;

	/**
	 * constructor of brick_strategies factory.
	 * @param gameManager bricker game manager
	 * @param object1Layer layer of object1
	 */
	public StrategiesFactory(BrickerGameManager gameManager, int object1Layer) {
		this.gameManager = gameManager;
		this.object1Layer = object1Layer;

	}

	/**
	 * returns specific CollisionStrategy.
	 * @param strategy Enum of wanted Strategy.
	 * @return CollisionStrategy
	 */
	public CollisionStrategy buildStrategy(Strategy strategy) {
		CollisionStrategy chosen = getNewCollisionStrategy(strategy);
		resetCounter();
		return chosen;
	}

	/**
	 * get a new random Strategy.
	 * @return new CollisionStrategy
	 */
	public CollisionStrategy buildRandomStrategy() {
		CollisionStrategy chosen = getNewCollisionStrategy(getRandomStrategy(false));
		resetCounter();
		return chosen;
	}

	/**
	 * enum switch to choose the wanted strategy
	 * @param strategy enum of strategy
	 * @return Collision strategy
	 */
	private CollisionStrategy getNewCollisionStrategy(Strategy strategy) {
		switch (strategy) {
		case FALLING_HEART:
			return createFallingHeartStrategy();
		case LIFE:
			return createLifeStrategy();
		case PADDLE:
			return createPaddleStrategy();
		case CAMERA:
			return createCameraStrategy();
		case DOUBLE:
			return createDoubleStrategy();
		case PUCK:
			return createPuckStrategy();
		default:
			return createBasicStrategy();
		}
	}

	private CollisionStrategy createFallingHeartStrategy() {
		return new FallingHeartStrategy(gameManager, object1Layer);
	}

	/**
	 *
	 * @return new LifeCollisionStrategy
	 */
	private CollisionStrategy createLifeStrategy() {
		return new LifeCollisionStrategy(gameManager, object1Layer);
	}

	/**
	 *
	 * @return new PuckCollisionStrategy
	 */
	private CollisionStrategy createPuckStrategy() {
		return new PuckCollisionStrategy(gameManager, object1Layer);
	}

	/**
	 *
	 * @return new PaddleCollisionStrateg
	 */
	private CollisionStrategy createPaddleStrategy() {
		return new PaddleCollisionStrategy(gameManager, object1Layer);
	}

	/**
	 *
	 * @return double collision strategy
	 */
	private CollisionStrategy createDoubleStrategy() {
		CollisionStrategy firstStrategy = getNewCollisionStrategy(getRandomStrategy(true));
		CollisionStrategy secondStrategy = getNewCollisionStrategy(getRandomStrategy(true));
		return new DoubleCollisionStrategy(gameManager, object1Layer, firstStrategy, secondStrategy);
	}

	/**
	 * gets a random strategy from strategy enum
	 * @param isSpecial boolean representing if we would like a special strategy
	 * @return strategy enum
	 */
	private Strategy getRandomStrategy(boolean isSpecial) {
		List<Strategy> strategies = generateStrategiesToPick(isSpecial);
		int ind = rand.nextInt(strategies.size());
		Strategy chosen = strategies.get(ind);
		if (chosen == Strategy.DOUBLE)
			doublesCounter.increment();
		return chosen;
	}

	/**
	 * generating a list which each strategy enum appears in it couple of times following it propability.
	 * @param isSpecial true if wanted to create only special list
	 * @return strategy list
	 */
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

	/**
	 *
	 * @return new BasicCollisionStrategy
	 */
	private CollisionStrategy createBasicStrategy() {
		return new BasicCollisionStrategy(gameManager, object1Layer);
	}

	/**
	 *
	 * @return new CameraCollisionStrategy
	 */
	private CollisionStrategy createCameraStrategy() {
		return new CameraCollisionStrategy(gameManager, object1Layer);
	}

	/**
	 * resets the counter
	 */
	public void resetCounter() {
		this.doublesCounter.reset();
	}

}