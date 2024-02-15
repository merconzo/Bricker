package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjects;

    public BasicCollisionStrategy (GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    public BasicCollisionStrategy() {
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameObjects.removeGameObject(object1, Layer.STATIC_OBJECTS);
    }
}
