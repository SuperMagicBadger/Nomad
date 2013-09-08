package com.greatcow.nomad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.ArtManager;
import com.greatcow.nomad.model.Planet;

public class PlanetInfoScr implements Screen{

	// varblok-------------------------
	//info source
	Planet focus;
	
	//resources
	TextureAtlas atlas;
	Skin skin;
	BitmapFont font;
	TextButtonStyle tbStyle;
	
	//ui layout
	Stage stage;
	
	//ui elements
	TextButton back;
	
	// varblok=========================	
	
	// constructor---------------------
	public PlanetInfoScr(Planet target){
		focus = target;
	}
	// constructor=====================
	
	// screen-------------------------------
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.20f, 0.227f, 0.31f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		//setup the atlas
		stage = new Stage();
		atlas = ArtManager.getSingleton().getAtlas("gamescreen");
		skin = new Skin(atlas);
		font = ArtManager.getSingleton().getFont("mono_white");
		
		//setup the styles
		tbStyle = new TextButtonStyle();
		tbStyle.up = skin.getDrawable("buttonup");
		tbStyle.down = skin.getDrawable("buttondown");
		tbStyle.font = font;
		
		back = new TextButton("Back", tbStyle);
		back.setPosition(0, 0);
		back.addListener(new uiButtonListener());
		
		stage.addActor(back);
		
		Gdx.input.setInputProcessor(stage);
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
		
	}
	// screen===============================
	
	// planet focus-------------------------
	
	// planet focus=========================
	
	// helpers------------------------------
	private class uiButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			Nomad.game.setScreen(new MapScreen());
		}
	}
	// helpers==============================
}
