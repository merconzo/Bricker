package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * DoubleCollisionStrategy class represents a collision strategy
 * that combines two different special collision brick_strategies.
 * It extends BasicCollisionStrategy and overrides the
 * onCollision method to merge the onCollision method of both
 * collisions
 */
public class DoubleCollisionStrategy extends BasicCollisionStrategy {
    private final CollisionStrategy firstCollisionStrategy;
    private final CollisionStrategy secondCollisionStrategy;

    /**
     * @param gameManager             The game object collection.
     * @param object1Layer            The layer of the object that has the collision strategy as a param.
     * @param firstCollisionStrategy  The first collision strategy to combine.
     * @param secondCollisionStrategy The second collision  to combine.
     */
    public DoubleCollisionStrategy(BrickerGameManager gameManager, int object1Layer,
                                   CollisionStrategy firstCollisionStrategy,
                                   CollisionStrategy secondCollisionStrategy) {
        super(gameManager, object1Layer);
        this.firstCollisionStrategy = firstCollisionStrategy;
        this.secondCollisionStrategy = secondCollisionStrategy;
    }

    /**
     * Overrides the onCollision method to combine the onCollision methods of both firstCollisionStrategy
     * and secondCollisionStrategy.
     *
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        firstCollisionStrategy.onCollision(object1, object2);
        secondCollisionStrategy. onCollision(object1,object2);
        }
}

