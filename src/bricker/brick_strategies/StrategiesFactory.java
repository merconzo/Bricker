package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.Random;


public class StrategiesFactory {

    //assets
    private static final String PUCK_IMG_PATH = "assets/mockBall.png";
    private final int puckBallRadius;

    private final Vector2 windowDimensions;

    private Vector2 brickCenter;

    //helpers
    private static final Random rand = new Random();
    private final int DEFAULT_MIN = 1;
    private final int DEFAULT_MAX = 10;
    private int minNum = DEFAULT_MIN;
    private int maxNum = DEFAULT_MAX;

    private int doublesCounter = 0;



    //GameObjects
    private final GameObjectCollection gameObjects;
    private final int object1Layer;
    private ArrayList<Ball> extraBallsList;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;


    public StrategiesFactory(int mainBallRadius, Vector2 windowDimensions,
                             GameObjectCollection gameObjects, int object1Layer,
                             ImageReader imageReader, UserInputListener inputListener) {
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.object1Layer = object1Layer;
        this.imageReader = imageReader;
        this.puckBallRadius = (int) (0.75 * mainBallRadius);
        this.inputListener = inputListener;

    }

    public CollisionStrategy returnRightStrategy (Vector2 brickCenter) {
        int randomNum = getRandomInt(minNum,maxNum);
        System.out.println(randomNum);
        this.brickCenter = brickCenter;
        if (randomNum == 6) {
            return (createPuckStrategy());
        }
        if (randomNum == 7) {
            return (createPaddleStrategy());
        }
        if (randomNum == 10) {
            doublesCounter++;
            if (doublesCounter >= 2) {
                this.maxNum = 9;
            }
            return (createDoubleStrategy());
        }
        else {
            return new BasicCollisionStrategy(gameObjects, object1Layer);
        }
    }

    private PuckCollisionStrategy createPuckStrategy () {
        // TODO: more than 2 pucks? LO HEVANTI MA HASHEELA
       Renderable puckImg = imageReader.readImage(PUCK_IMG_PATH, true);
       Ball puck1 = bricker.main.BrickerGameManager.createBall(puckImg, puckBallRadius, brickCenter);
       Ball puck2 = bricker.main.BrickerGameManager.createBall(puckImg, puckBallRadius, brickCenter);
       bricker.main.BrickerGameManager.addToExtraBallsList(puck1);
       bricker.main.BrickerGameManager.addToExtraBallsList(puck2);
       return new PuckCollisionStrategy(gameObjects, object1Layer, puck1, puck2);
    }

    private PaddleCollisionStrategy createPaddleStrategy(){
        return new PaddleCollisionStrategy(gameObjects,
                object1Layer, imageReader, inputListener, windowDimensions);
    }

    private DoubleCollisionStrategy createDoubleStrategy(){
        this.minNum = 6;
        CollisionStrategy firstStrategy = returnRightStrategy(this.brickCenter);
        CollisionStrategy secondStrategy = returnRightStrategy(this.brickCenter);
        setDefaultMinMax();
        return new DoubleCollisionStrategy(gameObjects, object1Layer,firstStrategy, secondStrategy);
    }

    private int getRandomInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    private void setDefaultMinMax() {
        this.maxNum = DEFAULT_MAX;
        this.minNum = DEFAULT_MIN;
    }

}