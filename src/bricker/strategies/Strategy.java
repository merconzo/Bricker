package bricker.strategies;

// Enum to represent different brick strategies
public enum Strategy {

	BASIC(0.5),
	PADDLE(0.1),
	PUCK(0.1),
	CAMERA(0.1),
	DOUBLE(0.1),
	LIFE(0.1),
	FALLING_HEART(0);

	private final double probability; //represents the strategy probability to be chosen by brick

	private Strategy(double probability) {
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}
}

