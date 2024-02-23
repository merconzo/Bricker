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
	 * Constructs a BasicCollisionStrategy with the given parameters.
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
