package com.greatcow.nomad.data;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.greatcow.nomad.model.Planet;
import com.greatcow.nomad.model.Star;
import com.greatcow.nomad.model.Universe;

public class CelestialFactory {

	// helper classes-----------------------

	/**
	 * Rules for generating a planet
	 * 
	 * @author Cow
	 * 
	 */
	public class PlanetType {

		public String atlasName, regionName;
		public TextureAtlas atlas;
		public TextureRegion planetImage = null;
		public float minScale, maxScale;
		public float frequency;

	}

	/**
	 * Rules for generating a star system
	 * 
	 * @author Cow
	 * 
	 */
	public class StarType {
		public String atlasName, textureName;
		public float level;
		public int minPlanets, maxPlanets;
		public float minOrbitDist, maxOrbitDist;
		public HashMap<String, Float> planets = new HashMap<String, Float>();
	}

	// helper classes=======================

	// varblok------------------------------
	private static final String STAR_STYLE_TAG = "star_style";	
	private static CelestialFactory singleton;

	HashMap<String, StarType> starList;
	HashMap<String, PlanetType> planetList;

	// varblok==============================

	//constructors--------------------------
	public static CelestialFactory getSingleton(){
		if(singleton == null){
			singleton = new CelestialFactory();
		}
		return singleton;
	}
	
	private CelestialFactory(){
		starList = new HashMap<String, CelestialFactory.StarType>();
		planetList = new HashMap<String, CelestialFactory.PlanetType>();
	}
	//constructors==========================
	
	// manips-------------------------------

	// universe generator-----
	public Universe generateUniverse(String universeFile) {
		//vars
		Universe u = Universe.getSingleton();
		XmlReader reader = new XmlReader();
		
		//pull xml data
		try {
			Element e = reader.parse(Gdx.files.internal("levels/" + universeFile + ".xml"));

			u.startingRadius = e.getFloat("startingRadius");
			u.mapWidth = e.getFloat("width");
			u.mapHeight = e.getFloat("height");

			for (int i = 0; i < e.getChildCount(); i++) {
				Element child = e.getChild(i);
				if(child.getName().compareTo(STAR_STYLE_TAG) == 0){
					recordStarType(child);
				}
			}
		} catch (IOException e1) {
			Gdx.app.error("CelFactory", e1.getMessage());
			e1.printStackTrace();
		}
		
		//log and return
		Gdx.app.log("CelFactory", "size: (" + u.mapWidth + ", " + u.mapHeight
				+ ") starting: " + u.startingRadius);

		return u;
	}

	// universe generator=====

	// star generation--------
	public Star generateStar(String starTypeName) {
		StarType type = starList.get(starTypeName);
		if (type != null) {
			Star s = new Star();

			s.setImage(type.atlasName, type.textureName);

			return s;
		}
		return null;
	}

	public void recordStarType(Element starDescriptor) {
		if (starDescriptor.getName().compareTo(STAR_STYLE_TAG) == 0) {
			StarType st = new StarType();
			// textures
			st.atlasName = starDescriptor.get("atlas", "gamescreen");
			st.textureName = starDescriptor.get("texture", "black_planet");
			// star data
			st.level = starDescriptor.getFloat("level", 0);
			// planet counts
			st.maxPlanets = starDescriptor.getInt("maxPlanetCount", 0);
			st.minPlanets = starDescriptor.getInt("minPlanetCount", 0);
			// orbit dists
			st.minOrbitDist = starDescriptor.getFloat("minOrbitDist", 500);
			st.maxOrbitDist = starDescriptor.getFloat("maxOrbitDist", 1000);
			recordStarType(starDescriptor.get("name", "default"), st);
		}
	}

	public void recordStarType(String name, StarType st) {
		if(starList.containsKey(name) || starList.containsValue(st)){
			return;
		}
		starList.put(name, st);
	}

	// star generation========

	// planet generation------
	public Planet generatePlanet(String planetType) {
		return null;
	}
	// planet generation======

	// manips===============================
}
