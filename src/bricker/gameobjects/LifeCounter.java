package bricker.gameobjects;
/**
 * Interface representing a life counter.
 * Implementations of this interface are used to manage the player's life count in the game.
 */
public interface LifeCounter {
	/**
	 * add life count
	 */
	void plusLifeCount();

	/**
	 * decrease life count
	 */
	void minusLifeCount();

	/**
	 *getter of life count
	 */
	int getLifeCount();

	/**
	 *getter of maximum life count possible
	 */
	int getMaxLife();

	/**
	 *setter for life count
	 */
	void setLifeCount(int lifeCount);

}
