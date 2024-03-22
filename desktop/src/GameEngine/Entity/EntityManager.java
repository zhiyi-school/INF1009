package GameEngine.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import GameLayer.orthographicCameraControllerSingleton;
import GameLayer.randomSingleton;
import GameLayer.worldSingleton;

public class EntityManager {

	private List<PlayableCharacter> pcList;
	private List<NonPlayableCharacter> npcList;
	private List<Entity> entityList;
	
	private PlayableCharacter Player1;
	private NonPlayableCharacter Enemy;	
	private NonPlayableCharacter Item;	
	private NonPlayableCharacter test0;	
	private NonPlayableCharacter test1;	
	private NonPlayableCharacter test2;	
	private NonPlayableCharacter Door;	
	private Map gameMap;

	private PlayableCharacter removePC;
	private NonPlayableCharacter removeNPC;
	private NonPlayableCharacter removeItem;

	private CollisionManager contactListener;
	private int count = 0;
	private static Random rand = randomSingleton.getInstance();
	private static World world = worldSingleton.getInstance();
	private static OrthographicCameraController orthographicCameraController = orthographicCameraControllerSingleton.getInstance();

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	public EntityManager() {

		entityList = new ArrayList<Entity>();
		pcList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		
		gameMap = new Map("gameMap2.tmx", MAP_SCALE);
    	entityList.add(gameMap);
    	
		// Creating Entities. Add them to ArrayList
//		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 10, 50, 0.75f, 3, 5, false, true, 
//				Keys.A, Keys.D, Keys.SPACE, Keys.S, "JumpSoundEffect.wav");
//		pcList.add(Player1);
//		
//		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, true);
//		npcList.add(Enemy);
//		
////		Item = new NonPlayableCharacter(world, "Weapon.png",  rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
//		Item = new NonPlayableCharacter(world, "Weapon.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
//		npcList.add(Item);
//		
//		test0 = new NonPlayableCharacter(world, "letters_img/A.png", 40, 40, 200, 100, 10, false);
//		npcList.add(test0);
//		test1 = new NonPlayableCharacter(world, "letters_img/C.png", 60, 40, 200, 100, 10, false);
//		npcList.add(test1);
//		test2 = new NonPlayableCharacter(world, "letters_img/T.png", 80, 40, 200, 100, 10, false);
//		npcList.add(test2);
//		
//		Door = new NonPlayableCharacter(world, "DoorClosed.png", 10, 400, 200, 100, 10, false);
//		npcList.add(Door);
		setCollision();
		
		// Create physics static bodies by iterating over all map objects
		getMap().mapObjects();	
	}
	
