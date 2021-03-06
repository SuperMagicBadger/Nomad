package com.greatcow.nomad.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.model.Unit.UnitState;
import com.greatcow.nomad.style.UnitStyle;

/*
 * The command class represents a player and groups all
 * the units he controls.  It has a reference to the map
 * screen to allow it to manage which units appear on screen.
 * 
 * It maintains a list of styles to facilitate easier resource
 * management and unit creation.
 * 
 * It also maintains a list of command's and manages turn order.
 * It signals each command when its turn begins and ends.
 */

public class Command extends Group{
	//varblok----------------------------------------------
	private static HashMap<String, UnitStyle> styleList = new HashMap<String, UnitStyle>();
	//turn management
	static private ArrayList<Command> comandList = new ArrayList<Command>();
	static private int activeTurn = 0;
	//team management
	private ArrayList<Unit> unitList;
	private int teamNumber;
	public Unit activeUnit;
	//varblok==============================================
	
	//constructors-----------------------------------------
	public static int createCommand(){
		Command c = new Command(comandList.size());
		comandList.add(c);
		return c.teamNumber;
	}
	public static void createCommand(int players){
		for(int i = 0; i < players; i++){
			createCommand();
		}
	}
	public Command(int teamNum){
		unitList = new ArrayList<Unit>();
		teamNumber = teamNum;
		activeUnit = null;
	}
	//constructors=========================================
	
	//turn swapping----------------------------------------
	public static Command activeCommand(){
		return comandList.get(activeTurn);
	}
	public static Command nextTurn(){
		activeCommand().endTurn();
		activeTurn = activeTurn + 1 < comandList.size() ? activeTurn + 1 : 0; 
		activeCommand().startTurn();
		return activeCommand();
	}
	private void startTurn(){
		for(Unit u : unitList){
			u.startTurn();
		}
	}
	private void endTurn(){
		for(Unit u : unitList){
			u.endTurn();
		}
	}
	//turn swapping========================================
	
	// manips----------------------------------------------
	public static UnitStyle registerStyle(String name, UnitStyle style){
		 styleList.put(name, style);
		return style;
	}

	
	public Unit addFriendlyUnitAt(String stylename, float x, float y){
		UnitStyle ustyle = getStyle(stylename);
		if(ustyle != null){
			Unit u = new Unit(ustyle, x, y);
			unitList.add(u);
			addActor(u);
			return u;
		}
		return null;
	}
	
	public Unit addFriendlyUnitAtScreen(String stylename, float screenX, float screenY){
		Vector2 workingvector = Nomad.vectorPool.obtain();
		workingvector.set(screenX, screenY);
		workingvector = screenToLocalCoordinates(workingvector);
		return getFriendlyUnitAt(workingvector.x, workingvector.y);
	}
	// manips==============================================
	
	// access----------------------------------------------
	public static UnitStyle getStyle(String name){
		if(styleList.containsKey(name)){
			return styleList.get(name);
		}
		return null;
	}
	
	public Unit getFriendlyUnitAt(float x, float y){
		Actor a = hit(x, y, true);
		if(a != null && a instanceof Unit){
			return (Unit) a;
		}
		return null;
	}
	
	public Unit getFriendlyUnitAtScreen(float screenX, float screenY){
		Vector2 workingvector = Nomad.vectorPool.obtain();
		workingvector.set(screenX, screenY);
	    workingvector = screenToLocalCoordinates(workingvector);
		Unit u = getFriendlyUnitAt(workingvector.x, workingvector.y);
		Nomad.vectorPool.free(workingvector);
		return u;
	}
	
	public static Unit getUnitAt(float x, float y){
		Actor a = activeCommand().hit(x, y, true);
		if(a != null && a instanceof Unit){
			return (Unit) a;
		}
		return null;
	}
	
	public static Unit getUnitAtScreen(float screenX, float screenY){
		Vector2 workingvector = Nomad.vectorPool.obtain();
		workingvector = activeCommand().screenToLocalCoordinates(workingvector);
		return getUnitAt(workingvector.x, workingvector.y);
	}
	// access==============================================
	
	// delete stuff----------------------------------------
	public static UnitStyle unregisterStyle(String style){
		if(styleList.containsKey(style)){
			return styleList.get(style);
		}
		return null;
	}
	
	public Unit removeFriendlyUnitAt(float x, float y){
		Unit u = getFriendlyUnitAt(x, y);
		if(u != null){
			unitList.remove(u);
			removeActor(u);
		}
		return u;
	}
	
	public Unit removeFriendlyUnitAtScreen(float screenX, float screenY){
		Unit u = getFriendlyUnitAtScreen(screenX, screenY);
		if(u != null){
			unitList.remove(u);
			removeActor(u);
		}
		return u;
	}
	// delete stuff========================================
	
	//command processing-----------------------------------	
	public Unit setActiveUnit(Unit u){
		if(u == null || unitList.contains(u)){
			if(activeUnit != null && activeUnit.ustate != UnitState.Idle){
				activeUnit.setToIdle();
			} else {
				activeUnit = u;
			}
			return activeCommand().activeUnit;
		} 
		return null;
	}
	//command processing===================================
	
}
