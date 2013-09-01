package com.greatcow.nomad.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.greatcow.nomad.Nomad;

public class Level extends Group{

	//varblok--------------------------
	public static Level activeLevel;
	//public Group mapgroup;
	private ArrayList<Planet> planetList;
	public ArrayList<Circle> orbits;
	//varblok==========================
	
	// constructors--------------------
	public Level(){
//		mapgroup = new Group();
		planetList = new ArrayList<Planet>();
		orbits = new ArrayList<Circle>();
	}
	// constructors====================
	
	// manips--------------------------
	public Planet addPlanet(Planet p){
		planetList.add(p);
		addActor(p);
		return p;
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
		workingVector = screenToLocalCoordinates(workingVector);
		Planet p = getPlanetAt(workingVector.x, workingVector.y);
		Nomad.vectorPool.free(workingVector);
		return p;
	}
	public Planet getPlanetAt(float x, float y){
		Actor a = hit(x, y, true);
		if(a != null && a instanceof Planet){
			return (Planet) a;
		} else {
			return null;
		}
	}
	public Planet getPlanet(int index){
		return planetList.get(index);
	}
	public Planet[] getPlanets(){
		Planet[] arr = new Planet[planetList.size()];
		for(int i = 0; i < arr.length; i++){
			arr[i] = planetList.get(i);
		}
		return arr;
	}
	// access==========================
	
	// delete stuff--------------------
	public Planet removePlanetAtScreen(float screenX, float screenY){
		Planet p = getPlanetAtScreen(screenX, screenY);
		if(p != null){
			removeActor(p);
			planetList.remove(p);
		}
		return p;
	}
	public Planet removePlanetAt(float x, float y){
		Planet p = getPlanetAt(x, y);
		if(p != null){
			removeActor(p);
			planetList.remove(p);
		}
		return p;
	}
	public Planet removePlanet(int index){
		Planet p = planetList.remove(index);
		removeActor(p);
		return p;
	}
	public Planet[] removeAllPlanets(){
		Planet[] arr = new Planet[planetList.size()];
		
		for(int i = 0; i < planetList.size(); i++){
			arr[i] = planetList.get(i);
			removeActor(arr[i]);
		}
		planetList.clear();
		
		return arr;
	}
	// delete stuff====================
}
