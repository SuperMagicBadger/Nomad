package com.greatcow.nomad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.greatcow.nomad.actors.SystemModel;

public class Map  implements Screen{

	private SystemModel systemModel;
	
	public Map(){
	}
	
	public void setSystemDraw(SystemModel system){
		systemModel = system;
		systemModel.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		systemModel.getCamera().translate(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0f);
		Gdx.input.setInputProcessor(systemModel.multiplexer);
	}
	
	// Screen------------------------------------
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(systemModel != null){
			systemModel.act(delta);
			systemModel.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		if(systemModel != null){
			systemModel.setViewport(width, height, false);
			systemModel.bg.resize(width, height);
		}
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		if(systemModel != null) systemModel.releaseImages();
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
	// Screen====================================
}
