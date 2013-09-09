package com.greatcow.nomad.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.greatcow.nomad.model.Planet;

public class PlanetView extends Actor{
	
	// varblok-------------------------
	TextureAtlas ta;
	TextureRegion tr;
	Planet p;
	// varblok=========================
	
	// constructors--------------------
	public static Pool<PlanetView> viewPool = new Pool<PlanetView>(){
		@Override
		protected PlanetView newObject() {
			return new PlanetView();
		}
		
	};
	
	public static PlanetView getView(Planet model){
		PlanetView view = viewPool.obtain();
		view.p = model;
		return view;
	}
	
	public PlanetView(){
		
	}
	// constructors====================
	
	// rendering-----------------------
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(tr != null){
			batch.draw(tr, getX(), getY(), getOriginX(),
					getOriginY(), getWidth(), getHeight(), getScaleX(),
					getScaleY(), getRotation());
		}
	}
	// rendering=======================
	
}
