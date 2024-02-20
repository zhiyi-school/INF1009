package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

	private Random rand = new Random();
	private CollisionManager contactListener;
	private int count = 0;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	public EntityManager(World world, OrthographicCameraController orthographicCameraController) {
		pcList = new ArrayList<PlayableCharacter>();
		npcList = new ArrayList<NonPlayableCharacter>();
		entityList = new ArrayList<Entity>();
		
		// Creating Entities. Add them to ArrayList
		Player1 = new PlayableCharacter(world, "PlayableCharacter.png", 0, 100, 200, 100, 5, false, true);
		pcList.add(Player1);
		
//		rand.nextInt(Gdx.graphics.getHeight() + 1)
		Enemy = new NonPlayableCharacter(world, "Enemy.png", rand.nextInt(Gdx.graphics.getWidth() - 90 + 1), 
				100, 200, 100, 10, true);
		npcList.add(Enemy);
		
		Item = new NonPlayableCharacter(world, "Weapon.png", 100, 300, 200, 100, 10, false);
		npcList.add(Item);
		
		gameMap = new Map(0, 0, "gamemap.tmx", MAP_SCALE, orthographicCameraController);
    	entityList.add(gameMap);

		setCollision(world);
	}
	
	// Dispose all entities
	public void diposeEntities(World world) {
		for(PlayableCharacter pc: pcList) {
			pc.destroy();
		}
		for(NonPlayableCharacter npc: npcList) {
			npc.destroy();
		}
		for(Entity entity: entityList) {
			if (entity instanceof Map) {
        		((Map) entity).dispose(world);
    		}else {
    			entity.dispose(world);
    		}
		}
	}
	
	// Drawing all entities
	public void entityDraw(SpriteBatch batch) {
		batch.begin();
		for(PlayableCharacter pc: pcList) {
			pc.draw(batch);
		}
		for(NonPlayableCharacter npc: npcList) {
			if("Enemy".contains((String) npc.getFix().getUserData())) {
				npc.draw(batch);
			}else {
				npc.draw(batch, true);
			}
		}
		for(Entity entity: entityList) {
			entity.render(batch);
		}
		batch.end();
	}
	public void addEntity(Entity entity) {
    	entityList.add(entity);
	}
	
	// Movement for entities
	private void entityMovement(Sound soundEffect){
		for(Entity pc: pcList) {
			if(pc.getAICheck()) {
				pc.moveUserControlled(soundEffect);
			}else {
				pc.moveAIControlled(Gdx.graphics.getDeltaTime());
			}
		}
	}
	private void npcMovement(){
		for(NonPlayableCharacter npc: npcList) {
			if(npc.getAICheck()) {
				npc.moveAIControlled(Gdx.graphics.getDeltaTime());
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
		if(entityList.size() != 0) {
			for(NonPlayableCharacter item: npcList) {
				if(getCollision().equip(item, world)) {
					removeItem = item;
					count--;
				}
			}
			npcList.remove(removeItem);
		}
	}
	public void collisionFight(World world) {
		if(npcList.size() != 0) {
			for(PlayableCharacter pc: pcList) {
				for(NonPlayableCharacter npc: npcList) {
					if(pc.getAttackCheck()) {
						removeNPC = getCollision().kill(pc, npc, world);
						count--;
						break;
					}else{
						removePC = getCollision().die(pc, npc, world);
						count--;
						break;
					}
				}
			}
			npcList.remove(removeNPC);
			pcList.remove(removePC);
		}
	}
	public int getNum() {
		for(NonPlayableCharacter npc: npcList) {
			if("fight".equals(npc.getFix().getUserData())) {
				count++;
			}
		}
		for(NonPlayableCharacter item: npcList) {
			if("equip".equals(item.getFix().getUserData())) {
				pcList.get(0).setAttackCheck(true);
				count++;
			}
		}
		return count;
	}
	public void update(float deltaTime) {
    	for (Entity pc : pcList) {
    		pc.update(deltaTime);
    	}
	}
	
	public PlayableCharacter getEntity(String entityInput) {
		for(PlayableCharacter pc: pcList) {
			if(entityInput.equals(pc.getFix().getUserData())){
				return pc;
			}
		}
		return null;
	}
	public Map getMap() {
		return gameMap;
	}
	public void setProjection(OrthographicCameraController orthographicCameraController, SpriteBatch batch) {
		batch.begin();
		batch.setProjectionMatrix(orthographicCameraController.getCamera().combined);
		batch.end();
	}
}
	
	
