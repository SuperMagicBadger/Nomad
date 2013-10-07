package com.greatcow.nomad.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.greatcow.nomad.data.ArtManager;

public class UnitActor extends Actor{
	// varblok-----------------------------------
	public String atlasName;
	public String textureName;
	public TextureRegion image;
	// movement
	public int ap;
	public int maxAp;
	public float rangePerAp;
	// state
	public State state = State.rest;
	// constants
	private static final float move_duration = 1;
	// varblok===================================
	
	// helpers-----------------------------------
	
	public enum State{
		rest, 
		beginMove, beginShoot,
		moving, shooting,
		endMoving, endShooting;
	}
	
	// helpers===================================
	
	// constructors------------------------------
	public UnitActor(String atlas, String texture) {
		atlasName = atlas;
		textureName = texture;
		image = null;
	}
	// constructors==============================
	
	// rendering---------------------------------
	public void AquireRegion(){
		TextureAtlas atlas = ArtManager.getSingleton().getAtlas(atlasName);
		if(atlas != null){
			image = atlas.findRegion(textureName);
			setWidth(image.getRegionWidth());			
			setHeight(image.getRegionHeight());
			setOriginX(getWidth() / 2f);
			setOriginY(getHeight() / 2f);
		}
	}
	
	public void ReleaseRegion(){
		ArtManager.getSingleton().disposeAtlas(atlasName);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(image == null) {
			AquireRegion();
		} else {
			batch.draw(image, getX(), getY(),
					getOriginX(), getOriginY(), 
					getWidth(), getHeight(),
					getScaleX(), getScaleY(),
					getRotation());
		}
	}
	// rendering=================================
	
	// position----------------------------------
	public void centerOn(float x, float y){
		setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
	}
	
	public float centerX(){
		return getX() + getWidth() / 2f;
	}
	
	public float centerY(){
		return getY() + getHeight() / 2f;
	}
	// position==================================
	
	// movement----------------------------------
	public void moveTo(float x, float y){
		MoveToAction action = Actions.moveTo(x, y);
		action.setDuration(move_duration);
		addAction(action);
	}
	
	public void moveBy(float deltaX, float deltaY){
		
	}
	
	public void moveByHeading(float theta, float radius){
		radius += 90;
		
		float x = (float) (radius * Math.cos(Math.toRadians(theta)));
		float y = (float) (radius * Math.sin(Math.toRadians(theta)));
		
		translate(x, y);
	}
	
	public float maxRange(){
		return ap * rangePerAp;
	}
	
	public float maxTheta(){
		return 180;
	}
	// movement==================================
	
	// rotation----------------------------------
	// rotation==================================
}
