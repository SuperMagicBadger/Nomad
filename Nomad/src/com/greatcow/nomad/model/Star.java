package com.greatcow.nomad.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.Nomad;

/**
 * model representation of a star.  Used for drawing it in the universe
 * map as well as maintaining level persistence
 * @author Cow
 *
 */
public class Star {

	// varblok-------------------------
	// position data
	public Vector2 position;
	// render data
	public String atlasName;
	public String textureName;
	// system data
	public ArrayList<Planet> planets;
	// varblok=========================
	
	// constructors----------------------------------------
	public Star(){
		position = Nomad.vectorPool.obtain();
		planets = new ArrayList<Planet>();
	}
	// constructors========================================
	
	// manips----------------------------------------------
	public void setImage(String atlas, String texture){
		atlasName = atlas;
		textureName = texture;
	}
	// manips==============================================
	
	// access----------------------------------------------
	public Actor getActor(){
		return null;
	}
	// access==============================================
}
