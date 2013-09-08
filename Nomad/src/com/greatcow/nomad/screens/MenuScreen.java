package com.greatcow.nomad.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.ArtManager;

public class MenuScreen implements Screen{
	
	//varblok------------------------------------
	Stage stage;
	
	TextureAtlas atlas;
	Skin skin;
	BitmapFont black;
	TextButtonStyle textbuttonstyle;
	
	Image titleimage;
	TextButton newGameButton, continueGameButton;
	//varblok====================================
	
	public MenuScreen() {
		
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
		stage.setViewport(width, height, false);
		titleimage.setPosition(20, Gdx.graphics.getHeight() - titleimage.getHeight() - 20);
		newGameButton.setPosition(Gdx.graphics.getWidth() - newGameButton.getWidth() - 20, 20);
		continueGameButton.setPosition(newGameButton.getX() - (continueGameButton.getWidth() - newGameButton.getWidth()), newGameButton.getY() + continueGameButton.getHeight() + 20);
	}

	@Override
	public void show() {
		//init shit
		atlas = ArtManager.getSingleton().getAtlas("propermain");

		skin = new Skin(atlas);
		stage = new Stage();
		black = new BitmapFont(Gdx.files.internal("fonts/mono_black.fnt"), false);
		textbuttonstyle = new TextButtonStyle();
		
		//make a title
		titleimage = new Image(atlas.findRegion("title"));
		titleimage.setPosition(20, Gdx.graphics.getHeight() - titleimage.getHeight() - 20);
		
		//make a button style
		textbuttonstyle.up = skin.getDrawable("buttonup");
		textbuttonstyle.down = skin.getDrawable("buttondown");
		textbuttonstyle.font = black;

		//make some buttons
		newGameButton = new TextButton("New Command", textbuttonstyle);
		newGameButton.setPosition(Gdx.graphics.getWidth() - newGameButton.getWidth() - 20, 20);
		newGameButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Nomad.game.setScreen(new NewGameScreen());
			}
		});
		
		continueGameButton = new TextButton("Cont. Command", textbuttonstyle);
		continueGameButton.setPosition(newGameButton.getX() - (continueGameButton.getWidth() - newGameButton.getWidth()), newGameButton.getY() + continueGameButton.getHeight() + 20);
		
		//connect shit to the stage
		stage.addActor(newGameButton);
		stage.addActor(continueGameButton);
		stage.addActor(titleimage);
		
		//connect the stage to aught else
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		stage.dispose();
		ArtManager.getSingleton().disposeAtlas("propermain");
		skin.dispose();
		black.dispose();
		
		stage = null;
		atlas = null;
		skin = null;
		black = null;
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
