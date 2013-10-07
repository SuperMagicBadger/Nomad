package com.greatcow.nomad.control;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.greatcow.nomad.data.Pools;

public class CurveMover extends Action {

	CatmullRomSpline spline;
	float t;
	
	public CurveMover() {
		spline = new CatmullRomSpline();
	}
	
	
	
	public void addPoint(float x, float y){
		spline.add(Pools.obtaVector3(x, y, 0));
	}



	@Override
	public boolean act(float delta) {
		return false;
	}
	
}
