package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class LifeNumericCounter extends GameObject {
	private static final int MAX_LIFE = 4;
	private static final int INIT_LIFE = 3;
	private static final Color DEFAULT_COLOR = Color.GREEN;

	private final int maxLife;
	private int lifeNumericCounter;
	private Color color;

	public LifeNumericCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
		super(topLeftCorner, dimensions, renderable);
		this.lifeNumericCounter = INIT_LIFE;
		this.maxLife = MAX_LIFE;
	}

	public int getLifeNumericCounter() {
		return lifeNumericCounter;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public Color getColor() {
		return color;
	}

	public void minusLifeNumericCounter(int lifeNumericCounter) {
		if (this.lifeNumericCounter == 0)
			return;
		this.lifeNumericCounter--;
		updateColor();
	}

	public void plusLifeNumericCounter(int lifeNumericCounter) {
		if (this.lifeNumericCounter == this.maxLife)
			return;
		this.lifeNumericCounter++;
		updateColor();
	}

	private void updateColor() {
		if (this.lifeNumericCounter <= 1)
			this.color = Color.RED;
		else if (this.lifeNumericCounter == 2)
			this.color = Color.ORANGE;
		else
			this.color = DEFAULT_COLOR;
	}
}
