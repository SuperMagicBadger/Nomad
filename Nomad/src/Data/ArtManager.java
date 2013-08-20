package Data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ArtManager {

	// varblok--------------------------
	public static ArtManager single;
	private HashMap<String, TextureAtlas> atlasMap;
	private HashMap<String, BitmapFont> fontMap;
	// varblok==========================

	// constructors---------------------
	public static ArtManager getSingleton() {
		if (single == null) {
			single = new ArtManager();
		}
		return single;
	}

	private ArtManager() {
		atlasMap = new HashMap<String, TextureAtlas>();
		fontMap = new HashMap<String, BitmapFont>();
	}
	public void dispose(){
		disposeAtlas();
		disposeFont();
	}
	// constructors=====================

	// texture access and manips--------
	public TextureAtlas getAtlas(String atlasname) {
		if (atlasMap.containsKey(atlasname)) {
			return atlasMap.get(atlasname);
		} else {
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/"
					+ atlasname + ".pack"), Gdx.files.internal("images"));
			atlasMap.put(atlasname, atlas);
			return atlas;
		}
	}
	public void disposeAtlas(String atlasname){
		if(atlasMap.containsKey(atlasname)){
			atlasMap.get(atlasname).dispose();
			atlasMap.remove(atlasname);
		}
	}
	public void disposeAtlas(){
		for(String s : atlasMap.keySet()){
			atlasMap.get(s).dispose();
		}
		atlasMap.clear();
	}
	// texture access and manips========
	
	// font access and manips-----------
	public BitmapFont getFont(String fontname){
		if(fontMap.containsKey(fontname)){
			return fontMap.get(fontname);
		} else {
			BitmapFont f = new BitmapFont(Gdx.files.internal("fonts/" + fontname + ".fnt"), false);
			fontMap.put(fontname, f);
			return f;
		}
	}
	public void disposeFont(String fontname){
		if(fontMap.containsKey(fontname)){
			fontMap.get(fontname).dispose();
			fontMap.remove(fontname);
		}
	}
	public void disposeFont(){
		for(String s : fontMap.keySet()){
			fontMap.get(s).dispose();
		}
		fontMap.clear();
	}
	// font access and manips===========
}
