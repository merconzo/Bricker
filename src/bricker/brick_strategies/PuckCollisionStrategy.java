package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

public class PuckCollisionStrategy extends BasicCollisionStrategy {

    private final Ball puck1, puck2;

    public PuckCollisionStrategy(GameObjectCollection gameObjects, int object1Layer,
                                 Ball puck1, Ball puck2) {
        super(gameObjects, object1Layer);
        this.puck1 = puck1;
        this.puck2 = puck2;
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        super.gameObjects.addGameObject(puck1);
        super.gameObjects.addGameObject(puck2);
    }



}
