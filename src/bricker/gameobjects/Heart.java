package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
	private final Renderable image;
	private final CollisionStrategy collisionStrategy;

	/**
	 * Constructor to initialize the Heart object.
	 *
	 * @param topLeftCorner The top-left corner position of the heart.
	 * @param dimensions    The dimensions of the heart.
	 * @param renderable    The renderable image for the heart.
	 */
	public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy) {
		super(topLeftCorner, dimensions, renderable);
		this.image = renderable;
        this.collisionStrategy = collisionStrategy;
    }
	/**
	 * Sets the visibility of the heart.
	 *
	 * @param isVisible True if the heart should be visible, false otherwise.
	 */
	public void setVisibility(boolean isVisible) {
		if (isVisible) {
			if (isVisible())  // TODO: maybe to delete
				return;
			super.renderer().setRenderable(image);
		}
		else {
			if (!isVisible())
				return;
			super.renderer().setRenderable(null);
		}
	}

	private boolean isVisible() {
		return super.renderer().getRenderable() != null;
	}
}
