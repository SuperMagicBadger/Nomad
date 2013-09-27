package com.greatcow.nomad.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.data.ArtManager;

public class UnitActor extends Actor{
	// varblok-----------------------------------
	public String atlasName;
	public String textureName;
	public TextureRegion image;
	// varblok===================================
	
	public UnitActor(String atlas, String texture) {
		atlasName = atlas;
		textureName = texture;
		image = null;
	}
	
	public void AquireRegion(){
		TextureAtlas atlas = ArtManager.getSingleton().getAtlas(atlasName);
		if(atlas != null){
			image = atlas.findRegion(textureName);
			setWidth(image.getRegionWidth());			
			setHeight(image.getRegionHeight());
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
					getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
		}
	}
	
	
}
