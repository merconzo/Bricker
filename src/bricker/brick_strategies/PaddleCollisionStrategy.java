/**
 * PaddleCollisionStrategy represents a collision strategy that adds an extra paddle to the game upon collision.
 * It extends BasicCollisionStrategy.
 */
package bricker.brick_strategies;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public class PaddleCollisionStrategy extends BasicCollisionStrategy {
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Constructs a PaddleCollisionStrategy.
     *
     * @param gameObjects     The game object collection.
     * @param object1Layer    The layer of the that has the collision strategy as a param.
     * @param imageReader     The image reader for loading paddle images.
     * @param inputListener   The user input listener for paddle control.
     * @param windowDimensions The dimensions of the game window for centering the paddle.
     */
    public PaddleCollisionStrategy(GameObjectCollection gameObjects, int object1Layer, ImageReader imageReader,
                                   UserInputListener inputListener, Vector2 windowDimensions) {
        super(gameObjects, object1Layer);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
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
        // Check if an extra paddle already exists
        if (bricker.main.BrickerGameManager.getExtraPaddle() == null) {
            Paddle extraPaddle = bricker.main.BrickerGameManager.createPaddle(imageReader, inputListener, windowDimensions);
            // Set the position of paddle at the center
            extraPaddle.setCenter((new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2)));
            gameObjects.addGameObject(extraPaddle); //adding game
            bricker.main.BrickerGameManager.setExtraPaddle(extraPaddle); //adding to the param in the manager

        }
    }


}
