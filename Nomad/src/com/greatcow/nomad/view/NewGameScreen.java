package com.greatcow.nomad.view;

import Data.ArtManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
	//layouts
	SplitPane scrollerPane;
	Table scrollerTable;
	
	//buttons
	//labels
	// Varblok===================================
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		//get image data
		font = ArtManager.getSingleton().getFont("mono_white");
		atlas = ArtManager.getSingleton().getAtlas("");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
