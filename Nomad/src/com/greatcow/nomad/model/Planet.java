package com.greatcow.nomad.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

public class Planet extends Actor{
	//varblok--------------------------
	//render data
	public PlanetStyle style;
	
	//planet data
	public int resourceUnits;
	//varblok==========================
	
	public Planet(){
	}
	
	public Planet(PlanetStyle ps){
		setStyle(ps);
	}
	
	public void setStyle(PlanetStyle ps){
		style = ps;
		setWidth(ps.planetImage.getRegionWidth());
		setHeight(ps.planetImage.getRegionHeight());
		setOriginX(style.planetImage.getRegionWidth() / 2f);
		setOriginY(style.planetImage.getRegionHeight() / 2f);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(style != null){
			batch.draw(
					style.planetImage, 
					getX(), getY(),
					getOriginX(), getOriginY(),
					getWidth(), getHeight(), 
					getScaleX(), getScaleY(),
					getRotation());
		}
	}
}
