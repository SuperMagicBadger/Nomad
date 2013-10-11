package com.greatcow.nomad.actors;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.ArtManager;
import com.greatcow.nomad.data.Pools;
import com.greatcow.nomad.shineys.ParallaxBackground;
import com.greatcow.nomad.shineys.ParallaxLayer;

public class SystemModel extends Stage{
	
	// varblok-------------------------
	// system data
	private ArrayList<PlanetActor> planetList = new ArrayList<PlanetActor>();
	public float modelWidth = 0;
	public float modelHeight = 0;
	// Background
	public ArrayList<TextureRegion> backgroundLayers;
	public ParallaxBackground bg; 
	// cursor
	CommandRing unitRing;
	// varblok=========================
	
	public SystemModel() {
		
		
		// create a test unit actor
		UnitActor ua = new UnitActor("gamescreen", "foundry");
		ua.setPosition(50, 50);
		addActor(ua);
		ua.toFront();
		
		//create the unit command rings
		unitRing = new CommandRing("mono_white", "gamescreen", "red_circle");
		addActor(unitRing);
		unitRing.setTarget(ua);
		
		// and the background
		backgroundLayers = new ArrayList<TextureRegion>();
		
		TextureRegion region1 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("bluestars");
		TextureRegion region2 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("littlegrid");
		TextureRegion region3 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("biggrid");
		
		ParallaxLayer[] layers = new ParallaxLayer[3];
		layers[0] = new ParallaxLayer(region1, new Vector2(0.15f, 0.15f), new Vector2(0, 0));
		layers[1] = new ParallaxLayer(region2, new Vector2(0.5f, 0.5f), new Vector2(0, 0));
		layers[2] = new ParallaxLayer(region3, new Vector2(1f, 1f), new Vector2(0, 0));
		
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
		planet.toBack();
		float radius = planet.getOrbitRadius();
		
		
		if(modelWidth - 500 < radius){
			modelWidth = radius + 500;
			modelHeight = radius + 500;
		}
		
		Gdx.app.log("SystemModel", "width: " + modelWidth + " height: " + modelHeight);
	}
	
	public void releaseImages(){
		for(PlanetActor a : planetList){
			a.releaseRegion();
		}
	}
	
	public void translate(float x, float y){
		getCamera().translate(x, y, 0);
		bg.scroll(x, y);
	}
	
}
