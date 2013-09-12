package com.greatcow.nomad.model;

import com.badlogic.gdx.math.Vector2;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.Economy;

public class PlanetModel {
	public Vector2 position;
	public String atlasName;
	public String textureName;
	public Economy econ;
	
	public PlanetModel(){
		position = Nomad.vectorPool.obtain();
		econ = new Economy();
	}
}
