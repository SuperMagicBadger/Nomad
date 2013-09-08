package com.greatcow.nomad.data;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.greatcow.nomad.model.Planet;
import com.greatcow.nomad.model.Star;

public class CelestialFactory {

	// helper classes-----------------------
	
	/**
	 * Rules for generating a planet
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
	private static final String STAR_DESCRIPTOR_NAME = "star_style";
	public static final CelestialFactory singleton = new CelestialFactory();
	
	HashMap<String, StarType> starList;
	HashMap<String, PlanetType> planetList;
	// varblok==============================
	
	// manips-------------------------------
	
	//star generation--------
	public Star generateStar(String starTypeName){
		StarType type = starList.get(starTypeName);
		if(type != null){
			Star s = new Star();
			
			s.setImage(type.atlasName, type.textureName);
			
			return s;
		}
		return null;
	}
	
	public void recordStarType(Element starDescriptor){
		if(starDescriptor.getName().compareToIgnoreCase("star_style") == 0){
			StarType st = new StarType();
			//textures
			st.atlasName = starDescriptor.get("atlas", "gamescreen");
			st.textureName = starDescriptor.get("texture", "black_planet");
			//star data
			st.level = starDescriptor.getFloat("level", 0);
			//planet counts
			st.maxPlanets = starDescriptor.getInt("maxPlanetCount", 0);
			st.minPlanets = starDescriptor.getInt("minPlanetCount", 0);
			//orbit dists
			st.minOrbitDist = starDescriptor.getFloat("minOrbitDist", 500);
			st.maxOrbitDist = starDescriptor.getFloat("maxOrbitDist", 1000);
			
		}
	}
	
	public void recordStarType(StarType st){
		
	}
	//star generation========
	
	//planet generation------
	public Planet generatePlanet(String planetType){
		return null;
	}
	//planet generation======
	
	// manips===============================
}
