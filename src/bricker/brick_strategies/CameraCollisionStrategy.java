/**
 * CameraCollisionStrategy represents a collision strategy:
 * while collision occurs wth object 1, the camera follows the man ball.
 * It implements the CollisionStrategy interface.
 */
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class CameraCollisionStrategy extends BasicCollisionStrategy {
	private final GameManager gameManager;
	private final Vector2 windowDimensions;
	private final String mainBallTag;

	/**
	 * Constructs a CameraCollisionStrategy with the given parameters.
	 *
	 * @param gameObjects  The game object collection.
	 * @param object1Layer The layer of the first object joining collision.
	 */
	public CameraCollisionStrategy(GameObjectCollection gameObjects, int object1Layer,
								   GameManager gameManager, Vector2 windowDimensions,
								   String mainBallTag) {
		super(gameObjects, object1Layer);
		this.gameManager = gameManager;
		this.windowDimensions = windowDimensions;
		this.mainBallTag = mainBallTag;
	}

	/**
	 * on coliision changing camera focus to focus on main ball
	 * @param object1 The first game object involved in the collision. (usually brick)
	 * @param object2 The second game object involved in the collision.
	 */
	@Override
	public void onCollision(GameObject object1, GameObject object2) {
		super.onCollision(object1, object2);
		if (!(object2.getTag().equals(mainBallTag)) || gameManager.camera() != null)
			return;
		gameManager.setCamera(new Camera(
				object2, Vector2.ZERO, windowDimensions.mult(1.2f), windowDimensions)
		);
	}

}
