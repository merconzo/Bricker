package bricker.gameobjects;

import danogl.GameObject;
import danogl.components.Component;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {
	private static final int MAX_LIFE = 4;
	private static final int INIT_LIFE = 3;

	private int lifeNumericCounter, maxLife;

	public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
		super(topLeftCorner, dimensions, renderable);
		this.lifeNumericCounter = INIT_LIFE;
		this.maxLife = MAX_LIFE;
	}

}
