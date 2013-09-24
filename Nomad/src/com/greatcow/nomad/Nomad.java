package com.greatcow.nomad;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greatcow.nomad.actors.SystemModel;
import com.greatcow.nomad.data.SystemFactory;
import com.greatcow.nomad.screens.Map;

public class Nomad extends Game {
	//varblok--------------------------
	public static Nomad game;
	public static ShapeRenderer shapeRenderer;
	public static Random rng;
	
	public static final String imageDir = "images/";
	public static final String imageExt = ".pack";
	
	public static final String fontDir = "fonts/";
	public static final String fontExt = ".fnt";
	
	public static final SystemFactory systemFactory = new SystemFactory();
	
	public Map mapScreen;
	//varblok==========================

	@Override
	public void create() {
		System.out.println("aSdf");
		
		game = this;
		shapeRenderer = new ShapeRenderer();
		rng = new Random();
		
		mapScreen = new Map();
		
		SystemModel model = systemFactory.loadModel("newLevelFormat");
		
		mapScreen.setSystemDraw(model);
		
		setScreen(mapScreen);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public static String texAtlasPath(String file){
		return imageDir + file + imageExt;
	}
	
	public static String fontPath(String file){
		return fontDir + file + fontExt;
	}
}
