package com.greatcow.nomad.view;

import Data.ArtManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.widgets.Spinner;
import com.greatcow.nomad.widgets.SpinnerStyle;

public class NewGameScreen implements Screen{

	// HELPERS-----------------------------------	
	class LevelNameListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
		}
	}
	
	// HELPERS===================================
	
	// Varblok-----------------------------------
	//image data
	TextureAtlas atlas;
	Skin skin;
	BitmapFont font;
	//styles
	TextButtonStyle tbStyle;
	ImageButtonStyle uArrowStyle;
	ImageButtonStyle dArrowStyle;
	LabelStyle lbStyle;
	SpinnerStyle spinnerStyle;
	//layouts
	Stage stage;
	SplitPane scrollerPane;
	Table scrollerTable;
	Table spinnerTable;
	//buttons
	Spinner playerCounter;
	TextButton newGameButton;
	TextButton backButton;
	//labels
	Label playerCounterLabel;
	Label titleLabel;
	Label mapsLabel;
	// Varblok===================================
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.20f, 0.227f, 0.31f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
		newGameButton.setPosition(Gdx.graphics.getWidth() - newGameButton.getWidth() - 20, 20);
		spinnerTable.setPosition(50, (Gdx.graphics.getHeight() / 2f) - 50);
		titleLabel.setPosition(20, Gdx.graphics.getHeight() - titleLabel.getHeight() - 20);
	}

	@Override
	public void show() {
		//get image data
		font = ArtManager.getSingleton().getFont("mono_white");
		atlas = ArtManager.getSingleton().getAtlas("newgame");
		skin = new Skin(atlas);
		stage = new Stage();
		
		//set up button styles
		tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonup");
		tbStyle.down = skin.getDrawable("buttondown");
		tbStyle.font = font;
		tbStyle.fontColor = Color.BLACK;
		
		lbStyle = new LabelStyle();
		lbStyle.font = font;
		lbStyle.fontColor = Color.BLACK;
		
		spinnerStyle = new SpinnerStyle();
		spinnerStyle.upArrowUp = skin.getDrawable("uparrowup");
		spinnerStyle.upArrowDown = skin.getDrawable("uparrowdown");
		spinnerStyle.downArrowUp = skin.getDrawable("downarrowup");
		spinnerStyle.downArrowDown = skin.getDrawable("downarrowdown");
		spinnerStyle.font = font;
		spinnerStyle.fontColor = Color.BLACK;
		
		//set up buttons
		playerCounter = new Spinner(spinnerStyle);
		
		newGameButton = new TextButton("Start Game", tbStyle);
		newGameButton.setPosition(Gdx.graphics.getWidth() - newGameButton.getWidth() - 20, 20);
		newGameButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Nomad.game.setScreen(new MapScreen());
			}
		});
		
		backButton = new TextButton("Back", tbStyle);
		backButton.setPosition(20, 20);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Nomad.game.setScreen(new MenuScreen());
			}
		});
		
		//setup labels
		titleLabel = new Label("New Game:", lbStyle);
		titleLabel.setFontScale(1.5f);
		titleLabel.setPosition(20, Gdx.graphics.getHeight() - titleLabel.getHeight() - 20);
		
		playerCounterLabel = new Label("Player Count", lbStyle);
		playerCounterLabel.setFontScale(0.75f);
		
		//set up layouts
		spinnerTable = new Table();
		spinnerTable.add(playerCounterLabel);
		spinnerTable.row();
		spinnerTable.add(playerCounter);
		spinnerTable.pack();
		
		//and bring everything together		
		stage.addActor(spinnerTable);
		stage.addActor(titleLabel);
		stage.addActor(newGameButton);
		stage.addActor(backButton);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		ArtManager.getSingleton().disposeAtlas("newgame");
		ArtManager.getSingleton().disposeFont("mono_white");		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
