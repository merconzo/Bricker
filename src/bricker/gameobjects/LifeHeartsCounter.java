package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LifeHeartsCounter {

	private static final int SPACE_BETWEEN_HEARTS = 10;
	private final int maxLife;
	private int curLifeCount;
	private final Heart[] hearts;


	public LifeHeartsCounter(GameObjectCollection gameObjects, int layer,
							 Vector2 topLeftCorner,
							 int heartSize,
							 Renderable heartImage,
							 int maxLifeCount, int initLifeCount) {
//		super(topLeftCorner,
//				new Vector2((heartSize + SPACE_BETWEEN_HEARTS) * maxLifeCount, heartSize),
//				null);
		this.maxLife = maxLifeCount;
		this.curLifeCount = initLifeCount;
		// create hearts
		this.hearts = new Heart[maxLifeCount];
		for (int i = 0; i < maxLifeCount; i++) {
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
		this.curLifeCount = lifeCount;
		for (int i = 0; i < lifeCount; i++) {
			this.hearts[i].setVisibility(true);
		}
		for (int i = lifeCount; i < this.maxLife; i++) {
			this.hearts[i].setVisibility(false);
		}
	}

	public void minusLifeCount() {
		if (this.curLifeCount == 0)
			return;
		this.curLifeCount--;
		this.hearts[curLifeCount].setVisibility(false);
	}

	public void plusLifeCount() {
		if (this.curLifeCount == this.maxLife)
			return;
		this.hearts[curLifeCount].setVisibility(true);
		this.curLifeCount++;
	}


}
