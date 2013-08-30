package com.greatcow.nomad.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Planet extends Actor{

	//varblok--------------------------
	//render data
	public PlanetStyle style;
	
	//planet data
	public int resourceUnits;
	//varblok==========================
	
	public Planet(){
		style = null;
	}
	
	public Planet(PlanetStyle ps){
		setStyle(ps);
	}
	
	public void setStyle(PlanetStyle ps){
		style = ps;
		setWidth(ps.planetImage.getRegionWidth());
		setHeight(ps.planetImage.getRegionHeight());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(style != null){
			batch.draw(style.planetImage, getX(), getY());
		}
	}
}
