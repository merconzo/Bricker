package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * representing paddle game object
 */
public class Paddle extends GameObject {
	private static final float MOVEMENT_SPEED = 500;
	private final UserInputListener inputListener;
	private final Vector2 windowDimensions;
	private final int bordersWidth;
	private final ArrayList<String> collisionTagsList = new ArrayList<>();
	private int collisionCounter = 0;

	/**
	 * Construct a new GameObject instance.
	 *
	 * @param topLeftCorner    Position of the object, in window coordinates (pixels).
	 *                         Note that (0,0) is the top-left corner of the window.
	 * @param dimensions       Width and height in window coordinates.
	 * @param renderable       The renderable representing the object. Can be null, in which case
	 * @param inputListener    input listener
	 * @param windowDimensions the dimensions of the game window
	 */
	public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
				  Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions,
                  int bordersWidth, String[] collisionTags) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
		this.windowDimensions = windowDimensions;
		this.bordersWidth = bordersWidth;
		if (collisionTags != null)
			Collections.addAll(this.collisionTagsList, collisionTags);
	}

	/**
	 * updating the padlle location du to input listener and borders location.
	 * @param deltaTime The time elapsed, in seconds, since the last frame.
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		// move with arrow keys
		Vector2 movementDir = Vector2.ZERO;
		if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
			movementDir = movementDir.add(Vector2.LEFT);
		}
		if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
			movementDir = movementDir.add(Vector2.RIGHT);
		}
		setVelocity(movementDir.mult(MOVEMENT_SPEED));

		// keep borders bound
		if (getTopLeftCorner().x() < bordersWidth) {
			setTopLeftCorner(new Vector2(bordersWidth, getTopLeftCorner().y()));
		} else if (getTopLeftCorner().x() >
				   this.windowDimensions.x() - bordersWidth - this.getDimensions().x()) {
			setTopLeftCorner(new Vector2(
					this.windowDimensions.x() - bordersWidth - this.getDimensions().x(),
					getTopLeftCorner().y()));
		}
	}

	/**
	 * counting collision while enter
	 * @param other The GameObject with which a collision occurred.
	 * @param collision Information regarding this collision.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		collisionCounter++;
	}

	/**
	 * checks if the given object should collide with the paddle
	 * @param other The other GameObject.
	 * @return true/false
	 */
	@Override
	public boolean shouldCollideWith(GameObject other) {
		return collisionTagsList.contains(other.getTag());
	}


	/**
	 *
	 * @return collisionCounter
	 */
	public int getCollisionCounter() {
		return collisionCounter;
	}


}
