package com.greatcow.nomad.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

	// varblok=========================

	public static void main(String[] args) {
		PlanetActor a = new PlanetActor();
		a.setOrbitPosition(10, 90);
		System.out.println(a.getX() + " " + a.getY());
		System.out.println(a.getOrbitRadius() + " " + a.getOrbitTheta());
	}

	// constructor---------------------
	public PlanetActor() {
	}

	// constructor=====================

	// drawing-------------------------
	public void acquireRegion() {
		image = ArtManager.getSingleton().getAtlas(atlasName)
				.findRegion(textureName);
		if (image != null) {
			setWidth(image.getRegionWidth());
			setHeight(image.getRegionHeight());
		}
	}

	public void releaseRegion() {
		ArtManager.getSingleton().disposeAtlas(atlasName);
		image = null;
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

	// drawing=========================

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
			batch.draw(image, getX() - getWidth() / 2f, getY() - getHeight()
					/ 2f, getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
	}
	// Actors==========================
}
