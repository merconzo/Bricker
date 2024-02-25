package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * class represents a Ball object
 */
public class Ball extends GameObject {
//    private static final String BALL_TAG = "ball";
    private final Sound collisionSound;
    private int collisionCounter = 0;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
//        this.setTag(BALL_TAG);
    }

    /**
     *
     * @param other The GameObject with which a collision occurred.
     * @param collision Collision
     * on collision enter flipping the ball direction, playing collision sound, counting the collision.
     *
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;
    }

    /**
     *
     * @return collision counter
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
