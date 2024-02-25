package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.collisions.Layer;
import danogl.util.Vector2;

import static java.lang.Math.max;
/**
 * Represents a graphic life counter using hearts images: each heart is a life point.
 */
public class LifeHeartsCounter implements LifeCounter {

	private static final int SPACE_BETWEEN_HEARTS = 10;
	private final int maxLife;
	private int lifeCount;
	private final Heart[] hearts;

	/**
	 * Constructor to initialize the LifeHeartsCounter.
	 *
	 * @param gameManager   The game manager.
	 * @param topLeftCorner The top-left corner position.
	 * @param heartSize     The size of each heart.
	 * @param initLifeCount The initial life count.
	 * @param maxLife       The maximum life count.
	 */
	public LifeHeartsCounter(BrickerGameManager gameManager,
							 Vector2 topLeftCorner, int heartSize, int initLifeCount, int maxLife) {
		this.lifeCount = max(initLifeCount, 1);  // 1 <= lifeCount
		this.maxLife = max(maxLife, this.lifeCount);  // lifeCount <= maxLife
		// create hearts
		this.hearts = new Heart[maxLife];
		for (int i = 0; i < maxLife; i++) {
			Vector2 curTopLeft = new Vector2(
					topLeftCorner.x() + (heartSize + SPACE_BETWEEN_HEARTS) * i,
					topLeftCorner.y());
			this.hearts[i] = gameManager.createHeart(curTopLeft, Layer.UI, null, null);
		}

		// init visibility
		setLifeCount(initLifeCount);

	}
	/**
	 * Sets the life count to the specified value and updates the visibility of hearts accordingly.
	 *
	 * @param lifeCount The new life count.
	 */
	public void setLifeCount(int lifeCount) {
		if (lifeCount < 0 || lifeCount > maxLife)
			return;
		this.lifeCount = lifeCount;
		for (int i = 0; i < lifeCount; i++) {
			this.hearts[i].setVisibility(true);
		}
		for (int i = lifeCount; i < this.maxLife; i++) {
			this.hearts[i].setVisibility(false);
		}
	}
	/**
	 * Decreases the life count by one and updates the visibility of the corresponding heart.
	 */
	public void minusLifeCount() {
		if (this.lifeCount == 0)
			return;
		this.lifeCount--;
		this.hearts[lifeCount].setVisibility(false);
	}
	/**
	 * Increases the life count by one and updates the visibility of the corresponding heart.
	 */
	public void plusLifeCount() {
		if (this.lifeCount == this.maxLife)
			return;
		this.hearts[lifeCount].setVisibility(true);
		this.lifeCount++;
	}

	public int getLifeCount() {
		return this.lifeCount;
	}

	public int getMaxLife() {
		return this.maxLife;
	}
}
