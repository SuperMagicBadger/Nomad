package com.greatcow.nomad.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.greatcow.nomad.Nomad;
import com.greatcow.nomad.data.Economy;
import com.greatcow.nomad.style.PlanetStyle;

public class Planet extends Actor {
	// varblok--------------------------
	// render data
	public PlanetStyle style;

	// planet data
	public int resourceUnits;

	// orbit data
	public Vector2 orbit = null;

	//resource data
	Economy econ;
	// varblok==========================

	// constructors----------------------------------------
	public Planet() {
	}

	public Planet(PlanetStyle ps) {
		setStyle(ps);
	}
	// constructors========================================

	public void setStyle(PlanetStyle ps) {
		style = ps;
		setWidth(ps.planetImage.getRegionWidth());
		setHeight(ps.planetImage.getRegionHeight());
		setOriginX(style.planetImage.getRegionWidth() / 2f);
		setOriginY(style.planetImage.getRegionHeight() / 2f);
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
	
	public Economy setEcon(Economy e){
		Economy oldecon = econ;
		econ = e;
		return oldecon;
	}
	
	public Economy getEcon(){
		return econ;
	}

	// rendering-------------------------------------------

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
		if (style != null && style.planetImage != null) {
			batch.draw(style.planetImage, getX(), getY(), getOriginX(),
					getOriginY(), getWidth(), getHeight(), getScaleX(),
					getScaleY(), getRotation());
		}
	}
	// rendering===========================================
}
