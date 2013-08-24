package Data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ArtManager {
	
	private class Counter <ITEMCLASS>{
		public ITEMCLASS item;
		public int count;
		
		public Counter(ITEMCLASS counted){
			item = counted;
			count = 1;
		}
		
		public int currentCount(){
			return count;
		}
		
		public ITEMCLASS obtain(){
			count++;
			return item;
		}
		
		public void release(){
			count--;
		}
	}
	
	// varblok--------------------------
	public static ArtManager single;
	private HashMap<String, Counter<TextureAtlas> > atlasMap;
	private HashMap<String, Counter<BitmapFont> > fontMap;
	// varblok==========================

	// constructors---------------------
	public static ArtManager getSingleton() {
		if (single == null) {
			single = new ArtManager();
		}
		return single;
	}

	private ArtManager() {
		atlasMap = new HashMap<String, ArtManager.Counter<TextureAtlas> >();
		fontMap = new HashMap<String, ArtManager.Counter<BitmapFont> >();
	}
	// constructors=====================

	// texture access and manips--------
	public synchronized TextureAtlas getAtlas(String atlasname) {
		if (atlasMap.containsKey(atlasname)) {
			return atlasMap.get(atlasname).obtain();
		} else {
			FileHandle packFile = Gdx.files.internal("images/" + atlasname + ".pack");
			FileHandle imageDir = Gdx.files.internal("images");
			if(imageDir.exists()){
				Gdx.app.log("ArtMan", "image directory exists " + imageDir.isDirectory());
			}
			for(FileHandle s : imageDir.list()){
				Gdx.app.log("ArtMan", s.name());
			}
			TextureAtlas atlas = new TextureAtlas(packFile, imageDir);
			atlasMap.put(atlasname, new Counter<TextureAtlas>(atlas));
			return atlas;
		}
	}
	public synchronized void disposeAtlas(String atlasname){
		if(atlasMap.containsKey(atlasname) ){
			atlasMap.get(atlasname).release();
			if(atlasMap.get(atlasname).currentCount() <= 0){
				atlasMap.get(atlasname).item.dispose();
				atlasMap.remove(atlasname);
			}
		}
	}
	// texture access and manips========
	
	// font access and manips-----------
	public synchronized BitmapFont getFont(String fontname){
		if(fontMap.containsKey(fontname)){
			return fontMap.get(fontname).obtain();
		} else {
			BitmapFont f = new BitmapFont(Gdx.files.internal("fonts/" + fontname + ".fnt"), false);
			fontMap.put(fontname, new Counter<BitmapFont>(f));
			return f;
		}
	}
	public synchronized void disposeFont(String fontname){
		if(fontMap.containsKey(fontname) ){
			fontMap.get(fontname).release();
			if(fontMap.get(fontname).currentCount() <= 0){
				fontMap.get(fontname).item.dispose();
				fontMap.remove(fontname);
			}
		}
	}
	// font access and manips===========
}
