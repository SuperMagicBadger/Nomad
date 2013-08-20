package com.greatcow.nomad.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Spinner {
	// varblok-------------------------
	SpinnerStyle style;
	SplitPane spane;
	Table table;
	Label l;
	ImageButton up;
	ImageButton down;
	// varblok=========================
	
	public Spinner(SpinnerStyle s){
		style = s;
	}
}
