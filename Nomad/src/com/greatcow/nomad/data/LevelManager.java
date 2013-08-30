package com.greatcow.nomad.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.greatcow.nomad.model.Level;

public class LevelManager {

	// varblok---------------
	//statics
	public static Level activeLevel = null;
	private static LevelManager singleton = null;
	// varblok===============
	
	// constructors------------------------------
	public static LevelManager getSingleton(){
		if(singleton == null){
			singleton = new LevelManager();
		}
		return singleton;
	}
	
	private LevelManager(){
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
	public void generateLevel(int planetCount){
		
		//generate sun
		
		//generate planets
		for(int i = 0; i < planetCount; i++){
			
		}
	}
	// manips====================================
	
}
