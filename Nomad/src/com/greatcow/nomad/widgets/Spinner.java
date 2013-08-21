package com.greatcow.nomad.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Spinner extends Table{
	// varblok-------------------------
	SpinnerStyle style;
	Table vert;
	Label l;
	ImageButton up;
	ImageButton down;
	int count;
	// varblok=========================
	
	public Spinner(SpinnerStyle s){
		count = 0;
		style = s;
		
		ImageButtonStyle imstyleUp = new ImageButtonStyle();
		imstyleUp.up = style.upArrowUp;
		imstyleUp.down = style.upArrowDown;
		up = new ImageButton(imstyleUp);
		up.addListener(new upListener());
		
		ImageButtonStyle imstyleDown = new ImageButtonStyle();
		imstyleDown.up = style.downArrowUp;
		imstyleDown.down = style.downArrowDown;
		down = new ImageButton(imstyleDown);
		down.addListener(new downListener());
		
		LabelStyle lstyle = new LabelStyle();
		lstyle.font = style.font;
		lstyle.fontColor = style.fontColor;
		l = new Label(Integer.toString(count), lstyle);
		vert = new Table();
		
		vert.add(up);
		vert.row().space(10);
		vert.add(down);
		
		add(l);
		add(vert);
	}
	
	private class upListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			count++;
			l.setText(Integer.toString(count));
		}
	}
	
	private class downListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			count--;
			l.setText(Integer.toString(count));
		}
	}
	
}
