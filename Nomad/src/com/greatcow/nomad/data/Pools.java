package com.greatcow.nomad.data;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.greatcow.nomad.actors.PlanetActor;

public class Pools {
	
	// vector 2------------------------	
	private static Pool<Vector2> v2Pool = new Pool<Vector2>(){

	@Override
	protected Vector2 newObject() {
		return new Vector2(0,0);
	}
	
	};
	public static Vector2 obtainVector2(){
		return v2Pool.obtain();
	}
	public static Vector2 obtainVector2(float x, float y){
		return v2Pool.obtain().set(x, y);
	}
	public static void free(Vector2 vec){
		v2Pool.free(vec);
	}
	// vector 2========================
	
	
	// vector 3------------------------
	private static Pool<Vector3> v3Pool = new Pool<Vector3>(){

		@Override
		protected Vector3 newObject() {
			return new Vector3(0, 0, 0);
		}
		
	};
	public static Vector3 obtainVector3(){
		return v3Pool.obtain();
	}
	public static Vector3 obtaVector3(float x, float y, float z){
		return v3Pool.obtain().set(x, y, z);
	}
	public static void free(Vector3 vec){
		v3Pool.free(vec);
	}
	// vector 3========================
	

	// Planet Models-------------------
	private static Pool<PlanetActor> planetPool = new Pool<PlanetActor>(){
		@Override
		protected PlanetActor newObject() {
			return new PlanetActor();
		};
	};
	public static PlanetActor obtainPlanetModel(){
		return planetPool.obtain(); 
	}
	public static void free(PlanetActor model){
		planetPool.free(model);
	}
	// Planet Models===================
}
