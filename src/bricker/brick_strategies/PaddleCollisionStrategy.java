/**
 * PaddleCollisionStrategy represents a collision strategy
 * that adds an extra paddle to the game upon collision.
 * It extends BasicCollisionStrategy.
 */
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * representing collision strategy which ads paddle
 */
public class PaddleCollisionStrategy extends BasicCollisionStrategy {

    /**
     * Constructs a PaddleCollisionStrategy.
     *
     * @param gameManager      The game manager.
     * @param object1Layer     The layer of the that has the collision strategy as a param.
     */
    public PaddleCollisionStrategy(BrickerGameManager gameManager, int object1Layer) {
        super(gameManager, object1Layer);
    }
    /**
     * adds an extra paddle to the game upon collision.
     *
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        super.gameManager.addExtraPaddle();
    }


}
