/**
 * LifeCollisionStrategy represents a collision strategy that adds an extra heart to the game.
 * the heart is falling and only if it is caught by the main paddle it will be added to life count
 * It extends BasicCollisionStrategy.
 */
package bricker.brick_strategies;


import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * The first collied object (usually brick) disappears,
 * then a heart falls down.
 * if the main paddle collects it, the players gains a life point.
 */
public class LifeCollisionStrategy extends BasicCollisionStrategy {

    /**
     * constructor
     * @param gameManager Bricker Game Manager
     * @param object1Layer brick layer
     */
    public LifeCollisionStrategy(BrickerGameManager gameManager, int object1Layer) {
        super(gameManager, object1Layer);

    }

    /**
     * Adds a falling heart at the center of the brick that disappeared.
     * @param object1 The first game object involved in the collision. (usually brick)
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        super.gameManager.addFallingHeart(object1.getCenter());
    }
}
