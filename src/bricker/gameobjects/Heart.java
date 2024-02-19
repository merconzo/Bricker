package bricker.gameobjects;

import danogl.GameObject;
import danogl.components.Transform;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
	private final Renderable initRenderable;
	private Renderable currentRenderable;
	public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
		super(topLeftCorner, dimensions, renderable);
		this.initRenderable = renderable;
		this.currentRenderable = renderable;
	}

	public void setVisibility(boolean isVisible) {
		if (isVisible) {
			if (isVisible())
				return;
			this.currentRenderable = initRenderable;
		}
		else {
			if (!isVisible())
				return;
			this.currentRenderable = null;
		}
	}

	public boolean isVisible() {
		return this.currentRenderable != null;
	}
}
