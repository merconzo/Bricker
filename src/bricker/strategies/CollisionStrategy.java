package bricker.strategies;

import danogl.GameObject;

/**
 * interface for choosing strategy for collisions (mostly balls with bricks)
 */
public interface CollisionStrategy {
   /**
    * execute strategy after collision of 2 objects.
    * @param object1 first object (usually brick)
    * @param object2 second object (usually ball)
    */
   void onCollision(GameObject object1, GameObject object2);
}
