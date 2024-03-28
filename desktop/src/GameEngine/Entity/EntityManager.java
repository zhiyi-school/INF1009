package GameEngine.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.World;

import GameEngine.Entity.Screen.ScreenManager;
import GameLayer.languageFactory;
import GameLayer.orthographicCameraControllerSingleton;
import GameLayer.randomSingleton;
import GameLayer.worldSingleton;

public class EntityManager {

	private List<PlayableCharacter> pcList;
	private List<NonPlayableCharacter> npcList;
	private List<Entity> entityList;
	private ArrayList<String> spawnLetters;
	
	private PlayableCharacter Player1;
	private NonPlayableCharacter enemyPrototype;	
	private NonPlayableCharacter Item;
	private NonPlayableCharacter Door;
	private NonPlayableCharacter spikePrototype;
	private NonPlayableCharacter letter;
	private Map gameMap;

	private PlayableCharacter removePC;
	private NonPlayableCharacter removeNPC;
	private NonPlayableCharacter removeItem;

	private CollisionManager contactListener;
	private languageFactory lang;
	private int count = 0;
	private int spikeWidth;
	private int spikeHeight;
	private String eachLetter;
	private String randWord;
	private static Random rand = randomSingleton.getInstance();
	private static World world = worldSingleton.getInstance();

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	private NonPlayableCharacter test0;	
	private NonPlayableCharacter test1;	
	private NonPlayableCharacter test2;
	
	public EntityManager() {

		entityList = new ArrayList<Entity>();
		pcList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		lang = new languageFactory();
		
		gameMap = new Map("gameMap2.tmx", MAP_SCALE);
    	entityList.add(gameMap);
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
			pcList.removeAll(pcList);
		}
		if(npcList.size() > 0) {
			for(NonPlayableCharacter npc: npcList) {
				if(npc.getFix() != null) {
					npc.destroy();
					npc.dispose();
				}
			}
			npcList.removeAll(npcList);
		}
		
		Player1 = new PlayableCharacter("PlayableCharacter.png", 10, 50, 0.75f, 3, 5, false, true, Keys.A, Keys.D, Keys.W, "JumpSoundEffect.wav");
		pcList.add(Player1);
		
		Item = new NonPlayableCharacter("Weapon.png", 50, rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
		npcList.add(Item);
		
		Door = new NonPlayableCharacter("DoorClosed.png", 10, 400, 200, 100, 10, false);
		npcList.add(Door);
		
		test0 = new NonPlayableCharacter("letters_img/A.png", 60, 40, 200, 100, 10, false);
		npcList.add(test0);
		test1 = new NonPlayableCharacter("letters_img/C.png", 40, 40, 200, 100, 10, false);
		npcList.add(test1);
		test2 = new NonPlayableCharacter("letters_img/T.png", 80, 40, 200, 100, 10, false);
		npcList.add(test2);
		
		spikePrototype = new NonPlayableCharacter("Spike.png", 0, 0, 0, 100, 10, false);
	    enemyPrototype = new NonPlayableCharacter("Enemy.png", 0, 0, 200, 100, 10, true);
		
		spawnEnemies(2); // Set number of Enemy clones
		spawnSpikes(2); // Set number of Spike clones
		
		// Generate a random word
		randWord = getPC("PlayableCharacter").getWord(rand.nextInt(getPC("PlayableCharacter").getWordSize()));
    	getPC("PlayableCharacter").setGuess(randWord);
    	getPC("PlayableCharacter").setWordSound(randWord);
		spawnLetters = lang.spawnLetters(randWord);
		System.out.println(randWord);
		
		// Create an object for each Letter
		for(int x=0;x<spawnLetters.size();x++){
			eachLetter = spawnLetters.get(x);
			
			// Spike spawn range
	        float minX = 150;
	        float maxX = 400;
	        
			//Ensure values are within map and not negative
			maxX = Math.min(maxX, gameMap.getMapWidth() - spikeWidth);
			minX = Math.max(minX, 0);

		    // Calculate and set spike to spawn at random positions
		    float spawnX = rand.nextFloat() * (maxX - minX) + minX;
		    float spawnY = rand.nextFloat() * (gameMap.getMapHeight() - spikeHeight);
		    
		    spawnY = Math.max(spawnY, 0);
			
			letter = new NonPlayableCharacter(eachLetter, spawnX, spawnY, 200, 100, 10, false);
			npcList.add(letter);
		}
	}
	
	// Clones and spawns multiple spikes within map
	public void spawnSpikes(int numberOfSpikes) {        
        // Spike spawn range
        float minX = 150;
        float maxX = 400;
        
		for (int i = 0; i < numberOfSpikes; i++) {
			//Ensure values are within map and not negative
			maxX = Math.min(maxX, gameMap.getMapWidth() - spikeWidth);
			minX = Math.max(minX, 0);

		    // Calculate and set spike to spawn at random positions
		    float spawnX = rand.nextFloat() * (maxX - minX) + minX;
		    float spawnY = rand.nextFloat() * (gameMap.getMapHeight() - spikeHeight);
		    
		    spawnY = Math.max(spawnY, 0);
		    
		    NonPlayableCharacter Spike = spikePrototype.clone();
		    Spike.setPosition(spawnX, spawnY);
		    System.out.println("Spike coords: " + spawnX + ", " + spawnY);
		    npcList.add(Spike);
		}
	}
	
	public void spawnEnemies(int numberOfEnemies) {
		for (int i = 0; i < numberOfEnemies; i++) {
			// Enemy spawn range
			float minX = 220;
	        float maxX = 350;
	        float minY = 90; 
	        float maxY = 465;
			
			// Calculate a random position within the defined zone
	        float spawnX = rand.nextFloat() * (maxX - minX) + minX;
	        float spawnY = rand.nextFloat() * (maxY - minY) + minY;
			
			NonPlayableCharacter Enemy = enemyPrototype.clone();
			Enemy.setPosition(spawnX, spawnY);
			System.out.println("Enemy coords: " + spawnX + ", " + spawnY);
			npcList.add(Enemy);
		}
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
					}else if(((String) npc.getFix().getUserData()).contains("fight") || ((String) npc.getFix().getUserData()).contains("contact")){
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
	
	public void collisionEndGame(ScreenManager screenManager) {
		for(NonPlayableCharacter npc: npcList) {
			if(((String) npc.getFix().getUserData()).contains("endGame")) {
				screenManager.setCurrentScreen("GameOver");
				count--;
			}
		}
	}
	
	public void entityCollision(ScreenManager screenManager) {
		removePC = null;
		removeNPC = null;
        collisionEquip();
        collisionFight();
        collisionScore();
        collisionEndGame(screenManager);
	}
	
	public void winGame() {
		for(NonPlayableCharacter npc: npcList) {
			if(((String) npc.getFix().getUserData()).contains("Closed")) {
				npc.setTexture("DoorOpened.png");
				npc.setImage("DoorOpened.png");

		        int filename = npc.getImage().lastIndexOf('.');
		        String strippedFilename = npc.getImage().substring(0, filename);
		        npc.getFix().setUserData(strippedFilename);
		        npc.setDefaultUserData(strippedFilename);
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
			if(((String) npc.getFix().getUserData()).equals("equip")) {
				pcList.get(0).setAttackCheck(true);
			}
			if(!((String) npc.getFix().getUserData()).equals(npc.getDefaultUserData())) {
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
	public String getPCGuess() {
		if(getPC("PlayableCharacter") == null) {
			return null;
		}else {
			return getPC("PlayableCharacter").getScore();
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
	
	
