package com.greatcow.nomad.actors;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.greatcow.nomad.data.ArtManager;
import com.greatcow.nomad.shineys.ParallaxBackground;
import com.greatcow.nomad.shineys.ParallaxLayer;

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
	public ParallaxBackground bg; 
	// varblok=========================
	
	public SystemModel() {
		gestureAdater = new GestureAdapter(){
			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				getCamera().translate(-deltaX, deltaY, 0);
				bg.scroll(-deltaX, deltaY);
				return true;
			}
		};
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(new GestureDetector(gestureAdater));
		
		backgroundLayers = new ArrayList<TextureRegion>();
		
		TextureRegion region1 = ArtManager.getSingleton().getAtlas("propermain").findRegion("bluestars");
		TextureRegion region2 = ArtManager.getSingleton().getAtlas("propermain").findRegion("littlegrid");
		TextureRegion region3 = ArtManager.getSingleton().getAtlas("propermain").findRegion("whitestars");
		TextureRegion region4 = ArtManager.getSingleton().getAtlas("propermain").findRegion("biggrid");
		
		
		
		ParallaxLayer[] layers = new ParallaxLayer[4];
		layers[0] = new ParallaxLayer(region1, new Vector2(0.15f, 0.15f), new Vector2(0, 0));
		layers[1] = new ParallaxLayer(region2, new Vector2(0.5f, 0.5f), new Vector2(0, 0));
		layers[2] = new ParallaxLayer(region3, new Vector2(0.75f, 0.75f), new Vector2(0, 0));
		layers[3] = new ParallaxLayer(region4, new Vector2(1f, 1f), new Vector2(0, 0));
		
		bg = new ParallaxBackground(layers, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector2(0, 0));
	}
	
	@Override
	public void draw() {
		getCamera().update();
		bg.render(0.5f);
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
