package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import Entity.EntityManager;
import Entity.OrthographicCameraController;
import Screen.GameScreen;
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
	
	private Screen currentScreen;
    private ScreenManager screenManager;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.8f), true);
		// Create Viewport and Camera
        orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        soundEffect = Gdx.audio.newSound(Gdx.files.internal("JumpSoundEffect.wav"));
		
        entityManager = new EntityManager(world, orthographicCameraController);
		
		// Create physics static bodies by iterating over all map objects
        entityManager.getMap().mapObjects(world);

		// Set up map dimensions
    	mapTileWidth = entityManager.getMap().getMapTileWidth();
    	mapTileHeight = entityManager.getMap().getMapTileHeight();
    	tileSize = entityManager.getMap().getTileSize();
    
    	// Calculate total pixel width and height of entire map
    	float mapFullWidth = mapTileWidth * tileSize * MAP_SCALE;
    	float mapFullHeight = mapTileHeight * tileSize * MAP_SCALE;
        
    	// Calculate camera boundaries and set them in OrthographicCameraController
    	orthographicCameraController.setCameraBoundaries(mapFullWidth, mapFullHeight);
    
    // Initialize the GameScreen
        gameScreen = new GameScreen();
        gameScreen.show(); // Show the GameScreen
        
        // Initialize the ScreenManager
        screenManager = new ScreenManager();
        
    	pauseScreen = new PauseScreen(screenManager, gameScreen);
        // screen1 = new InstructionsScreen();
        currentScreen = pauseScreen;
        currentScreen.show();
	}	
	
	public void update() {
		entityManager.collisionEquip(world);
		entityManager.collisionFight(world);
	}
	
	@Override
	public void render() {
		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ScreenUtils.clear(0, 0, 0.2f, 1);
		// System.out.println(entityManager.getNum());

        entityManager.entityDraw(batch);
      	float delta = Gdx.graphics.getDeltaTime();
        currentScreen.render(delta);
		// System.out.println(entityManager.getNum());

    entityManager.entityDraw(batch);
		entityManager.movement(soundEffect);
		
        // Check if update outside of world.set is required
		if(entityManager.getNum() > 0) {
			update();
		}else {
	        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		// System.out.println(world.getContactCount());
	        
			
			entityManager.update(Gdx.graphics.getDeltaTime());
	    		// Update camera position to follow character and ensures it does not go out of map boundaries
				if(entityManager.getEntity("PlayableCharacter") != null) {
					orthographicCameraController.updateCameraPosition(entityManager.getEntity("PlayableCharacter").getPosX() + 
		    				entityManager.getEntity("PlayableCharacter").getTexture().getWidth() / 2, 240);
		    		orthographicCameraController.applyViewport();
		    		// Set the batch projection matrix to camera's combined matrix	
		    		entityManager.setProjection(orthographicCameraController, batch);
				}else {
					
				}	
		}	
	}
	
	/*
    public void changeScreen(int screenNumber) {
//        if (screenNumber == 1) {
//            currentScreen.hide();
//            currentScreen = screen1;
//            currentScreen.show();
//        } 
//        else if (screenNumber == 2) {
//            currentScreen.hide();
//            currentScreen = screen2;
//            currentScreen.show();
//        }
    }
*/

	@Override
	public void resize(int width, int height) {
    	orthographicCameraController.resize(width, height);
	}
	
	@Override
    	public void dispose() {
    		batch.dispose();
			entityManager.diposeEntities(world);
			soundEffect.dispose();
			world.dispose();
			gameScreen.dispose();
			pauseScreen.dispose();
    	}
}
