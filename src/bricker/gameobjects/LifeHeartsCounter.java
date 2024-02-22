package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static java.lang.Math.max;

public class LifeHeartsCounter implements LifeCounter {

	private static final int SPACE_BETWEEN_HEARTS = 10;
	private final int maxLife;
	private int lifeCount;
	private final Heart[] hearts;

	public LifeHeartsCounter(GameObjectCollection gameObjects, int layer,
							 Vector2 topLeftCorner,
							 int heartSize,
							 Renderable heartImage,
							 int maxLife, int initLifeCount) {
		this.lifeCount = max(initLifeCount, 1);  // 1 <= lifeCount
		this.maxLife = max(maxLife, this.lifeCount);  // lifeCount <= maxLife
		// create hearts
		this.hearts = new Heart[maxLife];
		for (int i = 0; i < maxLife; i++) {
			Vector2 curTopLeft = new Vector2(
					topLeftCorner.x() + (heartSize + SPACE_BETWEEN_HEARTS) * i,
					topLeftCorner.y());
			this.hearts[i] = new Heart(curTopLeft,
					new Vector2(heartSize, heartSize),
					heartImage);
			gameObjects.addGameObject(this.hearts[i], layer);
		}

		// init visibility
		setLifeCount(initLifeCount);

	}

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

	public void minusLifeCount() {
		if (this.lifeCount == 0)
			return;
		this.lifeCount--;
		this.hearts[lifeCount].setVisibility(false);
	}

	public void plusLifeCount() {
		if (this.lifeCount == this.maxLife)
			return;
		this.hearts[lifeCount].setVisibility(true);
		this.lifeCount++;
	}


}
