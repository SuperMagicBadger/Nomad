package com.greatcow.nomad;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greatcow.nomad.actors.SystemModel;
import com.greatcow.nomad.data.SystemFactory;
import com.greatcow.nomad.screens.Map;

public class Nomad extends Game {
	//varblok--------------------------
	public static Nomad game;
	public static ShapeRenderer shapeRenderer;
	public static Random rng;
	
	public static final String imageDir = "images/";
	public static final String imageExt = ".pack";
	
	public static final String fontDir = "fonts/";
	public static final String fontExt = ".fnt";
	
	public static final SystemFactory systemFactory = new SystemFactory();
	
	public Map mapScreen;
	//varblok==========================

	@Override
	public void create() {
//		
//		OrthographicCamera cam = new OrthographicCamera();
//		cam.update();
//		Texture t = new Texture(Gdx.files.internal("images/newgame.png"));
//		ShaderProgram shader = new ShaderProgram(
//				Gdx.files.classpath("com/greatcow/nomad/shineys/vert_shader"),
//				Gdx.files.classpath("com/greatcow/nomad/shineys/frag_shader")
//				);
//		
//		float[] verts = new float[20];
//		int i = 0;
//
//		verts[i++] = -1; // x1
//		verts[i++] = -1; // y1
//		verts[i++] = 0;
//		verts[i++] = 0f; // u1
//		verts[i++] = 0f; // v1
//
//		verts[i++] = 1f; // x2
//		verts[i++] = -1; // y2
//		verts[i++] = 0;
//		verts[i++] = 1f; // u2
//		verts[i++] = 0f; // v2
//
//		verts[i++] = 1f; // x3
//		verts[i++] = 1f; // y2
//		verts[i++] = 0;
//		verts[i++] = 1f; // u3
//		verts[i++] = 1f; // v3
//
//		verts[i++] = -1; // x4
//		verts[i++] = 1f; // y4
//		verts[i++] = 0;
//		verts[i++] = 0f; // u4
//		verts[i++] = 1f; // v4
//
//		Mesh mesh = new Mesh(true, 4, 0, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
//		mesh.setVertices( verts );
//		
//		t.bind();
//		shader.begin();
//		shader.setUniformMatrix("u_worldView", cam.projection);
//		shader.setUniformi("u_texture",  0);
//		mesh.render(shader, GL20.GL_TRIANGLES);
//		shader.end();
//		System.out.println("aSdf");
		
		game = this;
		shapeRenderer = new ShapeRenderer();
		rng = new Random();
		
		mapScreen = new Map();
		
		SystemModel model = systemFactory.loadModel("newLevelFormat");
		
		mapScreen.setSystemDraw(model);
		
		setScreen(mapScreen);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public static String texAtlasPath(String file){
		return imageDir + file + imageExt;
	}
	
	public static String fontPath(String file){
		return fontDir + file + fontExt;
	}
}
