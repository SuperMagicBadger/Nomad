package com.greatcow.nomad.control;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.greatcow.nomad.actors.UnitActor;

public class UnitMovementController extends TemporalAction {
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
		if(percent < 0.25){
			float p = percent / 0.25f; 
			rotate(p);
		} else {
			float p = (percent - 0.25f) / 0.75f;
			move(p);
		}
	}
	
	protected void rotate(float percent){
		UnitActor unit = (UnitActor) getActor();
		unit.setRotation(startTheta + (endTheta - startTheta) * percent);
	}
	
	protected void move(float percent){
		UnitActor unit = (UnitActor) getActor();
		unit.centerOn(startX + (endX - startX) * percent,
				startY + (endY - startY) * percent);
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
