package com.greatcow.nomad.actors;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SystemModel extends Stage{
	
	// varblok-------------------------
	// input management
	public InputMultiplexer multiplexer;
	GestureAdapter gestureAdater;
	// system data
	private ArrayList<PlanetActor> planetList = new ArrayList<PlanetActor>();
	private float modelWidth = 0;
	private float modelHeight = 0;
	// Background
	public ArrayList<TextureRegion> backgroundLayers;
	// varblok=========================
	
	public SystemModel() {
		gestureAdater = new GestureAdapter(){
			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				getCamera().translate(-deltaX, deltaY, 0);
				return true;
			}
		};
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(new GestureDetector(gestureAdater));
	}
	
	@Override
	public void draw() {
		getCamera().update();
		if (!getRoot().isVisible()) return;
		
		getSpriteBatch().setProjectionMatrix(getCamera().combined);
		getSpriteBatch().begin();
		
		for(TextureRegion tr : backgroundLayers){
			getSpriteBatch().draw(tr, 0, 0);
		}
		
		getRoot().draw(getSpriteBatch(), 1);
		getSpriteBatch().end();
	}
	
	public void addPlanet(PlanetActor planet) {
		planetList.add(planet);
		addActor(planet);
		
		if(modelWidth < Math.abs(planet.getX()) + planet.getWidth()){
			modelWidth = Math.abs(planet.getX()) + planet.getWidth();
		}
		
		if(modelHeight < Math.abs(planet.getY()) + planet.getHeight()){
			modelHeight = Math.abs(planet.getY()) + planet.getHeight();
		}
		
		Gdx.app.log("SystemModel", "width: " + modelWidth + " height: " + modelHeight);
	}
	
	public void releaseImages(){
		for(PlanetActor a : planetList){
			a.releaseRegion();
		}
	}
	
}
