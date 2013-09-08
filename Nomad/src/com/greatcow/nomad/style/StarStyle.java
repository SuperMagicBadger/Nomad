package com.greatcow.nomad.style;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarStyle {
	public String atlasName, textureName;
	public float level;
	public int minPlanets, maxPlanets;
	public float minOrbitDist, maxOrbitDist;
	public HashMap<String, Float> planets = new HashMap<String, Float>();
}
