package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * represents a heart image game object
 */
public class Heart extends GameObject {
	private final Renderable image;
	private final CollisionStrategy collisionStrategy;
	private final ArrayList<String> collisionTagsList = new ArrayList<>();

	/**
	 * Constructor to initialize the Heart object.
	 *
	 * @param topLeftCorner The top-left corner position of the heart.
	 * @param dimensions    The dimensions of the heart.
	 * @param renderable    The renderable image for the heart.
	 */
	public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
				 CollisionStrategy collisionStrategy, String[] collisionTags) {
		super(topLeftCorner, dimensions, renderable);
		this.image = renderable;
        this.collisionStrategy = collisionStrategy;
		if (collisionTags != null)
			Collections.addAll(this.collisionTagsList, collisionTags);
    }
	/**
	 * Sets the visibility of the heart.
	 *
	 * @param isVisible True if the heart should be visible, false otherwise.
	 */
	public void setVisibility(boolean isVisible) {
		if (isVisible) {
			super.renderer().setRenderable(image);
		}
		else {
			super.renderer().setRenderable(null);
		}
	}

	/**
	 * Method called when a collision occurs with this brick.
	 *
	 * @param other      The GameObject involved in the collision.
	 * @param collision  The details of the collision.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		if(this.collisionStrategy != null)
			this.collisionStrategy.onCollision(this, other);
	}

	/**
	 * checks if the given object should collide with the heart.
	 * @param other The other GameObject.
	 * @return true/false
	 */
	@Override
	public boolean shouldCollideWith(GameObject other) {
		return collisionTagsList.contains(other.getTag());
	}
}
