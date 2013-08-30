package com.greatcow.nomad.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.greatcow.nomad.Nomad;

public class Level {

	//varblok--------------------------
	public static Level activeLevel;
	Group mapgroup;
	ArrayList<Planet> planetList;
	HashMap<String, PlanetStyle> styleList;
	//varblok==========================
	
	// constructors--------------------
	public Level(Group g){
		mapgroup = g;
		planetList = new ArrayList<Planet>();
		styleList = new HashMap<String, PlanetStyle>();
	}
	// constructors====================
	
	// manips--------------------------
	public Planet addPlanet(Planet p){
		planetList.add(p);
		mapgroup.addActor(p);
		return p;
	}
	public PlanetStyle registerStyle(String name, PlanetStyle style){
		styleList.put(name, style);
		return style;
	}
	public Planet createPlanetAtScreen(String stylename, float screenX, float screenY){
		return null;
	}
	public Planet createPlanetAt(String stylename, float x, float y){
		return null;
	}
	// manips==========================
	
	// access--------------------------
	public Planet getPlanetAtScreen(float screenX, float screenY){
		//test for a hit
		Vector2 workingVector = Nomad.vectorPool.obtain();
		workingVector.set(screenX, screenY);
		workingVector = mapgroup.screenToLocalCoordinates(workingVector);
		Planet p = getPlanetAt(workingVector.x, workingVector.y);
		Nomad.vectorPool.free(workingVector);
		return p;
	}
	public Planet getPlanetAt(float x, float y){
		Actor a = mapgroup.hit(x, y, true);
		if(a != null && a instanceof Planet){
			return (Planet) a;
		} else {
			return null;
		}
	}
	public PlanetStyle getStyle(String name){
		if(styleList.containsKey(name)){
			return styleList.get(name);
		}
		return null;
	}
	// access==========================
	
	// delete stuff--------------------
	public PlanetStyle unregisterStyle(String name){
		PlanetStyle ps = getStyle(name);
		if(ps != null){
			styleList.remove(name);
		}
		return ps;
	}
	public Planet removePlanetAtScreen(float screenX, float screenY){
		Planet p = getPlanetAtScreen(screenX, screenY);
		if(p != null){
			mapgroup.removeActor(p);
			planetList.remove(p);
		}
		return p;
	}
	public Planet removePlanetAt(float x, float y){
		Planet p = getPlanetAt(x, y);
		if(p != null){
			mapgroup.removeActor(p);
			planetList.remove(p);
		}
		return p;
	}
	// delete stuff====================
}
