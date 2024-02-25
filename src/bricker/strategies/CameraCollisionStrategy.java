/**
 * CameraCollisionStrategy represents a collision strategy:
 * while collision occurs wth object 1, the camera follows the man ball.
 * It implements the CollisionStrategy interface.
 */
package bricker.strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class CameraCollisionStrategy extends BasicCollisionStrategy {
	private final BrickerGameManager gameManager;

	/**
	 * Constructs a CameraCollisionStrategy with the given parameters.
	 *
	 * @param object1Layer The layer of the first object joining collision.
	 */
	public CameraCollisionStrategy(BrickerGameManager gameManager, int object1Layer) {
		super(gameManager, object1Layer);
		this.gameManager = gameManager;
	}

	/**
	 * on coliision changing camera focus to focus on main ball
	 * @param object1 The first game object involved in the collision. (usually brick)
	 * @param object2 The second game object involved in the collision.
	 */
	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		super.onCollision(object1, object2);
		if (!(object2.getTag().equals(gameManager.getMainBallTag())) || gameManager.camera() != null)
			return;
		gameManager.focusCameraOnBall();
	}

}
