/**
 * BasicCollisionStrategy represents a basic collision strategy:
 * the first collied object (usually brick) disappears.
 * It implements the CollisionStrategy interface.
 */
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * represents a collision strategy which remove the first object on collision.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    /**
     * game manager
     */
    protected final BrickerGameManager gameManager;
    /**
     * the layer of the object to remove
     */
    protected int object1Layer;

    /**
     * Constructs a BasicCollisionStrategy with the given parameters.
     *
     * @param gameManager BrickerGameManager
     * @param object1Layer The layer of the first object joining collision.
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager, int object1Layer) {
        this.gameManager = gameManager;
        this.object1Layer = object1Layer;
    }


    /**
     * Handles collisions between game objects by removing the first object from the game.
     *
     * @param object1 The first game object involved in the collision. (usually brick)
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameManager.removeGameObject(object1, this.object1Layer);
    }
}
