package com.greatcow.nomad.model;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.greatcow.nomad.data.CelestialFactory;

public class Universe {	
	// varblok-------------------------
	public static Universe singleton;
	public ArrayList<Star> starlist;
	private Node quadtree;
	
	private float startingRadius = 0;
	private float mapWidth = 0;
	private float mapHeight = 0;
	// varblok=========================

	// constructors------------------------------
	public static Universe getSingleton() {
		if (singleton == null) {
			singleton = new Universe();
		}
		return singleton;
	}

	private Universe() {
		starlist = new ArrayList<Star>();
		quadtree = new Node(-mapWidth / 2f, -mapHeight / 2f, mapWidth / 2f, mapHeight / 2f);
	}

	// constructors==============================

	// access------------------------------------
	public Star[] stars(){
		Star[] ret = new Star[starlist.size()];
		
		for(int i = 0; i < ret.length; i++){
			ret[i] = starlist.get(i);
		}
		
		return ret;
	}
	// access====================================

	// manips------------------------------------
	// manips====================================

	// universe generation-----------------------
	public void initUniverse() throws IOException {
		XmlReader reader = new XmlReader();
		
		Element e = reader.parse(Gdx.files.internal("levels/universe.xml"));
		startingRadius = e.getFloat("startingRadius");
		mapWidth = e.getFloat("width");
		mapHeight = e.getFloat("height");
		
		for(int i = 0; i < e.getChildCount(); i++){
			
		}
		
		Gdx.app.log("Universe", "size: (" + mapWidth + ", " + mapHeight + ") starting: " + startingRadius);

		Star s = new Star();
		s.atlasName = "gamescreen";
		s.textureName = "black_sun";
		quadtree.addStar(s);
		starlist.add(s);
	}

	// universe generation=======================

	// helpers-----------------------------------
	/**
	 * quadtree node
	 * 
	 * @author Cow
	 * 
	 */
	private class Node {
		Rectangle box;
		Node NE, SE, NW, SW;
		Star child;

		Node(float x, float y, float width, float height) {
			box = new Rectangle(x, y, width, height);
		}

		public boolean addStar(Star child) {
			// test to see if it fits
			if (contains(child)) {

				// check to see if this node is empty
				if (this.child == null && NE == null) {
					this.child = child;
					return true;
				}

				// split if necessary
				if (NE == null) {
					split();
				}

				// add them to the splits
				if (NE.addStar(child))
					return true;
				if (SE.addStar(child))
					return true;
				if (NW.addStar(child))
					return true;
				if (SW.addStar(child))
					return true;
			}
			return false;
		}

		public boolean contains(Star child) {
			return box.contains(child.position.x, child.position.y);
		}

		public void split() {
			float x = box.y;
			float y = box.y;
			float width = box.width;
			float height = box.height;
			if (NE == null) {
				NE = new Node(x, y + height / 2f, width / 2f, height / 2f);
				SE = new Node(x, y, width / 2f, height / 2f);
				NW = new Node(x + width / 2f, y, width / 2f, height / 2f);
				SW = new Node(x + width / 2f, y + height / 2f, width / 2f,
						height / 2f);
			}
		}
	}
	// helpers===================================
}
