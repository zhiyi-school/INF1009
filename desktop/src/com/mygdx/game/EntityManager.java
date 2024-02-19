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
	
	private List<PlayableCharacter> entityList;
	private List<NonPlayableCharacter> npcList;
	private List<NonPlayableCharacter> itemList;
	
	private PlayableCharacter Player1;	
	private NonPlayableCharacter Enemy;	
	private NonPlayableCharacter Item;	
	

	private NonPlayableCharacter removeNPC;
	private NonPlayableCharacter removeItem;
	private PlayableCharacter removePC;

	Random rand = new Random();
	private SpriteBatch batch;
	private CollisionManager contactListener;
	private int count = 0;
	
	public EntityManager(World world) {
		entityList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		itemList = new ArrayList<NonPlayableCharacter>();
		
//		rand.nextInt(Gdx.graphics.getHeight() + 1)
		
		// NPC and PC
		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextInt(Gdx.graphics.getWidth() + 1), 
				10, 200, 100, 10, true);
		npcList.add(Enemy);
		
		Item = new NonPlayableCharacter(world, "Weapon.png", 100, 100, 200, 100, 10, false);
		itemList.add(Item);
		
		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 0, 0, 200, 100, 5, false, true);
		entityList.add(Player1);
		setCollision(world);
		
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
		batch = new SpriteBatch();
		batch.begin();
		for(PlayableCharacter entity: entityList) {
			entity.draw(batch);
		}
		for(NonPlayableCharacter npc: npcList) {
			npc.draw(batch);
		}
		for(NonPlayableCharacter item: itemList) {
			item.draw(batch, true);
		}
		batch.end();
		batch.dispose();
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
		contactListener = new CollisionManager();
		world.setContactListener(contactListener);
	}
	public CollisionManager getCollision() {
		return contactListener;
	}
	public void collisionDelete(World world) {
		if(itemList.size() != 0) {
			for(NonPlayableCharacter item: itemList) {
				if(getCollision().equip(item, world)) {
					removeItem = item;
					count--;
				}
			}
			itemList.remove(removeItem);
		}
		
	}
	public void collisionFight(World world) {
		if(npcList.size() != 0) {
			for(PlayableCharacter entity: entityList) {
				for(NonPlayableCharacter npc: npcList) {
					if(entity.getAttackCheck() && "fight".equals(entity.getFix().getUserData())&& "fight".equals(npc.getFix().getUserData())) {
						getCollision().kill(npc, world);
						entity.getFix().setUserData("PlayableCharacter");
						entity.setAttackCheck(false);
						removeNPC = npc;
						count--;
					}else if(!entity.getAttackCheck() && "fight".equals(entity.getFix().getUserData())&& "fight".equals(npc.getFix().getUserData())){
						npc.getFix().setUserData("Enemy");
						getCollision().die(entity, world);
						removePC = entity;
						count--;
					}
				}
			}
			npcList.remove(removeNPC);
			entityList.remove(removePC);
		}
	}
	public int getNum() {
		for(PlayableCharacter entity: entityList) {
			if("flag".equals(entity.getFix().getUserData())) {
				count++;
			}
		}
		for(NonPlayableCharacter npc: npcList) {
			if("fight".equals(npc.getFix().getUserData())) {
				count++;
			}
		}
		for(NonPlayableCharacter item: itemList) {
			if("equip".equals(item.getFix().getUserData())) {
				entityList.get(0).setAttackCheck(true);
				count++;
			}
		}
		return count;
	}
	
}
	
	