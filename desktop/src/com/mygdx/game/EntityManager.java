package GameEngine.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import GameLayer.Language;

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
	private NonPlayableCharacter letter;
	private Map gameMap;

	private PlayableCharacter removePC;
	private NonPlayableCharacter removeNPC;
	private NonPlayableCharacter removeItem;

	private CollisionManager contactListener;
	private int count = 0;
	private Random rand = new Random();
	
	private Language lang;
	private ArrayList<String> spawnLetters;
	private String randWord;
	private String eachLetter;
	private String letterImage;
	private Sound wordSound;
	
	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	public EntityManager(World world, OrthographicCameraController orthographicCameraController) {
		
		entityList = new ArrayList<Entity>();
		pcList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		lang = new Language();
		
		gameMap = new Map("gameMap2.tmx", MAP_SCALE, orthographicCameraController);
    	entityList.add(gameMap);
    	
		// Creating Entities. Add them to ArrayList
		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 10, 50, 0.75f, 3, 5, false, true, 
				Keys.A, Keys.D, Keys.SPACE, Keys.S, "JumpSoundEffect.wav");
		pcList.add(Player1);
		
		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, true);
		npcList.add(Enemy);
		
//		Item = new NonPlayableCharacter(world, "Weapon.png",  rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
		Item = new NonPlayableCharacter(world, "Weapon.png", rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
		npcList.add(Item);
		
		
//		test0 = new NonPlayableCharacter(world, "letters_img/A.png", 40, 40, 200, 100, 10, false);
//		npcList.add(test0);
//		test1 = new NonPlayableCharacter(world, "letters_img/B.png", 60, 40, 200, 100, 10, false);
//		npcList.add(test1);
//		test2 = new NonPlayableCharacter(world, "letters_img/T.png", 80, 40, 200, 100, 10, false);
//		npcList.add(test2);
		
		
		Door = new NonPlayableCharacter(world, "DoorClosed.png", 10, 400, 200, 100, 10, false);
		npcList.add(Door);
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
		
		// Generate a random word
		randWord = lang.generateWord();
		spawnLetters = lang.spawnLetters(randWord);
		System.out.println("Random word generated: "+randWord);
		
		// Create an object for each Letter
		for(int x=0;x<spawnLetters.size();x++)
		{
			eachLetter = spawnLetters.get(x);
			
//			float worldWidth = rand.nextFloat()* orthographicCameraController.getMapFullWidth();
//			float worldHeight =  rand.nextFloat()*orthographicCameraController.getMapFullHeight();
			float worldWidth = rand.nextInt(Gdx.graphics.getWidth())-orthographicCameraController.getMapFullWidth();
			float worldHeight = rand.nextInt(Gdx.graphics.getHeight())-orthographicCameraController.getMapFullHeight();
			System.out.println(eachLetter+ " spawned at ("+worldWidth+","+worldHeight + ")");
			letter = new NonPlayableCharacter(world, eachLetter, worldWidth,worldHeight, 200, 100, 10, false);
			npcList.add(letter);
		}
		
		Door = new NonPlayableCharacter(world, "DoorClosed.png", 10, 400, 200, 100, 10, false);
		npcList.add(Door);
		System.out.println("Map width: "+orthographicCameraController.getMapFullWidth()+" Height: "+orthographicCameraController.getMapFullHeight());
	}
	
	// Create a function to play the sound of random generated word
	public void playWordSound()
	{
		wordSound = Gdx.audio.newSound(Gdx.files.internal("words/" + randWord + ".mp3"));
		wordSound.play();
		System.out.println("Word that was read is : "+ randWord);
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
	public void collisionScore(World world) {
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
				removeNPC.dispose(world);
				npcList.remove(removeNPC);
			}
		}
	}
	
	// Check which Entity to dispose
	public void collisionFight(World world) {
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
	
	public void entityCollision(World world) {
		removePC = null;
		removeNPC = null;
        collisionEquip(world);
        collisionFight(world);
        collisionScore(world);
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
	
	// The return type is PlayableCharacter class 
	public PlayableCharacter getPC(String entityInput) 
	{
		for(PlayableCharacter pc: pcList) 
		{
			if(((String) pc.getFix().getUserData()).contains(entityInput))
			{
				return pc;
			}
		}
		return null;
	}
	public int getPCList() {
		return pcList.size();
	}
	
	public int getPCLives() {
		if(getPC("PlayableCharacter") == null) {
			return 0;
		}else {
			return getPC("PlayableCharacter").getLives();
		}
	}
	
	// Map
	public Map getMap() {
		return gameMap;
	}
	public void createMap(World world) {
		getMap().mapObjects(world);
	}
}
	
	
