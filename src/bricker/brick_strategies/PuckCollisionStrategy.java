/**
* PuckCollisionStrategy represents a collision strategy that putting extra 2 Balls (named pucks) objects in the game.
* It extends BasicCollisionStrategy and handles collisions involving puck objects.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class PuckCollisionStrategy extends BasicCollisionStrategy {

    private final Ball puck1, puck2;

    /**
     * Constructs a PuckCollisionStrategy with the given parameters.
     *
     * @param gameObjects  The game object collection.
     * @param object1Layer The layer of the object that has the collision strategy as a param.
     * @param puck1        The first puck object (extra ball).
     * @param puck2        The second puck object (extra ball).
     */
    public PuckCollisionStrategy(GameObjectCollection gameObjects, int object1Layer,
                                 Ball puck1, Ball puck2) {
        super(gameObjects, object1Layer);
        this.puck1 = puck1;
        this.puck2 = puck2;
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
        super.gameObjects.addGameObject(puck1);
        super.gameObjects.addGameObject(puck2);
    }



}
