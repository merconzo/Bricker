package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.ArrayList;

public class StrategiesFactory {

    //assets
    private static final String PUCK_IMG_PATH = "assets/mockBall.png";

    private final int puckBallRadius;
    private Vector2 windowDimensions;
    private Vector2 brickCenter;

    //GameObjects
    private final GameObjectCollection gameObjects;
    private final int object1Layer;
    private ArrayList<Ball> extraBallsList;
    private final Sound collisionSound;
    private final ImageReader imageReader;
    private UserInputListener inputListener;

    public StrategiesFactory(int mainBallRadius, Vector2 windowDimensions,
                             GameObjectCollection gameObjects, int object1Layer,
                             ArrayList<Ball> extraBallsList, Sound collisionSound,
                             ImageReader imageReader, UserInputListener inputListener) {
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.object1Layer = object1Layer;
        this.collisionSound = collisionSound;
        this.imageReader = imageReader;
        this.puckBallRadius = (int) (0.75 * mainBallRadius);
        this.inputListener = inputListener;
    }

    public CollisionStrategy returnRightStrategy (int randomNum, Brick brick) {
        if (randomNum == 6) {
            Vector2 brickCenter = brick.getCenter();
            return (createPuckStrategy(brickCenter));
        }
        if (randomNum == 7) {
            return (createPaddleStrategy());
        }
        else {
            return new BasicCollisionStrategy(gameObjects, object1Layer);
        }
    }

    private PuckCollisionStrategy createPuckStrategy (Vector2 brickCenter) {
       Renderable puckImg = imageReader.readImage(PUCK_IMG_PATH, true);
       Ball puck1 = bricker.main.BrickerGameManager.createBall(puckImg, collisionSound, puckBallRadius, brickCenter);
       Ball puck2 = bricker.main.BrickerGameManager.createBall(puckImg, collisionSound, puckBallRadius, brickCenter);
       bricker.main.BrickerGameManager.addToExtraBallsList(puck1);
       bricker.main.BrickerGameManager.addToExtraBallsList(puck2);
       return new PuckCollisionStrategy(gameObjects, object1Layer, puck1, puck2);
    }

    private PaddleCollisionStrategy createPaddleStrategy(){
        return new PaddleCollisionStrategy(gameObjects,
                object1Layer, imageReader, inputListener, windowDimensions);
    }



}