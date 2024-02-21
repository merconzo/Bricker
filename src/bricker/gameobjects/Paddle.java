package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 500;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    private final int bordersSize;

    private int collisionCounter = 0;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener input listener
     * @param windowDimensions the dimensions of the game window
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
				  Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions, int bordersSize) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.bordersSize = bordersSize;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        if (getTopLeftCorner().x() < bordersSize) {
            setTopLeftCorner(new Vector2(bordersSize, getTopLeftCorner().y()));
        } else if (getTopLeftCorner().x() > this.windowDimensions.x()-bordersSize-this.getDimensions().x()) {
            setTopLeftCorner(new Vector2(this.windowDimensions.x()-bordersSize-this.getDimensions().x(),
                    getTopLeftCorner().y()));
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {collisionCounter++;};

    }

    public int getCollisionCounter() {
        return collisionCounter;
    }
    public void setCollisionCounter(int collisionCounter) {
        this.collisionCounter = collisionCounter;
    }
}
