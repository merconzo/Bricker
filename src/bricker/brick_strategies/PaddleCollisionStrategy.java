/**
 * PaddleCollisionStrategy represents a collision strategy that adds an extra paddle to the game upon collision.
 * It extends BasicCollisionStrategy.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

public class PaddleCollisionStrategy extends BasicCollisionStrategy {
    private final Vector2 windowDimensions;

    /**
     * Constructs a PaddleCollisionStrategy.
     *
     * @param gameObjects      The game object collection.
     * @param object1Layer     The layer of the that has the collision strategy as a param.
     * @param windowDimensions The dimensions of the game window for centering the paddle.
     */
    public PaddleCollisionStrategy(GameObjectCollection gameObjects, int object1Layer, Vector2 windowDimensions) {
        super(gameObjects, object1Layer);
        this.windowDimensions = windowDimensions;
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
        // Check if an extra paddle already exists TODO: static functions etc.
        if (bricker.main.BrickerGameManager.getExtraPaddle() == null) {
            Paddle extraPaddle = bricker.main.BrickerGameManager.createPaddle(windowDimensions);
            // Set the position of paddle at the center
            extraPaddle.setCenter((new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2)));
            gameObjects.addGameObject(extraPaddle); //adding game

        }
    }


}
