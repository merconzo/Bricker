package bricker.brick_strategies;

/**
 * Enum to represent different brick brick_strategies and their probabilities
  */
public enum Strategy {
	/**
	 * basic collision
	 */
	BASIC(0.5),
	/**
	 * paddle collision
	 */
	PADDLE(0.1),
	/**
	 * puck collision
	 */
	PUCK(0.1),
	/**
	 * camera collision
	 */
	CAMERA(0.1),
	/**
	 * double strategy
	 */
	DOUBLE(0.1),
	/**
	 * life strategy
	 */
	LIFE(0.1),
	/**
	 * falling heart strategy
	 */
	FALLING_HEART(0);

	private final double probability; //represents the strategy probability to be chosen by brick

	/**
	 * constructor for the ennum
	 * @param probability the probability for the strategy to be chosen
	 */
	Strategy(double probability) {
		this.probability = probability;
	}

	/**
	 * getter for probability
	 */
	public double getProbability() {
		return probability;
	}
}

