package com.greatcow.nomad.actors;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.data.ArtManager;

public class CommandRing extends Actor {
	// varblok-------------------------
	private Actor target;
	private ArrayList<CommandData> commandList;
	private int activeCommand;
	private boolean ringDirty;

	private String defaultRingAtlas, defaultRingTexture;
	private String fontName;

	private TextureRegion ring;
	private BitmapFont font;

	// varblok=========================

	// helpers-------------------------
	public class CommandData {
		public String commandText;
		public String indicatorAtlas, indicatorTexture;
		public CommandListener listener;
	}

	public interface CommandListener{
		public void onCommand();
	}
	// helpers=========================

	public CommandRing(String fontName, String defaultAtlas,
			String defaultTexture) {
		this.fontName = fontName;
		defaultRingAtlas = defaultAtlas;
		defaultRingTexture = defaultTexture;
		ringDirty = true;
		activeCommand = -1;
		commandList = new ArrayList<CommandRing.CommandData>();
	}

	/**
	 * set the target on which to draw the command ring
	 * 
	 * @param a
	 */
	public void setTarget(Actor a) {
		target = a;
		if(a == null){
			releaseAssets();
			setVisible(false);
		} else {
			a.toFront();
			setZIndex(a.getZIndex() - 1);
			setVisible(true);
			acquireAssets();
		}
	}
	
	// load and release resources--------------------------
	public void acquireAssets() {
		acquireRing();
	}

	public void releaseAssets() {
		if (font != null) {
			ArtManager.getSingleton().disposeFont(fontName);
			font = null;
		}
		if (ring != null) {
			ArtManager.getSingleton().disposeAtlas(defaultRingAtlas);
			ring = null;
		}
	}

	private void acquireRing(){
		if(ringDirty){
			releaseRing();
			System.out.println("switch to " + activeCommand);
			if(activeCommand < commandList.size() && activeCommand >= 0){
				ring = ArtManager.getSingleton().getAtlas(commandList.get(activeCommand).indicatorAtlas).findRegion(commandList.get(activeCommand).indicatorTexture);
			} else {
				ring = ArtManager.getSingleton().getAtlas(defaultRingAtlas).findRegion(defaultRingTexture);
			}
			setWidth(ring.getRegionWidth());
			setHeight(ring.getRegionHeight());
			ringDirty = false;
		}
	}
	
	private void releaseRing(){
		ArtManager.getSingleton().disposeAtlas(defaultRingAtlas);
		ring = null;
		ringDirty = true;
	}
	// load and release resources==========================
	
	// rendering-------------------------------------------
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(ring == null || ringDirty){
			acquireRing();
		} else if (target != null && target instanceof UnitActor) {
			UnitActor ua = (UnitActor) target;
			batch.draw(ring, ua.centerX() - getWidth() / 2f, ua.centerY() - getHeight() / 2f);
		}
	}
	// rendering===========================================
	
	// commands--------------------------------------------
	public void addCommand(String commandName, String circleAtlas, String circleTexture, CommandListener listener){
		CommandData c = new CommandData();
		c.commandText = commandName;
		c.indicatorAtlas = circleAtlas;
		c.indicatorTexture = circleTexture;
		c.listener = listener;
		commandList.add(c);
	}
	
	public void addCommand(String commandName, CommandListener listener){
		addCommand(commandName, null, null, listener);
	}
	
	public void addCommand(String commandName){
		addCommand(commandName, null, null, null);
	}

	public CommandData getCommand(int i){
		if(i >= 0 && i < commandList.size()){
			return commandList.get(i);
		}
		return null;
	}
	
	public void setCommand(int command){
		activeCommand = command;
		trigerCommand(command);
		ringDirty = true;
	}
	
	public void setCommand(float xDir, float yDir){
		ringDirty = true;
	}
	
	public void trigerCommand(int i){
		CommandData cd = getCommand(i);
		if(cd != null && cd.listener != null){
			cd.listener.onCommand();
		}
	}
	
	public void showCommands(){
		
	}
	
	public void hideCommands(){
		
	}
	// commands============================================

}
