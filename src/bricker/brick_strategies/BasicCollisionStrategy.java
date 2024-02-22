/**
 * BasicCollisionStrategy represents a basic collision strategy.
 * It implements the CollisionStrategy interface.
 */
package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class BasicCollisionStrategy implements CollisionStrategy {
    protected GameObjectCollection gameObjects;
    protected int object1Layer;
    /**
     * Constructs a BasicCollisionStrategy with the given parameters.
     *
     * @param gameObjects  The game object collection.
     * @param object1Layer The layer of the first object joining collision.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, int object1Layer) {
        this.gameObjects = gameObjects;
        this.object1Layer = object1Layer;
    }
    /**
     * Handles collisions between game objects by removing the first object from the game.
     *
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameObjects.removeGameObject(object1, this.object1Layer);
    }
}
