package com.greatcow.nomad.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.greatcow.nomad.model.Star;
import com.greatcow.nomad.model.Universe;
import com.greatcow.nomad.view.StarView;

public class UniverseScreen implements Screen {

	ArrayList<StarView> viewList;
	Stage stage;
	
	public UniverseScreen(){
		viewList = new ArrayList<StarView>();
		stage = new Stage();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.20f, 0.227f, 0.31f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Universe u = Universe.getSingleton();
		
		for(Star s : u.stars()){
			StarView sv = StarView.obtain(s);
			viewList.add(sv);
			stage.addActor(sv);
		}

	}

	@Override
	public void hide() {
		for(StarView s : viewList){
			s.free();
		}
		viewList.clear();
		

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
