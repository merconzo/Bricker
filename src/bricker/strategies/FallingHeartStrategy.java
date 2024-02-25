package bricker.strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class FallingHeartStrategy extends BasicCollisionStrategy {

	public FallingHeartStrategy(BrickerGameManager gameManager, int object1Layer) {
		super(gameManager, object1Layer);
	}

	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		super.onCollision(object1, object2);
		super.gameManager.plusLife();
	}
}
