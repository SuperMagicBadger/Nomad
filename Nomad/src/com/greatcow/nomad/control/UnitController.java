package com.greatcow.nomad.control;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.greatcow.nomad.actors.UnitActor;

public class UnitController extends TemporalAction {
	private float startX, startY;
	private float endX, endY;
	private float startTheta, endTheta;

	@Override
	protected void begin() {
		UnitActor unit = (UnitActor) getActor();
		startX = unit.centerX();
		startY = unit.centerY();
		startTheta = actor.getRotation();
		endTheta = (float) Math.toDegrees(Math.atan2((endY - startY) , (endX - startX))) - 90;
	}

	@Override
	protected void update(float percent) {
		UnitActor unit = (UnitActor) getActor();
		unit.centerOn(startX + (endX - startX) * percent,
				startY + (endY - startY) * percent);
		unit.setRotation(startTheta + (endTheta - startTheta) * percent);
	}

	public void setPosition(float x, float y) {
		endX = x;
		endY = y;
	}

	public float getX() {
		return endX;
	}

	public float getY() {
		return endY;
	}
}
