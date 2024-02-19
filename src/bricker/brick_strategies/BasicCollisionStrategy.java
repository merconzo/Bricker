package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy {
    protected GameObjectCollection gameObjects;
    protected int object1Layer;

    public BasicCollisionStrategy(GameObjectCollection gameObjects, int object1Layer) {
        this.gameObjects = gameObjects;
        this.object1Layer = object1Layer;
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameObjects.removeGameObject(object1, this.object1Layer);
    }
}
