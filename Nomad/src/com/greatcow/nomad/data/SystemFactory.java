package com.greatcow.nomad.data;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.actors.PlanetActor;
import com.greatcow.nomad.actors.SystemModel;

public class SystemFactory {
	
	// load a map from file----------------------
	
	private static final String level_root = "SystemModel";
	private static final String planet_root = "Planet";
	private static final String atlas_tag = "atlas";
	private static final String texture_tag = "texture";
	private static final String radius_tag = "radius";
	private static final String orbit_angle_tag = "orbit_angle";
	
	public SystemModel loadModel(String file){
		SystemModel model = new SystemModel();

		try {
			XmlReader reader = new XmlReader();
			Element e = reader.parse(Gdx.files.internal("levels/" + file + ".xml"));
			
			if(e.getName().compareTo(level_root) == 0){
				
				PlanetActor sun = new PlanetActor();
				sun.atlasName = e.get(atlas_tag);
				sun.textureName = e.get(texture_tag);
				sun.setPosition(0, 0);
				model.addPlanet(sun);
				
				for(int i = 0; i < e.getChildCount(); i++){
					if(e.getChild(i).getName().compareTo(planet_root) == 0){
						model.addPlanet(createActor(e.getChild(i), i));
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return model;
	}
	
	public PlanetActor createActor(Element e, int count){
		PlanetActor actor = new PlanetActor();
		
		actor.atlasName = e.get(atlas_tag);
		actor.textureName = e.get(texture_tag);
		actor.setOrbitPosition(
				e.getFloat(radius_tag, Nomad.rng.nextFloat() * 100 * count),
				e.getFloat(orbit_angle_tag, Nomad.rng.nextFloat() * 360)
				);
		
		return actor;
	}
	
	// load a map from file======================
	
}
