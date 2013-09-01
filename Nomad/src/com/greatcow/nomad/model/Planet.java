package com.greatcow.nomad.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.greatcow.nomad.Nomad;

public class Planet extends Actor {
	// varblok--------------------------
	// render data
	public PlanetStyle style;

	// planet data
	public int resourceUnits;

	// orbit data
	public Vector2 orbit = null;

	// varblok==========================

	public Planet() {
	}

	public Planet(PlanetStyle ps) {
		setStyle(ps);
	}

	public void setStyle(PlanetStyle ps) {
		style = ps;
		setWidth(ps.planetImage.getRegionWidth());
		setHeight(ps.planetImage.getRegionHeight());
		setOriginX(style.planetImage.getRegionWidth() / 2f);
		setOriginY(style.planetImage.getRegionHeight() / 2f);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (orbit != null) {
			batch.end();

			Nomad.shapeRenderer
					.setProjectionMatrix(batch.getProjectionMatrix());
			Nomad.shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
			Nomad.shapeRenderer.begin(ShapeType.Circle);
			Nomad.shapeRenderer.setColor(Color.GREEN);
			Nomad.shapeRenderer.circle(orbit.x, orbit.y,
					Math.abs(orbit.dst(getCenterX(), getCenterY())));
			Nomad.shapeRenderer.end();

			batch.begin();
		}
		if (style != null) {
			batch.draw(style.planetImage, getX(), getY(), getOriginX(),
					getOriginY(), getWidth(), getHeight(), getScaleX(),
					getScaleY(), getRotation());
		}
	}

	public void setOrbitCenter(float x, float y) {
		if (orbit == null) {
			orbit = Nomad.vectorPool.obtain();
		}
		orbit.x = x;
		orbit.y = y;
	}

	public void setOrbitCenter(Vector2 v) {
		if (v == null) {
			if (orbit != null) {
				Nomad.vectorPool.free(orbit);
			}
			orbit = null;
		} else if (orbit == null) {
			orbit = Nomad.vectorPool.obtain();
		}
		orbit.x = v.x;
		orbit.y = v.y;
	}

	public float getCenterY() {
		return getY() + getHeight() / 2f;
	}

	public float getCenterX() {
		return getX() + getWidth() / 2f;
	}
}
