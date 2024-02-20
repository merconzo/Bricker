package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
	private final Renderable image;
	public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
		super(topLeftCorner, dimensions, renderable);
		this.image = renderable;
	}

	public void setVisibility(boolean isVisible) {
		if (isVisible) {
			if (isVisible())  // TODO: maybe to delete
				return;
			super.renderer().setRenderable(image);
		}
		else {
			if (!isVisible())
				return;
			super.renderer().setRenderable(null);
		}
	}

	private boolean isVisible() {
		return super.renderer().getRenderable() != null;
	}
}
