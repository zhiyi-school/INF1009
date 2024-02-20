package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class EntityManager {
	
	private List<PlayableCharacter> entityList;
	private List<NonPlayableCharacter> npcList;
	private List<NonPlayableCharacter> itemList;
	
	private PlayableCharacter Player1;	
	private NonPlayableCharacter Enemy;	
	private NonPlayableCharacter Item;	

	private PlayableCharacter removePC;
	private NonPlayableCharacter removeNPC;
	private NonPlayableCharacter removeItem;

	private Random rand = new Random();
	private SpriteBatch batch;
	private CollisionManager contactListener;
	private int count = 0;
	
	public EntityManager(World world) {
		entityList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		itemList = new ArrayList<NonPlayableCharacter>();
		
		// Creating Entities. Add them to ArrayList
		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 0, 0, 200, 100, 5, false, true);
		entityList.add(Player1);
		
//		rand.nextInt(Gdx.graphics.getHeight() + 1)
		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextInt(Gdx.graphics.getWidth() - 90 + 1), 
				10, 200, 100, 10, true);
		npcList.add(Enemy);
		
		Item = new NonPlayableCharacter(world, "Weapon.png", 100, 100, 200, 100, 10, false);
		itemList.add(Item);

		setCollision(world);
	}
	
	// Dispose all entities
	public void diposeEntities() {
		for(Entity entity: entityList) {
			entity.destroy();
		}
		for(Entity npc: npcList) {
			npc.destroy();
		}
		for(Entity item: itemList) {
			item.destroy();
		}
	}
	
	// Drawing all entities
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
	
	// Movement for entities
	private void entityMovement(Sound soundEffect){
		for(Entity entity: entityList) {
			if(entity.getAICheck()) {
				entity.moveUserControlled(soundEffect);
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
	public void movement(Sound soundEffect) {
		entityMovement(soundEffect);
		npcMovement();
	}
	
	// Box2d Collision
	public void setCollision(World world) {
		contactListener = new CollisionManager();
		world.setContactListener(contactListener);
	}
	public CollisionManager getCollision() {
		return contactListener;
	}
	public void collisionEquip(World world) {
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
					if(entity.getAttackCheck()) {
						removeNPC = getCollision().kill(entity, npc, world);
						count--;
					}else{
						removePC = getCollision().die(entity, npc, world);
						count--;
					}
				}
			}
			npcList.remove(removeNPC);
			entityList.remove(removePC);
		}
	}
	public int getNum() {
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
	
	