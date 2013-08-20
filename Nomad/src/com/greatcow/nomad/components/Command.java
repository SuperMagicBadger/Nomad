package com.greatcow.nomad.components;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.components.Unit.UnitState;

public class Command{
	//varblok----------------------------------------------
	private static Group mapgroup;
	//turn management
	static private ArrayList<Command> comandList = new ArrayList<Command>();
	static private int activeTurn = 0;
	//team management
	private HashMap<String, UnitStyle> styleList;
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
		styleList = new HashMap<String, UnitStyle>();
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
	public UnitStyle registerStyle(String name, UnitStyle style){
		 styleList.put(null, style);
		return style;
	}

	
	public Unit addUnitAt(String stylename, float x, float y){
		return null;
	}
	
	public Unit addUnitAtScreen(String stylename, float screenX, float screenY){
		return null;
	}
	// manips==============================================
	
	// access----------------------------------------------
	public UnitStyle getStyle(String name){
		if(styleList.containsKey(name)){
			return styleList.get(name);
		}
		return null;
	}
	
	public Unit getUnitAt(float x, float y){
		Actor a = mapgroup.hit(x, y, true);
		if(a != null && a instanceof Unit){
			return (Unit) a;
		}
		return null;
	}
	
	public Unit getUnitAtScreen(float screenX, float screenY){
		Vector2 workingvector = Nomad.vectorPool.obtain();
		workingvector.set(screenX, screenY);
	    workingvector = mapgroup.screenToLocalCoordinates(workingvector);
		Unit u = getUnitAt(workingvector.x, workingvector.y);
		Nomad.vectorPool.free(workingvector);
		return u;
	}
	// access==============================================
	
	// delete stuff----------------------------------------
	public UnitStyle unregisterStyle(String style){
		if(styleList.containsKey(style)){
			return styleList.get(style);
		}
		return null;
	}
	// delete stuff========================================
	
	//command processing-----------------------------------
	public Unit createUnit(UnitStyle style, float x, float y){
		Unit u = new Unit(style);
		u.setAsFriendly();
		u.setPosition(x, y);
		unitList.add(u);
		return u;
	}
	
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
