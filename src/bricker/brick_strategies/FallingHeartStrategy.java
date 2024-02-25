package bricker.brick_strategies;


import bricker.main.BrickerGameManager;
import danogl.GameObject;
/**
 * representing strategy for a falling heart
 */
public class FallingHeartStrategy extends BasicCollisionStrategy {
	/**
	 * constructor for falling heart strategy
	 * @param gameManager game manager
	 * @param object1Layer object1 layer
	 */
	public FallingHeartStrategy(BrickerGameManager gameManager, int object1Layer) {
		super(gameManager, object1Layer);
	}

	/**
	 * on collision add a life to game.
	 * @param object1 The first game object involved in the collision. (usually brick)
	 * @param object2 The second game object involved in the collision.
	 */
	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		super.onCollision(object1, object2);
		super.gameManager.plusLife();
	}
}
