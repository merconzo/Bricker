package bricker.brick_strategies;
public enum Strategy {

	BASIC(0.5),
	PADDLE(0.1),
	PUCK(0.1),
	CAMERA(0.1),
	DOUBLE(0.1),
	LIFE(0.1);

	private final double probability;

	private Strategy(double probability) {
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}
}

/*
Strategy strategy = Strategy.valueOf("STRATEGY_1");
Strategy.PUCK.get()
 */