package com.greatcow.nomad.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UnitStyle {
	public Skin s;
	public NinePatch blackcircle;
	public NinePatch redcircle;
	public NinePatch bluecircle;
	public float scale;
	
	public TextureRegion friendlySprite;
	public TextureRegion neutralSprite;
	public TextureRegion enemySprite;
	
	public Color moveCircleColor;
	public Color attackCircleColor;
}
