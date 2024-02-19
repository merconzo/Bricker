package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LifeHeartsCounter {

	private static final int SPACE_BETWEEN_HEARTS = 10;
	private int maxLife, curLifeCount;
	private final Heart[] hearts;
	public LifeHeartsCounter(Vector2 topLeftCorner, int heartSize,
							 Renderable heartImage,
							 int maxLifeCount, int initLifeCount) {
		this.maxLife = maxLifeCount;
		this.curLifeCount = initLifeCount;
		// create hearts
		this.hearts = new Heart[maxLifeCount];
		for (int i = 0; i < maxLifeCount; i++) {
			Vector2 curTopLeft = new Vector2(
					topLeftCorner.x() + (heartSize + SPACE_BETWEEN_HEARTS)*i,
					topLeftCorner.y());
			this.hearts[i] = new Heart(curTopLeft,
					new Vector2(heartSize, heartSize),
					heartImage);
		}

		// init visibility
		for (int i = initLifeCount; i < maxLifeCount; i++) {
			this.hearts[i].setVisibility(false);
		}
	}


}