	public void restartGame() {
		if(pcList.size() > 0) {
			for(PlayableCharacter pc: pcList) {
				pc.disposeSoundEffect();
				pc.destroy();
				pc.dispose();
			}
			pcList.clear();
		}
		if(npcList.size() > 0) {
			for(NonPlayableCharacter npc: npcList) {
				if(npc.getFix() != null) {
					npc.destroy();
					npc.dispose();
				}
			}
			npcList.clear();
		}
		
		Player1 = new PlayableCharacter("PlayableCharacter.png", 10, 50, 0.75f, 3, 5, false, true, Keys.A, Keys.D, Keys.W, Keys.S, "JumpSoundEffect.wav");
		pcList.add(Player1);
		
		Enemy = new NonPlayableCharacter("Enemy.png", rand.nextFloat(100) + 250, rand.nextFloat(Gdx.graphics.getHeight()/10) + 10, 200, 100, 10, true);
		npcList.add(Enemy);
		
//		Item = new NonPlayableCharacter(world, "Weapon.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
		Item = new NonPlayableCharacter("Weapon.png", 50, rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
		npcList.add(Item);
		
		test0 = new NonPlayableCharacter("letters_img/C.png", 40, 40, 200, 100, 10, false);
		npcList.add(test0);
		test1 = new NonPlayableCharacter("letters_img/A.png", 60, 40, 200, 100, 10, false);
		npcList.add(test1);
		test2 = new NonPlayableCharacter("letters_img/T.png", 80, 40, 200, 100, 10, false);
		npcList.add(test2);
		
		Door = new NonPlayableCharacter("DoorClosed.png", 10, 400, 200, 100, 10, false);
		npcList.add(Door);
		
	}
	
	// Dispose all entities
	public void diposeEntities() {
		if(pcList.size() > 0) {
			for(PlayableCharacter pc: pcList) {
				pc.destroy();
				pc.dispose();
			}
		}
		if(npcList.size() > 0) {
			for(NonPlayableCharacter npc: npcList) {
				if(npc.getFix() != null) {
					npc.destroy();
					npc.dispose();
				}
			}
		}
		if(entityList.size() > 0) {
			for(Entity entity: entityList) {
				if (entity instanceof Map) {
	        		((Map) entity).dispose();
	    		}else {
	    			entity.dispose();
	    		}
			}
		}
	}
	
	// Drawing all entities
	public void entityDraw() {
		for(PlayableCharacter pc: pcList) {
			pc.draw();
		}
		for(NonPlayableCharacter npc: npcList) {
			npc.draw();
		}
		for(Entity entity: entityList) {
			entity.render();
		}
	}
	
	// Movement for entities
	private void pcMovement(float mapFullWidth){
		for(PlayableCharacter pc: pcList) {
			if(pc.getAICheck()) {
				pc.moveUserControlled(mapFullWidth);
			}else {
				pc.moveAIControlled(Gdx.graphics.getDeltaTime(), mapFullWidth);
			}
		}
	}
	private void npcMovement(float mapFullWidth){
		for(NonPlayableCharacter npc: npcList) {
			if(npc.getAICheck()) {
				npc.moveAIControlled(Gdx.graphics.getDeltaTime(), mapFullWidth);				
			}
		}
	}
	public void movement(float mapFullWidth) {
		pcMovement(mapFullWidth);
		npcMovement(mapFullWidth);
	}
	
	// Box2d Collision
	public void setCollision() {
		contactListener = new CollisionManager();
		world.setContactListener(contactListener);
	}
	public CollisionManager getCollision() {
		return contactListener;
	}
	public void collisionEquip() {
		if(getNum() != 0) {
			for(NonPlayableCharacter item: npcList) {
				if(getCollision().equip(item, world)) {
					removeItem = item;
					count--;
				}
			}
			npcList.remove(removeItem);
		}
	}
	public void collisionScore() {
		if(getNum() != 0) {
			for(PlayableCharacter pc: pcList) {
				for(NonPlayableCharacter npc: npcList) {
					if(((String) npc.getFix().getUserData()).contains("add")) {
						removeNPC = getCollision().addScore(pc, npc);
						count--;
						break;
					}
				}
			}
			if(removeNPC != null) {
				removeNPC.destroy();
				removeNPC.dispose();
				npcList.remove(removeNPC);
			}
		}
	}
	
	// Check which Entity to dispose
	public void collisionFight() {
		if(getNum() != 0) {
			for(PlayableCharacter pc: pcList) {
				for(NonPlayableCharacter npc: npcList) {
					if(pc.getAttackCheck() && ((String) npc.getFix().getUserData()).contains("fight")) {
						removeNPC = getCollision().kill(pc, npc);
						count--;
						break;
					}else if(((String) pc.getFix().getUserData()).contains("fight")){
						removePC = getCollision().die(pc, npc);
						count--;
						break;
					}
					
				}
			}
			if(removeNPC != null) {
				removeNPC.destroy();
				removeNPC.dispose();
				npcList.remove(removeNPC);
			}
			if(removePC != null) {
				removePC.destroy();
				removePC.dispose();
				pcList.remove(removePC);
			}
		}
	}
	
	public void entityCollision() {
		removePC = null;
		removeNPC = null;
        collisionEquip();
        collisionFight();
        collisionScore();
	}
	
	public void setNum() {
		count++;
	}
	public int getNum() {
		return count;
	}
	
	public void count() {		
		for(NonPlayableCharacter npc: npcList) {
			if(((String) npc.getFix().getUserData()).contains("fight")) {
				count++;
			}
		}
		for(NonPlayableCharacter npc: npcList) {
			if(((String) npc.getFix().getUserData()).contains("equip")) {
				pcList.get(0).setAttackCheck(true);
				count++;
			}else if(((String) npc.getFix().getUserData()).contains("add")) {
				count++;
			}
		}
	}
	
	// Check if there is still a Player
	public boolean checkGame() {
		if(pcList.size() > 0) {
			return true;
		}
		return false;
	}
	
	// Access to Lists
	public void addEntity(Entity entity) {
    	entityList.add(entityList.size(), entity);
	}
	public void addPlayableCharacter(PlayableCharacter pc) {
		pcList.add(pcList.size(), pc);
	}
	public void addNonPlayableCharacter(NonPlayableCharacter npc) {
		npcList.add(npcList.size(), npc);
	}
	
	public PlayableCharacter getPC(String entityInput) {
		for(PlayableCharacter pc: pcList) {
			if(((String) pc.getFix().getUserData()).contains(entityInput)){
				return pc;
			}
		}
		return null;
	}
	public int getPCListSize() {
		return pcList.size();
	}
	
	public int getPCLives() {
		if(getPC("PlayableCharacter") == null) {
			return 0;
		}else {
			return getPC("PlayableCharacter").getLives();
		}
	}
	public List<PlayableCharacter> getPCList() {
		return pcList;
	}
	public List<NonPlayableCharacter> getNPCList() {
		return npcList;
	}
	public List<Entity> getEntityList() {
		return entityList;
	}
	
	// Map
	public Map getMap() {
		return gameMap;
	}
	public void createMap() {
		getMap().mapObjects();
	}
}
	
	
