package com.greatcow.nomad.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.actors.CommandRing;
import com.greatcow.nomad.actors.PlanetActor;
import com.greatcow.nomad.actors.SystemModel;
import com.greatcow.nomad.actors.UnitActor;
import com.greatcow.nomad.actors.CommandRing.CommandListener;
import com.greatcow.nomad.actors.UnitActor.State;
import com.greatcow.nomad.data.Pools;

public class SystemInput extends InputMultiplexer implements GestureListener {

	public SystemModel model;
	public GestureDetector detector;
	public UnitActor activeUnit;
	public PlanetActor activePlanet;
	// cursor
	CommandRing unitRing;

	public SystemInput(SystemModel systemModel) {
		model = systemModel;
		detector = new GestureDetector(this);

		addProcessor(model);
		addProcessor(detector);		
		
		//create the unit command rings
		unitRing = new CommandRing("mono_white", "gamescreen", "red_circle");
		model.addActor(unitRing);
		
		unitRing.addCommand("Move", "gamescreen", "blue_circle", new CommandListener() {
			@Override
			public void onCommand() {
					System.out.println("move");
			}
		});
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		
		if(activeUnit != null){
			
			return true;
		}
		
		Vector2 v = Pools.obtainVector2(x, y);
		v = model.screenToStageCoordinates(v);
		Actor a = model.hit(v.x, v.y, true);

		if (a != null) {
			if (a instanceof PlanetActor) {
				activePlanet = (PlanetActor) a;
				activeUnit = null;
			} else if (a instanceof UnitActor) {
				activeUnit = (UnitActor) a;
				unitRing.setTarget(activeUnit);
				activePlanet = null;
			}
		}

		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (activeUnit != null && activeUnit.state == State.beginMove) {
			Vector2 v = Pools.obtainVector2(x, y);
			model.screenToStageCoordinates(v);
			UnitMovementController action = new UnitMovementController();
			action.setPosition(v.x, v.y);
			action.setDuration(1);
			activeUnit.addAction(action);
			activeUnit = null;
			unitRing.setTarget(null);
			unitRing.setCommand(-1);
			Pools.free(v);
		}
		deselectUnit();
		activePlanet = null;
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		activeUnit = null;
		activePlanet = null;
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		
		if(activeUnit != null && velocityY < -2){
			activeUnit.state = State.beginMove;
			unitRing.setCommand(0);
			return true;
		} else {		
			deselectUnit();
			activePlanet = null;
			return false;
		}
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (activeUnit == null) {
			Camera c = model.getCamera();

			// calculate positions
			float camLeft = c.position.x - (c.viewportWidth / 2f) - deltaX;
			float camBot = c.position.y - (c.viewportHeight / 2f) + deltaY;
			float camRight = c.position.x + (c.viewportWidth / 2f) - deltaX;
			float camTop = c.position.y + (c.viewportHeight / 2f) + deltaY;

			// correct x
			if (camLeft <= -model.modelWidth && deltaX > 0) {
				Gdx.app.log("SystemModel", "block left " + camLeft);
				deltaX = 0;
			} else if (camRight >= model.modelWidth && deltaX < 0) {
				Gdx.app.log("SystemModel", "block right " + camRight);
				deltaX = 0;
			}

			// correct y
			if (camTop >= model.modelHeight && deltaY > 0) {
				deltaY = 0;
			} else if (camBot <= -model.modelHeight && deltaY < 0) {
				deltaY = 0;
			}

			model.translate(-deltaX, deltaY);
			return true;
		}

		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
	
	public void selectUnit(){
		
	}
	
	public void deselectUnit(){
		if(activeUnit != null){
			activeUnit.state = State.rest;
			activeUnit = null;
		}
	}

}
