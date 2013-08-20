package com.greatcow.nomad.view;

import Data.ArtManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		//get image data
		font = ArtManager.getSingleton().getFont("mono_white");
		atlas = ArtManager.getSingleton().getAtlas("newgame");
		skin = new Skin(atlas);
		
		//set up button styles
		tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonup");
		tbStyle.down = skin.getDrawable("buttondown");
		tbStyle.font = font;
		tbStyle.fontColor = Color.WHITE;
		tbStyle.downFontColor = Color.DARK_GRAY;
		
		//set up buttons
		newGameButton = new TextButton("Start Game", tbStyle);
		backButton = new TextButton("Back", tbStyle);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Nomad.game.setScreen(new MenuScreen());
			}
		});
		
		
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
