package com.greatcow.nomad.control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class MoveAction extends Action {

	// varblok-------------------------
	Vector2 destination;
	Vector2 velocity;

	// varblok=========================

	// actions-------------------------
	@Override
	public boolean act(float delta) {
		getActor().translate(velocity.x * delta, velocity.y * delta);

		if (destination.dst(getActor().getX(), getActor().getY()) < 2) {
			getActor().removeAction(this);
		}
		return false;
	}
	// actions=========================
}
