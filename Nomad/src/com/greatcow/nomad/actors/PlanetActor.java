package com.greatcow.nomad.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.ArtManager;

public class PlanetActor extends Actor {
	// varblok-------------------------
	// flags
	public boolean disposed = false;
	// position data
	// drawing
	public String atlasName, textureName;
	public TextureRegion image;
	// collision
	private Circle colCircle;

	// varblok=========================

	// drawing-------------------------
	public void acquireRegion() {
		image = ArtManager.getSingleton().getAtlas(atlasName)
				.findRegion(textureName);
		if (image != null) {
			setWidth(image.getRegionWidth());
			setHeight(image.getRegionHeight());
			colCircle = new Circle(getX() + getWidth() / 2f, getY() + getHeight() / 2f, getWidth() / 2f);
		}
	}

	public void releaseRegion() {
		ArtManager.getSingleton().disposeAtlas(atlasName);
		image = null;
		colCircle = null;
	}

	public void setOrbitPosition(float radius, float theta) {

		float x = (float) (radius * Math.cos(Math.toRadians(theta)));
		float y = (float) (radius * Math.sin(Math.toRadians(theta)));

		Gdx.app.log("PlanetActor", "Placeing at r: " + radius + " theta "
				+ theta + " x: " + x + " y: " + y);

		setPosition(x, y);
	}

	public float getOrbitRadius() {
		return (float) Math.sqrt(getX() * getX() + getY() * getY());
	}

	public float getOrbitTheta() {
		return (float) Math.toDegrees(Math.atan(getY() / getX()));
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if(colCircle != null){
			colCircle.x = x;
			colCircle.y = y;
		}
	}
	
	@Override
	public void translate(float x, float y) {
		super.translate(x, y);
		if(colCircle != null){
			colCircle.x += x;
			colCircle.y += y;
		}
	}

	// drawing=========================

	
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		Actor a = super.hit(x, y, touchable);
		
		if(a == this){
			if(colCircle == null){
				acquireRegion();
				return null;
			}
			if(colCircle.contains(x, y)){
				return this;
			} else {
				return null;
			}
		}
		
		return a;
	}
	
	// Actors--------------------------
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		if (getOrbitRadius() > 0) {

			batch.end();
			ShapeRenderer shapeRenderer = Nomad.shapeRenderer;

			shapeRenderer.begin(ShapeType.Circle);
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
			shapeRenderer.setColor(0, 1, 0, 1);
			shapeRenderer.circle(0, 0, getOrbitRadius());
			shapeRenderer.end();

			batch.begin();

		}

		if (image == null)
			acquireRegion();
		else
			batch.draw(image, getX(), getY(),
					getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
	}
	// Actors==========================
}
