/**
* PuckCollisionStrategy represents a collision strategy that putting extra 2 Balls (named pucks) objects in the game.
* It extends BasicCollisionStrategy and handles collisions involving puck objects.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class PuckCollisionStrategy extends BasicCollisionStrategy {

    private static final String PUCK_IMG_PATH = "assets/mockBall.png";
    private static final int PUCKS_COUNT = 2;
    private final ImageReader imageReader;
    private final int puckRadius;
    private final Vector2 center;


    /**
     * Constructs a PuckCollisionStrategy with the given parameters.
     *
     * @param gameObjects  The game object collection.
     * @param object1Layer The layer of the object that has the collision strategy as a param.
     */
    public PuckCollisionStrategy(GameObjectCollection gameObjects, int object1Layer,
                                 ImageReader imageReader, int puckRadius, Vector2 center) {
        super(gameObjects, object1Layer);
        this.imageReader = imageReader;
        this.puckRadius = puckRadius;
        this.center = center;
    }
    /**
     * on collision doing the super onCollision method, adding puck objects to the game.
     *
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        addPucks();
    }

    private void addPucks() {
        Renderable puckImg = imageReader.readImage(PUCK_IMG_PATH, true);
        for (int i = 0; i < PUCKS_COUNT; i++) {
            Ball puck = bricker.main.BrickerGameManager.addBall(puckImg, puckRadius, center);
            gameObjects.addGameObject(puck);
            bricker.main.BrickerGameManager.addToExtraBallsList(puck);
        }
    }



}
