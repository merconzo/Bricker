/**
* PuckCollisionStrategy represents a collision strategy that putting extra 2 Balls (named pucks) objects in the game.
* It extends BasicCollisionStrategy and handles collisions involving puck objects.
 */
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class PuckCollisionStrategy extends BasicCollisionStrategy {

    private static final String PUCK_IMG_PATH = "assets/mockBall.png";
    private static final int PUCKS_COUNT = 2;


    /**
     * Constructs a PuckCollisionStrategy with the given parameters.
     *
     * @param gameManager  The gamemanager.
     * @param object1Layer The layer of the object that has the collision strategy as a param.
     */
    public PuckCollisionStrategy(BrickerGameManager gameManager, int object1Layer) {
        super(gameManager, object1Layer);
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
        addPucks(object1.getCenter());
    }

    /**
     * adding wanted number of pucks to game
     * @param center the center location of pucks
     */
    private void addPucks(Vector2 center) {
        Renderable puckImg = gameManager.readImage(PUCK_IMG_PATH, true);
        for (int i = 0; i < PUCKS_COUNT; i++) {
            gameManager.addPuckBalls(puckImg, center);
        }
    }



}
