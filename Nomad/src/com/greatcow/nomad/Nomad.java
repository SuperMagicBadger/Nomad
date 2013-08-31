package com.greatcow.nomad;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.greatcow.nomad.view.MenuScreen;

public class Nomad extends Game {
	//varblok--------------------------
	public static Nomad game;
	public static ShapeRenderer shapeRenderer;
	public static Pool<Vector2> vectorPool;
	public static Random rng;
	//varblok==========================

	@Override
	public void create() {
		game = this;
		shapeRenderer = new ShapeRenderer();
		rng = new Random();
		
		vectorPool = new Pool<Vector2>(){
			@Override
			protected Vector2 newObject() {
				return new Vector2(0, 0);
			}
		};
		
		setScreen(new MenuScreen());
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
