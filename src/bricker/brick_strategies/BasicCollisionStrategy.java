package bricker.brick_strategies;

import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        System.out.println("collision with brick detected");
    }
}
