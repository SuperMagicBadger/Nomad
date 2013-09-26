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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.ArtManager;
import com.greatcow.nomad.data.Pools;
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
				
				// calculate positions
				float camLeft = getCamera().position.x - (getCamera().viewportWidth / 2f) - deltaX;
				float camBot = getCamera().position.y - (getCamera().viewportHeight / 2f) + deltaY;
				float camRight = getCamera().position.x + (getCamera().viewportWidth / 2f) - deltaX;
				float camTop = getCamera().position.y + (getCamera().viewportHeight / 2f) + deltaY;

				System.out.println("center: " + getCamera().position.x + " " + getCamera().position.y);
				System.out.println("edges: " + camLeft + " " + camRight + " " + camTop + " " + camBot);
				
				// correct x
				if(camLeft <= -modelWidth && deltaX > 0){
					Gdx.app.log("SystemModel", "" + camLeft);
					deltaX = 0;
				} else if (camRight >= modelWidth && deltaX < 0) {
					Gdx.app.log("SystemModel", "" + camRight);
					deltaX = 0;
				}
				
				// correct y
				if(camTop >= modelHeight && deltaY > 0){
					deltaY = 0;
				} else if (camBot <= -modelHeight && deltaY < 0){
					deltaY = 0;
				}
				

				getCamera().translate(-deltaX, deltaY, 0);
				bg.scroll(-deltaX, deltaY);
				
				return true;
			}
		};
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(new GestureDetector(gestureAdater));
		
		backgroundLayers = new ArrayList<TextureRegion>();
		
		TextureRegion region1 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("bluestars");
		TextureRegion region2 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("littlegrid");
//		TextureRegion region3 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("whitestars");
		TextureRegion region4 = ArtManager.getSingleton().getAtlas("gamescreen").findRegion("biggrid");
		
		
		
		ParallaxLayer[] layers = new ParallaxLayer[3];
		layers[0] = new ParallaxLayer(region1, new Vector2(0.15f, 0.15f), new Vector2(0, 0));
		layers[1] = new ParallaxLayer(region2, new Vector2(0.5f, 0.5f), new Vector2(0, 0));
//		layers[2] = new ParallaxLayer(region3, new Vector2(0.75f, 0.75f), new Vector2(0, 0));
		layers[2] = new ParallaxLayer(region4, new Vector2(1f, 1f), new Vector2(0, 0));
		
		bg = new ParallaxBackground(layers, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector2(0, 0));
	}
	
	@Override
	public void draw() {
		
		getCamera().update();

////Draw camera debug--------------------------------------------------------------------------------
//		// calculate positions
//		float camLeft = getCamera().position.x - (getCamera().viewportWidth / 2f);
//		float camBot = getCamera().position.y - (getCamera().viewportHeight / 2f);
//		float camRight = getCamera().position.x + (getCamera().viewportWidth / 2f);
//		float camTop = getCamera().position.y + (getCamera().viewportHeight / 2f);
//		//draw camera spots
//		Nomad.shapeRenderer.begin(ShapeType.FilledCircle);
//		//top
//		Nomad.shapeRenderer.setColor(1, 0, 0, 1);
//		Nomad.shapeRenderer.filledCircle(getCamera().position.x, camTop, 32);
//		//bottom
//		Nomad.shapeRenderer.setColor(0, 1, 0, 1);
//		Nomad.shapeRenderer.filledCircle(getCamera().position.x, camBot, 32);
//		//center
//		Nomad.shapeRenderer.setColor(1, 1, 1, 1);
//		Nomad.shapeRenderer.filledCircle(getCamera().position.x, getCamera().position.y, 32);
//		//left
//		Nomad.shapeRenderer.setColor(1, 0, 1, 1);
//		Nomad.shapeRenderer.filledCircle(camLeft, getCamera().position.y, 32);
//		//right
//		Nomad.shapeRenderer.setColor(0, 0, 1, 1);
//		Nomad.shapeRenderer.filledCircle(camRight, getCamera().position.y, 32);	
//		Nomad.shapeRenderer.end();
////Draw camera debug--------------------------------------------------------------------------------
		
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
	
}
