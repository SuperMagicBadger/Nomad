package com.greatcow.nomad.shineys;

/**
 * @author Lycing
 * Mon Aug 22, 2011 3:12 pm
 * 
 * @author Cow
 * modified it to allow modular scroll
 * rather than continuous scroll
 * September 24, 2013
 */

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ParallaxBackground {
   
   private ParallaxLayer[] layers;
   private Camera camera;
   private SpriteBatch batch;
   private Vector2 speed = new Vector2();
   
   /**
    * @param layers  The  background layers 
    * @param width   The screenWith 
    * @param height The screenHeight
    * @param speed A Vector2 attribute to point out the x and y speed
    */
   public ParallaxBackground(ParallaxLayer[] layers,float width,float height,Vector2 speed){
      this.layers = layers;
      this.speed.set(speed);
      camera = new OrthographicCamera(width, height);
      batch = new SpriteBatch();
   }
   
   public void scroll(float x, float y){
	   camera.translate(x, y, 0);
   }
   
   public void resize(float x, float y){
	   camera.viewportWidth = x;
	   camera.viewportHeight = y;
   }
   
   public void render(float delta){
	  camera.update();
      camera.translate(speed.x*delta,speed.y*delta, 0);
      for(ParallaxLayer layer:layers){
         batch.setProjectionMatrix(camera.projection);
         batch.begin();
         
         float currentX = - camera.position.x*layer.parallaxRatio.x % ( layer.region.getRegionWidth() + layer.padding.x) ;
         currentX += -( layer.region.getRegionWidth() + layer.padding.x);
         
         do{
            float currentY = - camera.position.y*layer.parallaxRatio.y % ( layer.region.getRegionHeight() + layer.padding.y) ;
            currentY += - (layer.region.getRegionHeight()+layer.padding.y);
            
            do{
               batch.draw(layer.region,
                     -this.camera.viewportWidth/2+currentX + layer.startPosition.x ,
                     -this.camera.viewportHeight/2 + currentY +layer.startPosition.y);
               currentY += ( layer.region.getRegionHeight() + layer.padding.y );
            }while( currentY < camera.viewportHeight);
            
            currentX += ( layer.region.getRegionWidth()+ layer.padding.x);
         }while( currentX < camera.viewportWidth);
         
         batch.end();
      }
   }
}
