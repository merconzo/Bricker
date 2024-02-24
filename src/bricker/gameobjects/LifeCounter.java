package bricker.gameobjects;
/**
 * Interface representing a life counter.
 * Implementations of this interface are used to manage the player's life count in the game.
 */
public interface LifeCounter {

	void plusLifeCount();
	void minusLifeCount();
	int getLifeCount();
	int getMaxLife();
	void setLifeCount(int lifeCount);

}
