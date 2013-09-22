package com.greatcow.nomad.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Universe {	
	// varblok-------------------------
	public static Universe singleton;
	public ArrayList<Star> starlist;
	private Node quadtree;
	
	public float startingRadius = 0;
	public float mapWidth = 0;
	public float mapHeight = 0;
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
	public boolean addStar(Star s){
		
		quadtree.addStar(s);
		starlist.add(s);
		return true;
	}
	public void addStar(Star[] s){
		for(Star star : s){
			addStar(star);
		}
	}
	// manips====================================

	// map queries---------------------------------------------
	
	public Star nearestStar(float x, float y){
		return null;
	}
	
	public Star nearestStar(Vector2 pos){
		return nearestStar(pos.x, pos.y);
	}
	
	// map queries=================================
	
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
