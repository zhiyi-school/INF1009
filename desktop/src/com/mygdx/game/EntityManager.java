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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class EntityManager {
	
	private List<Entity> entityList;
	private List<Entity> npcList;
	Random rand = new Random();
	private SpriteBatch batch;
	// Create a Box2D world
	World world = new World(new Vector2(0, -10), true);
	
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
		for(Entity npc: npcList) {
			batch = new SpriteBatch();
			batch.begin();
			npc.draw(batch);
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
				
				float entityX = entity.getPosX();
				float entityY = entity.getPosY();
				float npcX = npc.getPosX();
				float npcY = npc.getPosY();
				
				float entityWidth = entity.getSprite().getWidth();
				float entityHeight = entity.getSprite().getHeight();
				float npcWidth = npc.getSprite().getWidth();
				float npcHeight = npc.getSprite().getHeight();
	            // Create a Polygon and set its vertices to match the shape of your texture
	            // This example assumes a rectangular texture, you'll need to adjust for irregular shapes
	            float[] verticesEntity = new float[] {
	                (entityX - (entityHeight/2)), (entityY - (entityHeight/2)), // Bottom left corner
	                entityWidth, (entityY - (entityHeight/2)), // Bottom right corner
	                entityWidth, entityHeight, // Top right corner
	                (entityX - (entityHeight/2)), entityHeight // Top left corner
	            };
	            
	            float[] verticesNPC = new float[] {
            		(npcX - (npcHeight/2)), (npcY - (npcHeight/2)), // Bottom left corner
            		npcWidth, (npcY - (npcHeight/2)), // Bottom right corner
            		npcWidth, npcHeight, // Top right corner
	                (npcX - (npcHeight/2)), npcHeight // Top left corner
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
