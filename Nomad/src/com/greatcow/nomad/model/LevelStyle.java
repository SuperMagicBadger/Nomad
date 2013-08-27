package com.greatcow.nomad.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelStyle {

	public String levelName;
	public int sizeX, sizeY;
	
	//render settings
	public String atlasName;
	public ArrayList<TextureRegion> planetSprites;
	
	//generation data
	public ArrayList<Integer> planetCounts;
	
}
