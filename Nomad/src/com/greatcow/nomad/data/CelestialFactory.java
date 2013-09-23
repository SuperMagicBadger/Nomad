package com.greatcow.nomad.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.model.PlanetModel;
import com.greatcow.nomad.model.Star;
import com.greatcow.nomad.model.Universe;

public class CelestialFactory {

	// helper classes-----------------------

	/**
	 * rules for randomly generating stars
	 * in a universe
	 * 
	 * @author Cow
	 *
	 */
	public class RandomTableType{
		public Rectangle bounds;
		public int count;
		public HashMap<String, Float> frequency = new HashMap<String, Float>();
	}
	
	/**
	 * Rules for generating a planet
	 * 
	 * @author Cow
	 * 
	 */
	public class PlanetType {
		public String atlasName, textureName;
		public float level;
		public float minRadius, maxRadius;
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
	private static final String PLANET_STYLE_TAG = "planet_style";
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
		ArrayList<RandomTableType> tables = new ArrayList<RandomTableType>();
		
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
				} else if(child.getName().compareTo(PLANET_STYLE_TAG) == 0){
					recordPlanetType(child);
				} else if(child.getName().compareTo("random_table") == 0){
					tables.add(randomTable(child));
				}
			}
		} catch (IOException e1) {
			Gdx.app.error("CelFactory", e1.getMessage());
			e1.printStackTrace();
		}
		
		//do the random generation
		for(RandomTableType t : tables){ 
			for(String stName : t.frequency.keySet()){
				final int count = (int) (t.count * t.frequency.get(stName));
				for(int i = 0; i < count; i++){
					Star s = generateStar(stName, Vector2.Zero);
					
				}
			}
		}
		
		//log and return
		Gdx.app.log("CelFactory", "size: (" + u.mapWidth + ", " + u.mapHeight
				+ ") starting: " + u.startingRadius);

		return u;
	}

	public RandomTableType randomTable(Element e){
		if(e.getName().compareTo("random_table") == 0){
			RandomTableType table = new RandomTableType();
			table.bounds = new Rectangle();
			table.bounds.x = e.getFloat("startX", 0);
			table.bounds.y = e.getFloat("startY", 0);
			table.bounds.width = e.getFloat("width");
			table.bounds.height = e.getFloat("height");
			
			Element child;
			
			for(int i = 0; i < e.getChildCount(); i++){
				child = e.getChild(i);;
				String name = child.get("star", "default");
				float freq = child.getFloat("frequency", 0);
				
				table.frequency.put(name, freq);
			}
			
			return table;
		}
		return null;
	}
	// universe generator=====

	// star generation--------
	public Star generateStar(String starTypeName, Vector2 position){
		return generateStar(starTypeName, position.x, position.y);
	}
	public Star generateStar(String starTypeName, float x, float y) {
		StarType type = starList.get(starTypeName);
		if (type != null) {
			Star s = new Star();

			s.setImage(type.atlasName, type.textureName);
			s.position.set(x, y);
			
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
			//add to collection
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
	public PlanetModel generatePlanet(String planetType) {
		PlanetType pt = planetList.get(planetType);
		if(pt != null){
			PlanetModel p = new PlanetModel();
			
			p.atlasName = pt.atlasName;
			p.textureName = pt.textureName;
			
			return p;
		} 
		return null;
	}
	
	public void recordPlanetType(Element planetDescriptor){
		if(planetDescriptor.getName().compareTo(PLANET_STYLE_TAG) == 0){
			PlanetType pt = new PlanetType();
			//textures
			pt.atlasName = planetDescriptor.get("atlas");
			pt.textureName = planetDescriptor.get("texture");
			//planet data
			pt.minRadius = planetDescriptor.getFloat("minRadius");
			pt.maxRadius = planetDescriptor.getFloat("maxRadius");
			//add to collection
			recordPlanetType(planetDescriptor.get("name", "default"), pt);
		}
	}
	
	public void recordPlanetType(String name, PlanetType type){
		if(planetList.containsKey(name) || planetList.containsValue(type)){
			return;
		}
		planetList.put(name, type);
	}
	// planet generation======

	// manips===============================
}
