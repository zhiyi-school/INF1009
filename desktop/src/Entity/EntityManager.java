package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class EntityManager {
	
	private List<PlayableCharacter> pcList;
	private List<NonPlayableCharacter> npcList;
	private List<Entity> entityList;
	
	private PlayableCharacter Player1;	
	private NonPlayableCharacter Enemy;	
	private NonPlayableCharacter Item;	
	private Map gameMap;

	private PlayableCharacter removePC;
	private NonPlayableCharacter removeNPC;
	private NonPlayableCharacter removeItem;

	private CollisionManager contactListener;
	private int count = 0;
	private Random rand = new Random();

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	public EntityManager(World world, OrthographicCameraController orthographicCameraController) {

		entityList = new ArrayList<Entity>();
		pcList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		
		gameMap = new Map("gameMap2.tmx", MAP_SCALE, orthographicCameraController);
    	entityList.add(gameMap);
    	
		// Creating Entities. Add them to ArrayList
		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 10, 50, 0.75f, 1, 5, false, true, 
				Keys.A, Keys.D, Keys.SPACE, Keys.S, "JumpSoundEffect.wav");
		pcList.add(Player1);
		
		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, true);
		npcList.add(Enemy);
		
//		Item = new NonPlayableCharacter(world, "Weapon.png",  rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
		Item = new NonPlayableCharacter(world, "letters_img/A.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
		npcList.add(Item);
		setCollision(world);
		
		// Create physics static bodies by iterating over all map objects
		getMap().mapObjects(world);	
	}
	
	public void restartGame(World world, OrthographicCameraController orthographicCameraController) {
		if(pcList.size() > 0) {
			for(PlayableCharacter pc: pcList) {
				pc.disposeSoundEffect();
				pc.destroy();
				pc.dispose(world);
			}
			pcList.clear();
		}
		if(npcList.size() > 0) {
			for(NonPlayableCharacter npc: npcList) {
				if(npc.getFix() != null) {
					npc.destroy();
					npc.dispose(world);
				}
			}
			npcList.clear();
		}
		
		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 10, 50, 0.75f, 3, 5, false, true, Keys.A, Keys.D, Keys.W, Keys.S, "JumpSoundEffect.wav");
		pcList.add(Player1);
		
		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextFloat(100) + 250, rand.nextFloat(Gdx.graphics.getHeight()/10) + 10, 200, 100, 10, true);
		npcList.add(Enemy);
		
//		Item = new NonPlayableCharacter(world, "Weapon.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
		Item = new NonPlayableCharacter(world, "Weapon.png", 50, rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
		npcList.add(Item);
		
	}
	
	// Dispose all entities
	public void diposeEntities(World world) {
		if(pcList.size() > 0) {
			for(PlayableCharacter pc: pcList) {
				pc.destroy();
				pc.dispose(world);
			}
		}
		if(npcList.size() > 0) {
			for(NonPlayableCharacter npc: npcList) {
				if(npc.getFix() != null) {
					npc.destroy();
					npc.dispose(world);
				}
			}
		}
		if(entityList.size() > 0) {
			for(Entity entity: entityList) {
				if (entity instanceof Map) {
	        		((Map) entity).dispose(world);
	    		}else {
	    			entity.dispose(world);
	    		}
			}
		}
	}
	
	// Drawing all entities
	public void entityDraw(SpriteBatch batch) {
		for(PlayableCharacter pc: pcList) {
			pc.draw(batch);
		}
		for(NonPlayableCharacter npc: npcList) {
			npc.draw(batch);
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
	public void setCollision(World world) {
		contactListener = new CollisionManager();
		world.setContactListener(contactListener);
	}
	public CollisionManager getCollision() {
		return contactListener;
	}
	public void collisionEquip(World world) {
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
	
	// Check which Entity to dispose
	public void collisionFight(World world) {
		if(getNum() != 0) {
			for(PlayableCharacter pc: pcList) {
				for(NonPlayableCharacter npc: npcList) {
					if(pc.getAttackCheck()) {
						removeNPC = getCollision().kill(pc, npc);
						count--;
						break;
					}else{
						removePC = getCollision().die(pc, npc);
						count--;
						break;
					}
				}
			}
			if(removeNPC != null) {
				removeNPC.destroy();
				removeNPC.dispose(world);
				npcList.remove(removeNPC);
			}
			if(removePC != null) {
				removePC.destroy();
				removePC.dispose(world);
				pcList.remove(removePC);
			}
		}
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
		for(NonPlayableCharacter item: npcList) {
			if(((String) item.getFix().getUserData()).contains("equip")) {
				pcList.get(0).setAttackCheck(true);
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
	public int getPCList() {
		return pcList.size();
	}
	
	// Map
	public Map getMap() {
		return gameMap;
	}
	public void createMap(World world) {
		getMap().mapObjects(world);
	}
}
	
	
