package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class EntityManager {
	
	private List<Entity> entityList;
	private List<Entity> npcList;
	Random rand = new Random();
	private SpriteBatch batch;
	
	public EntityManager() {
		entityList = new ArrayList<Entity>();
		npcList = new ArrayList<Entity>();
		
		// NPC and PC
		npcList.add(new NonPlayableCharacter(Color.WHITE, "badlogic.jpg", rand.nextInt(Gdx.graphics.getHeight() + 1), 
				rand.nextInt(Gdx.graphics.getHeight() + 1), 200, 100, 10, false));
		entityList.add(new PlayableCharacter(Color.CYAN, "bucket.png", 0, 0, 200, 100, 5, false, true));
		
	}
	
	public void entityDraw() {
		for(Entity entity: entityList) {
			batch = new SpriteBatch();
			batch.begin();
			entity.draw(batch);
			batch.end();
			batch.dispose();
		}
		for(Entity entity: npcList) {
			batch = new SpriteBatch();
			batch.begin();
			entity.draw(batch);
			batch.end();
			batch.dispose();
		}
	}
	public void entityMovement(){
		for(Entity entity: entityList) {
			if(entity.getAICheck()) {
				entity.moveUserControlled();
			}else {
				entity.moveAIControlled();
			}
		}
	}
	public static Rectangle getBounds(Texture texture) {
        TextureData textureData = texture.getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }

        Pixmap pixmap = textureData.consumePixmap();
        int textureWidth = pixmap.getWidth();
        int textureHeight = pixmap.getHeight();
        System.out.println(textureWidth);
        System.out.println(textureHeight);
        
        // Create and return a rectangle representing the bounds of the texture
        return new Rectangle(0, 0, textureWidth, textureHeight);
    }
	
	public void checkCollide() {
		for(Entity entity: entityList) {
			for(Entity npc: npcList) {
	            // Create a Polygon and set its vertices to match the shape of your texture
	            // This example assumes a rectangular texture, you'll need to adjust for irregular shapes
	            float[] verticesEntity = new float[] {
	                (entity.getPosX() - (entity.getSprite().getWidth()/2)), (entity.getPosY() - (entity.getSprite().getHeight()/2)), // Bottom left corner
	                entity.getSprite().getWidth(), (entity.getPosY() - (entity.getSprite().getHeight()/2)), // Bottom right corner
	                entity.getSprite().getWidth(), entity.getSprite().getHeight(), // Top right corner
	                (entity.getPosX() - (entity.getSprite().getWidth()/2)), entity.getSprite().getHeight() // Top left corner
	            };
	            
	            float[] verticesNPC = new float[] {
	            		(npc.getPosX() - (npc.getSprite().getWidth()/2)), (npc.getPosY() - (npc.getSprite().getHeight()/2)), // Bottom left corner
		                npc.getSprite().getWidth(), (npc.getPosY() - (npc.getSprite().getHeight()/2)), // Bottom right corner
		                npc.getSprite().getWidth(), npc.getSprite().getHeight(), // Top right corner
		                (npc.getPosX() - (npc.getSprite().getWidth()/2)), npc.getSprite().getHeight() // Top left corner
		            };

	            Polygon polygon1 = new Polygon(verticesEntity);
	            Polygon polygon2 = new Polygon(verticesNPC);

	            // Update the position of the Polygon whenever the sprite moves
	            polygon1.setPosition(entity.getPosX(), entity.getPosY());
	            polygon2.setPosition(npc.getPosX(), npc.getPosY());

	            // Check for collision
	            if (Intersector.overlapConvexPolygons(polygon1, polygon2)) {
	                System.out.println("Collision detected!");
	            } else {
	                System.out.println("No collision.");
	            }
				
			}
		}		
	}
}
