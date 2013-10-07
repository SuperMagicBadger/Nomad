package com.greatcow.nomad.actors;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 
 * @author Cow
 *
 * @param <targetType> the target type that the selection indicator will work with
 */
public class SelectionIndicatorActor <targetType> extends Actor{
	// varblok-------------------------
	private targetType target;
	private ArrayList<CommandData> commandList;
	private int activeCommand;
	// varblok=========================
	
	// helpers-------------------------
	public class CommandData{
		public String commandText;
		public String indicatorAtlas, indicatorTexture;
	}
	// helpers=========================
	
	public void setTarget(targetType a){
		if(a instanceof UnitActor){
			target = a;
		} else if(a instanceof PlanetActor){
			target = a;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(target instanceof Actor){
			if(activeCommand < commandList.size()){
				
			}
		}
	}
	
}
