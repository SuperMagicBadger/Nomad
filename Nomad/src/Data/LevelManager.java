package Data;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.greatcow.nomad.model.Level;

public class LevelManager {

	// varblok---------------
	//statics
	public static Level activeLevel = null;
	private static LevelManager singleton = null;
	//files
	FileHandle directory;
	// varblok===============
	
	// constructors------------------------------
	public LevelManager getSingleton(){
		if(singleton == null){
			singleton = new LevelManager();
		}
		return singleton;
	}
	
	private LevelManager(){
		directory = Gdx.files.internal("levels");
	}
	// constructors==============================
	
	// access------------------------------------
	public String[] getLevelsFiles(){
		ArrayList<String> fileNames = new ArrayList<String>();
		for(FileHandle entry : directory.list()){
			if(entry.name().endsWith(".level")){
				fileNames.add(entry.name().substring(0, entry.name().length() - 6));
			}
		}
		return (String[]) fileNames.toArray();
	}
	// access====================================
	
}
