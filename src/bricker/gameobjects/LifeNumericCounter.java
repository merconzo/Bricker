package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static java.lang.Math.max;
/**
 * Represents a numeric life counter displayed as text.
 */
public class LifeNumericCounter extends GameObject implements LifeCounter {
	private static final int MAX_LIFE = 4;
	private static final int INIT_LIFE = 3;
	private static final Color DEFAULT_COLOR = Color.GREEN;

	private final TextRenderable renderable;
	private final int maxLife;
	private int lifeCount;
	/**
	 * Constructs a LifeNumericCounter with default initial and maximum life counts.
	 *
	 * @param topLeftCorner The top-left corner position of the counter.
	 * @param dimensions    The dimensions of the counter.
	 * @param renderable    The text renderable object for displaying the life count.
	 */
	public LifeNumericCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable) {
		this(topLeftCorner, dimensions, renderable, INIT_LIFE, MAX_LIFE);
	}
	/**
	 * Constructs a LifeNumericCounter with custom initial and maximum life counts.
	 *
	 * @param topLeftCorner The top-left corner position of the counter.
	 * @param dimensions    The dimensions of the counter.
	 * @param renderable    The text renderable object for displaying the life count.
	 * @param lifeCount     The initial number of lives.
	 * @param maxLife       The maximum number of lives allowed.
	 */
	public LifeNumericCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable,
							  int lifeCount, int maxLife) {
		super(topLeftCorner, dimensions, renderable);
		this.renderable = renderable;
		this.lifeCount = max(lifeCount, 1);  // 1 <= lifeCount
		this.maxLife = max(maxLife, this.lifeCount);  // lifeCount <= maxLife
		render();
	}
	/**
	 * Retrieves the current life count.
	 *
	 * @return The current life count.
	 */
	public int getLifeCount() {
		return lifeCount;
	}

	/**
	 * Retrieves the maximum number of lives allowed.
	 *
	 * @return The maximum number of lives allowed.
	 */
	public int getMaxLife() {
		return maxLife;
	}

	/**
	 * Sets the life count to the specified value.
	 *
	 * @param lifeCount The new life count.
	 */
	public void setLifeCount(int lifeCount) {
		if (lifeCount < 0 || lifeCount > maxLife)
			return;
		this.lifeCount = lifeCount;
		render();
	}
	/**
	 * Decreases the life count by one.
	 */
	public void minusLifeCount() {
		if (this.lifeCount == 0)
			return;
		this.lifeCount--;
		render();
	}
	/**
	 * Increases the life count by one.
	 */
	public void plusLifeCount() {
		if (this.lifeCount == this.maxLife)
			return;
		this.lifeCount++;
		render();
	}
	/**
	 * Renders the current life count.
	 */
	private void render() {
		updateColor();
		updateRenderNumber();
	}
	/**
	 * Updates the color of the text based on the current life count.
	 */
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
	/**
	 * Updates the text to display the current life count.
	 */
	private void updateRenderNumber() {
		this.renderable.setString(Integer.toString(this.lifeCount));
	}
}
