package com.greatcow.nomad.view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.greatcow.nomad.data.ArtManager;
import com.greatcow.nomad.model.Star;

public class StarView extends Actor {
	// varblok-------------------------
	private static Pool<StarView> pool = new Pool<StarView>(){
		@Override
		protected StarView newObject() {
			return new StarView();
		}
	};
	private static HashMap<Star, StarView> viewmap = new HashMap<Star, StarView>();
	public Star model;
	public TextureAtlas atlas;
	public TextureRegion region;
	// varblok=========================
	
	// constructors----------------------------------------	
	public static StarView obtain(Star star){
		StarView v = pool.obtain();
		v.setModel(star);
		return v;
	}
	
	public void free(){
		remove();
		ArtManager.getSingleton().disposeAtlas(model.atlasName);
		atlas = null;
		region = null;
		model = null;
		pool.free(this);
	}
	// constructors========================================
	
	// render settings-------------------------------------
	public void setModel(Star star){
		//add to star view
		if(model != null){
			viewmap.remove(model);
		}
		model = star;
		viewmap.put(model, this);
		
		// load assets
		atlas = ArtManager.getSingleton().getAtlas(model.atlasName);
		region = atlas.findRegion(model.textureName);
		
		
		//setup actor
		setPosition(model.position.x, model.position.y);
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {		
		//draw sprites
		batch.draw(region, getX(), getY(), getOriginX(),
				getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}
	// render settings=====================================
}
