package com.greatcow.nomad.view;


import Data.ArtManager;
import Data.LevelManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greatcow.nomad.model.Command;
import com.greatcow.nomad.model.PlanetStyle;
import com.greatcow.nomad.model.UnitStyle;

public class MapScreen implements Screen {
	// varblok------------------------------------
	// render data
	TextureAtlas atlas;
	Skin skin;
	BitmapFont font;
	Group unitBlock;
	Stage stage;

	// unit data
	private UnitStyle unitStyle;
	private PlanetStyle planetStyle;

	// unit buttons
	UnitButtonListener unitButtonListener;
	Table unitUITable;
	TextButtonStyle unitButtonStyle;
	TextButton moveBtn, attackBtn;

	// ui buttons
	UIButtonListener uiButtonListener;
	Table uitable;
	TextButtonStyle uiTextButtonStyle;
	ImageButtonStyle uiImageButtonStyle;
	TextButton endTurnButton;
	ImageButton sliderButton;

	// state data
	public boolean menuActive = false;
	public boolean selectCircleActive = false;

	// varblok====================================

	// gdx controls-------------------------------
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.20f, 0.227f, 0.31f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Table.drawDebug(stage);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void show() {
		atlas = ArtManager.getSingleton().getAtlas("gamescreen");
		skin = new Skin(atlas);
		font = ArtManager.getSingleton().getFont("mono_white");
		stage = new Stage();
		unitButtonListener = new UnitButtonListener();
		uiButtonListener = new UIButtonListener();
		unitBlock = new Group();

		Command.mapgroup = unitBlock;
		Command.createCommand(3);

		// UNITS________________________________________________________________

		// initialize the main unit style
		unitStyle = Command.registerStyle("basic_style", new UnitStyle());
		unitStyle.attackCircleColor = Color.RED;
		unitStyle.moveCircleColor = Color.BLUE;
		unitStyle.enemySprite = atlas.findRegion("units_enemy");
		unitStyle.friendlySprite = atlas.findRegion("units_friendly");

		// the buttons for he unit. use null to get
		// a clear background. text color determines
		// u or down-ness
		unitButtonStyle = new TextButtonStyle();
		unitButtonStyle.fontColor = Color.WHITE;
		unitButtonStyle.downFontColor = Color.RED;
		unitButtonStyle.font = font;

		// and the control buttons use the standard grey
		// backgrounds
		uiTextButtonStyle = new TextButtonStyle();
		uiTextButtonStyle.down = skin.getDrawable("buttondown");
		uiTextButtonStyle.up = skin.getDrawable("buttonup");
		uiTextButtonStyle.font = font;

		// set up the buttons
		attackBtn = new TextButton("Attack", unitButtonStyle);
		attackBtn.addListener(unitButtonListener);

		moveBtn = new TextButton("Move", unitButtonStyle);
		moveBtn.addListener(unitButtonListener);

		// set the uitable to a size that can contain both buttons
		unitUITable = new Table();
		unitUITable.debug();

		unitUITable.setWidth(Math.max(moveBtn.getWidth(), attackBtn.getWidth()));
		unitUITable.setHeight(moveBtn.getHeight() + attackBtn.getHeight());

		unitUITable.add(attackBtn).left();
		unitUITable.row();
		unitUITable.add(moveBtn).left();
		unitUITable.setPosition(100, 100);
		unitUITable.setVisible(false);

		unitUITable.setBackground(skin.newDrawable("buttonborder"));

		unitBlock.addActor(unitUITable);
		
		// UNITS__________________________________________________________________________
		
		// PLANETS________________________________________________________________________
		
		planetStyle = new PlanetStyle();
		planetStyle.planetImage = atlas.findRegion("black_circle");
		
		// PLANETS________________________________________________________________________
		
		// CONTROLS_______________________________________________________________________

		// set up the styles
		// image button, to show/hide the menu
		uiImageButtonStyle = new ImageButtonStyle();
		uiImageButtonStyle.up = skin.getDrawable("arrowup");
		uiImageButtonStyle.down = skin.getDrawable("arrowdown");

		// menu buttons
		uiTextButtonStyle = new TextButtonStyle();
		uiTextButtonStyle.font = font;
		uiTextButtonStyle.fontColor = Color.WHITE;
		uiTextButtonStyle.downFontColor = Color.RED;

		// create teh buttons
		// show/hide
		sliderButton = new ImageButton(uiImageButtonStyle);

		// menu buttons
		endTurnButton = new TextButton("End Turn", uiTextButtonStyle);
		endTurnButton.addListener(uiButtonListener);

		// fill the table
		uitable = new Table();
		uitable.debug();

		uitable.setPosition(Gdx.graphics.getWidth() - endTurnButton.getWidth(),
				0);
		uitable.setWidth(endTurnButton.getWidth());
		uitable.setHeight(Gdx.graphics.getHeight());

		uitable.bottom().right().add(endTurnButton);

		stage.addActor(uitable);
		
		// CONTROLS_______________________________________________________________________

		
		stage.addActor(unitBlock);
		InputMultiplexer im = new InputMultiplexer(stage, new GestureDetector(
				new stageInput()));
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		ArtManager.getSingleton().disposeAtlas("gamescreen");
		ArtManager.getSingleton().disposeFont("mono_white");
	}

	// gdx controls===============================

	// map screen controls------------------------
	private void centerMenuOnUnit(Unit u) {
		if (u == null) {
			unitUITable.setVisible(false);
			menuActive = false;

		} else {
			unitUITable.setPosition(u.getX() + u.getWidth() + 10, u.getY());
			unitUITable.setVisible(true);
			menuActive = true;
		}
	}
	// map screen controls========================
	
	
	
	// helpers------------------------------------

		// gesture actions
		private class stageInput implements GestureListener {

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {
				
				//test for units
				Unit u = Command.activeCommand().getFriendlyUnitAtScreen(x, y);
				//Unit active = Command.activeCommand().setActiveUnit(u);
				if(u != null){
					centerMenuOnUnit(u);
				} else if (Command.activeCommand().activeUnit == null){
					
				}
				
				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				unitBlock.translate(deltaX, -deltaY);
				return true;
			}

			@Override
			public boolean zoom(float initialDistance, float distance) {
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
					Vector2 pointer1, Vector2 pointer2) {
				return false;
			}

		}

		// button actions
		private class UnitButtonListener extends ClickListener {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getListenerActor() == attackBtn) {
					Command.activeCommand().activeUnit.startCombatOrder();
				} else if (event.getListenerActor() == moveBtn) {
					Command.activeCommand().activeUnit.startMoveOrder();
				}
			}
		}

		private class UIButtonListener extends ClickListener {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getListenerActor() == endTurnButton) {
					if (menuActive) {
						centerMenuOnUnit(null);
					}
					Command.nextTurn();
				}
			}
		}

		// helpers====================================
	
}
