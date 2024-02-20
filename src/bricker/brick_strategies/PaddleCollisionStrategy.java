package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public class PaddleCollisionStrategy extends BasicCollisionStrategy {
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    public PaddleCollisionStrategy(GameObjectCollection gameObjects, int object1Layer, ImageReader imageReader,
                                   UserInputListener inputListener, Vector2 windowDimensions) {
        super(gameObjects, object1Layer);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        if (bricker.main.BrickerGameManager.getExtraPaddle() == null) {
            Paddle extraPaddle = bricker.main.BrickerGameManager.createPaddle(imageReader, inputListener, windowDimensions);
            extraPaddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
            bricker.main.BrickerGameManager.setExtraPaddle(extraPaddle);
            gameObjects.addGameObject(extraPaddle);
        }
    }


}
