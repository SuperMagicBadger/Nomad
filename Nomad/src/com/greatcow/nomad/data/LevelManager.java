package com.greatcow.nomad.data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.model.Level;
import com.greatcow.nomad.model.Planet;
import com.greatcow.nomad.model.PlanetStyle;

public class LevelManager {

	// helpers---------------
	
	// helpers===============
	
	// varblok---------------
	//statics
	private static Level activeLevel = null;
	private static LevelManager singleton = null;
	//pools
	Pool<Planet> planetPool;
	//generation paramiters
	private HashMap<String, PlanetStyle> styleList;
	// varblok===============
	
	// constructors------------------------------
	public static LevelManager getSingleton(){
		if(singleton == null){
			singleton = new LevelManager();
		}
		return singleton;
	}
	
	private LevelManager(){
		styleList = new HashMap<String, PlanetStyle>();
		planetPool = new Pool<Planet>(){
			@Override
			protected Planet newObject() {
				return new Planet();
			}
			
		};
	}
	// constructors==============================
	
	// planet styles-----------------------------
	public PlanetStyle registerStyle(String name, PlanetStyle style){
		styleList.put(name, style);
		return style;
	}
	public PlanetStyle getStyle(String name){
		if(styleList.containsKey(name)){
			return styleList.get(name);
		}
		return null;
	}
	public PlanetStyle unregisterStyle(String name){
		PlanetStyle ps = getStyle(name);
		if(ps != null){
			styleList.remove(name);
		}
		return ps;
	}
	// planet styles=============================
	
	// access------------------------------------
	@SuppressWarnings("incomplete-switch")
	public String[] getLevelsFiles(){
		FileHandle directory = null;
		switch(Gdx.app.getType()){
		case Android:
			directory = Gdx.files.internal("levels");
			break;
		case Desktop:
			directory = Gdx.files.local("./levels");
			break;
		}
		if(!directory.exists()){
			Gdx.app.log("LevelMan", "directory does not exist");
		} else {
			Gdx.app.log("LevelMan", "directory found");
		}
		String[] s = new String[directory.list(".level").length];
		for(int i = 0; i < s.length; i++){
			s[i] = directory.list(".level")[i].nameWithoutExtension();
			Gdx.app.log("LevelMan", s[i]);
		}
		return s;
	}
	
	public Level getActiveLevel(){
		return activeLevel;
	}
	// access====================================
	
	// manips------------------------------------
	public void readyLevel(Group g){
		if(activeLevel == null)
			activeLevel = new Level(g);
		
		Planet[] arr = activeLevel.removeAllPlanets();
		
		for(Planet p : arr){
			planetPool.free(p);
		}
	}
	
	public PlanetStyle pickStyle(){
		float max = 0;
		
		for(PlanetStyle ps : styleList.values()){
			max += ps.frequency;
		}
		
		float value = Nomad.rng.nextFloat();
		Gdx.app.log("Level Loader", "random style picker: " + value + ", " + max + ", " + (value *= max));
		
		for(PlanetStyle ps : styleList.values()){
			value -= ps.frequency;
			if(value <= 0){
				return ps;
			}
		}
		
		return null;
	}
	
	public void generateLevel(int planetCount, float minDist, float maxDist){
		// varblok
		Planet prev, current;
		Vector2 vec = Nomad.vectorPool.obtain();
		float minY = 0;
		float minX = 0;
		float maxX = 0;
		float maxY = 0;
		
		//generate sun
		prev = planetPool.obtain();
		prev.setPosition(0, 0);
		prev.setStyle(getStyle("black_planet"));
		activeLevel.addPlanet(prev);
		
		//generate planets
		for(int i = 0; i < planetCount; i++){
			current = planetPool.obtain();
			current.setStyle(getStyle("planet"));
			//generate the range and rotation
			final float range = (Nomad.rng.nextFloat() * (maxDist - minDist)) + minDist;
			final float rotation = Nomad.rng.nextFloat() * 360;
			//calculate the position and store min variables
			vec.set(i * (maxDist + minDist) / 2f + range, 0);
			vec.rotate(rotation);
			if(vec.x < minX){
				minX = vec.x;
			} else if (vec.x > maxX){
				maxX = vec.x;
			}
			if (vec.y < minY){
				minY = vec.y;
			} else if (vec.y > maxY){
				maxY = vec.y;
			}
			Gdx.app.log("LevelMan", "range: " + range + " rotation: " + rotation);
			//add the planet
			current.setPosition(vec.x, vec.y);
			activeLevel.addPlanet(current);
			prev = current;
		}

		for(int i = 0; i < activeLevel.orbits.size(); i++){
			activeLevel.getPlanet(i).translate(0 - minX, 0 - minY);
		}
		
		activeLevel.setWidth(maxX - minX);
		activeLevel.setHeight(maxY - minY);
		
		Nomad.vectorPool.free(vec);
	}
	// manips====================================
	
}