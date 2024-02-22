import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import Entity.EntityManager;
import Entity.OrthographicCameraController;
import Screen.GameOverScreen;
import Screen.GameScreen;
import Screen.InstructionsScreen;
import Screen.MainMenuScreen;
import Screen.PauseScreen;
import Screen.ScreenManager;

public class GameMaster extends ApplicationAdapter {
	private SpriteBatch batch;
	private EntityManager entityManager;
	private World world;
    private Sound soundEffect;

	// For Viewport and Camera
	private OrthographicCameraController orthographicCameraController;

	// For Map dimensions
	private float mapTileWidth;
	private float mapTileHeight;
	private float tileSize;
	private float mapFullWidth;
	private float mapFullHeight;
	
	private Screen currentScreen;
    private ScreenManager screenManager;

	private Box2DDebugRenderer debugRenderer;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	@Override
	public void create() {
		debugRenderer = new Box2DDebugRenderer();
    	
    	System.out.println(mapFullWidth);
		
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.8f), true);
//		world = new World(new Vector2(0, 0f), true);
		// Create Viewport and Camera
        soundEffect = Gdx.audio.newSound(Gdx.files.internal("JumpSoundEffect.wav"));
        orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth() / 100f, Gdx.graphics.getHeight() / 100f);
//        orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth() * 4f, Gdx.graphics.getHeight() * 4f);
        entityManager = new EntityManager(world, orthographicCameraController);
		
		// Create physics static bodies by iterating over all map objects
        entityManager.getMap().mapObjects(world);

		// Set up map dimensions
    	mapTileWidth = entityManager.getMap().getMapTileWidth();
    	mapTileHeight = entityManager.getMap().getMapTileHeight();
    	tileSize = entityManager.getMap().getTileSize();
    	
    	// Calculate total pixel width and height of entire map
    	mapFullWidth = (mapTileWidth * tileSize * MAP_SCALE) / 100f;
    	mapFullHeight = (mapTileHeight * tileSize * MAP_SCALE) / 100f;
    	
    	// Calculate camera boundaries and set them in OrthographicCameraController
    	orthographicCameraController.setCameraBoundaries(mapFullWidth, mapFullHeight);
        
        // Initialize the ScreenManager
        screenManager = new ScreenManager(entityManager, world, orthographicCameraController);
        screenManager.setScreenManager(screenManager);
	}	
	
	public void update() {
		entityManager.collisionEquip(world);
		entityManager.collisionFight(world);
	}
	
	@Override
	public void render() {
		try {
	      	float delta = Gdx.graphics.getDeltaTime();
	      	currentScreen = screenManager.getCurrentScreen();      	
	      	
	      	if(currentScreen == null) {
	      		screenManager.setCurrentScreen("Instruction");
	      	}else {
	          	currentScreen.show();
	    		currentScreen.render(delta);
	          	
	    		if(screenManager.getCurrentScreen() instanceof GameScreen) {
	    			
	    			// Clear the screen
	    			Gdx.gl.glClearColor(0, 0, 0, 1);
	    	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	    			ScreenUtils.clear(0, 0, 0.2f, 1);
	    			// System.out.println(entityManager.getNum());
	    			debugRenderer.render(world, orthographicCameraController.getCamera().combined.cpy().scl(MAP_SCALE));
	    			
	    			// Check if update outside of world.set is required
	    			if(entityManager.getNum() > 0) {
	    				update();
	    			}else {
	    				entityManager.entityDraw(batch);
		    			entityManager.movement(soundEffect, mapFullWidth);
		    			
						world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	    		        // System.out.println(world.getContactCount());
	    				
	    				if(entityManager.getEntity("PlayableCharacter") != null) {
	    					entityManager.camera(orthographicCameraController, batch);
	        			}else {
	        				screenManager.setCurrentScreen("GameOver");
	        				update();
	        			}
	    			}
	    		}
	      	}
		}catch(Exception e){
			System.out.println(e);
			Gdx.app.exit();
			
		}finally{
			
		}
	}

	@Override
	public void resize(int width, int height) {
    	orthographicCameraController.resize(width, height);
	}
	
	@Override
    	public void dispose() {
    		batch.dispose();
//			entityManager.endGame(world);
			soundEffect.dispose();
			world.dispose();
    	}
}
