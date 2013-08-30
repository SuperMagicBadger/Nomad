package com.greatcow.nomad.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool;
import com.greatcow.nomad.model.Level;
import com.greatcow.nomad.model.Planet;
import com.greatcow.nomad.model.PlanetStyle;

public class LevelManager {

	// varblok---------------
	//statics
	public static Level activeLevel = null;
	private static LevelManager singleton = null;
	//pools
	Pool<Planet> planetPool;
	// varblok===============
	
	// constructors------------------------------
	public static LevelManager getSingleton(){
		if(singleton == null){
			singleton = new LevelManager();
		}
		return singleton;
	}
	
	private LevelManager(){
		planetPool = new Pool<Planet>(){
			@Override
			protected Planet newObject() {
				return new Planet();
			}
			
		};
	}
	// constructors==============================
	
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
	// access====================================
	
	// manips------------------------------------
	public void readyLevel(Group g){
		if(activeLevel == null)
			activeLevel = new Level(g);
		
		
	}
	
	public void generateLevel(int planetCount){
		// varblok
		Planet p;
		
		//generate sun
		p = new Planet();
		p.setPosition(0, 0);
		
		//generate planets
		for(int i = 0; i < planetCount; i++){
			
		}
	}
	// manips====================================
	
}
