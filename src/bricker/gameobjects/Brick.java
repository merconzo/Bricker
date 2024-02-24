package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {

    /**
     * totalNumberOfBricks
     */
    static public int totalNumberOfBricks = 0;

    private CollisionStrategy collisionStrategy;


    /**
     * Constructor for creating a Brick object.
     *
     * @param topLeftCorner    Position of the top-left corner of the brick.
     * @param dimensions       Width and height of the brick.
     * @param renderable       Renderable object representing the brick.
     * @param collisionStrategy   Collision strategy associated with the brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable, CollisionStrategy collisionStrategy, int brickLayer)  {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        totalNumberOfBricks++;
    }

//    /**
//     * setter for collision strategy
//     * @param collisionStrategy
//     */
//    public void setCollisionStrategy(CollisionStrategy collisionStrategy) {
//        this.collisionStrategy = collisionStrategy;
//    }

    /**
     * Method called when a collision occurs with this brick.
     *
     * @param other      The GameObject involved in the collision.
     * @param collision  The details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        totalNumberOfBricks--;
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other);
    }

}
