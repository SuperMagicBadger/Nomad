package com.greatcow.nomad.view;

import Data.ArtManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
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
	
	class StartButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// TODO Auto-generated method stub
			super.clicked(event, x, y);
		}
	}
	
	class CancelButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// TODO Auto-generated method stub
			super.clicked(event, x, y);
		}
	}
	
	class LevelNameListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// TODO Auto-generated method stub
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
		Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
		newGameButton.setPosition(Gdx.graphics.getWidth() - newGameButton.getWidth() - 20, 20);
		
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
		
		spinnerStyle = new SpinnerStyle();
		spinnerStyle.upArrowUp = skin.getDrawable("uparrowup");
		spinnerStyle.upArrowDown = skin.getDrawable("uparrowdown");
		spinnerStyle.downArrowUp = skin.getDrawable("downarrowup");
		spinnerStyle.downArrowDown = skin.getDrawable("downarrowdown");
		spinnerStyle.font = font;
		spinnerStyle.fontColor = Color.BLACK;
		
		//set up buttons
		playerCounter = new Spinner(spinnerStyle);
		playerCounter.setPosition(100, 150);
		
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
		
		stage.addActor(newGameButton);
		stage.addActor(backButton);
		stage.addActor(playerCounter);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		ArtManager.getSingleton().disposeAtlas("newgame");
		ArtManager.getSingleton().disposeFont("mono_white");		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
