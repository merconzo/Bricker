package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static java.lang.Math.max;

public class LifeNumericCounter extends GameObject implements LifeCounter {
	private static final int MAX_LIFE = 4;
	private static final int INIT_LIFE = 3;
	private static final Color DEFAULT_COLOR = Color.GREEN;

	private final TextRenderable renderable;
	private final int maxLife;
	private int lifeCount;

	public LifeNumericCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable) {
		this(topLeftCorner, dimensions, renderable, INIT_LIFE, MAX_LIFE);
	}

	public LifeNumericCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable,
							  int lifeCount, int maxLife) {
		super(topLeftCorner, dimensions, renderable);
		this.renderable = renderable;
		this.lifeCount = max(lifeCount, 1);  // 1 <= lifeCount
		this.maxLife = max(maxLife, this.lifeCount);  // lifeCount <= maxLife
		render();
	}

	public int getLifeCount() {
		return lifeCount;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setLifeCount(int lifeCount) {
		if (lifeCount < 0 || lifeCount > maxLife)
			return;
		this.lifeCount = lifeCount;
		render();
	}

	public void minusLifeCount() {
		if (this.lifeCount == 0)
			return;
		this.lifeCount--;
		render();
	}

	public void plusLifeCount() {
		if (this.lifeCount == this.maxLife)
			return;
		this.lifeCount++;
		render();
	}

	private void render() {
		updateColor();
		updateRenderNumber();
	}

	private void updateColor() {
		Color color;
		if (this.lifeCount <= 1)
			color = Color.RED;
		else if (this.lifeCount == 2)
			color = Color.ORANGE;
		else
			color = DEFAULT_COLOR;
		this.renderable.setColor(color);
	}

	private void updateRenderNumber() {
		this.renderable.setString(Integer.toString(this.lifeCount));
	}
}
