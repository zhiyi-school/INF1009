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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class EntityManager {
	
	private List<Entity> entityList;
	private List<Entity> npcList;
	Random rand = new Random();
	private SpriteBatch batch;
	
	public EntityManager(World world) {
		entityList = new ArrayList<Entity>();
		npcList = new ArrayList<Entity>();
		
//		rand.nextInt(Gdx.graphics.getHeight() + 1)
		
		// NPC and PC
		npcList.add(new NonPlayableCharacter(world, "Enemy.png", rand.nextInt(Gdx.graphics.getWidth() + 1), 
				10, 200, 100, 10, true));
		npcList.add(new NonPlayableCharacter(world, "Weapon.png", 10, 10, 200, 100, 10, false));
		entityList.add(new PlayableCharacter(world, "PlayableCharacter.png", 0, 0, 200, 100, 5, false, true));
		
	}
	
	public void diposeEntities() {
		for(Entity entity: entityList) {
			entity.destroy();
		}
		for(Entity npc: npcList) {
			npc.destroy();
		}
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
	private void entityMovement(){
		for(Entity entity: entityList) {
			if(entity.getAICheck()) {
				entity.moveUserControlled();
			}else {
				entity.moveAIControlled();
			}
		}
	}
	
	private void npcMovement(){
		for(Entity npc: npcList) {
			if(npc.getAICheck()) {
				npc.moveAIControlled();
			}
		}
	}
	
	public void movement() {
		entityMovement();
		npcMovement();
	}
	
	public void setCollision(World world) {
		CollisionManager contactListener = new CollisionManager();
		world.setContactListener(contactListener);
	}
	
}
	
	