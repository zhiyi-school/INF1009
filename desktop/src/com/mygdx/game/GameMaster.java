package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

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

    	// Constant variable for enlarging objects
    	protected static final float MAP_SCALE = 3.0f;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.8f), true);
		soundEffect = Gdx.audio.newSound(Gdx.files.internal("JumpSoundEffect.wav"));
		entityManager = new EntityManager(world);

		// Create Viewport and Camera
        	orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Create and render map entities
        	entityManager = new EntityManager();
        	Map gameMap = new Map(0, 0, "gamemap.tmx", MAP_SCALE, orthographicCameraController);
        	entityManager.addEntity(gameMap);

		// Create physics static bodies by iterating over all map objects
        	gameMap.mapObjects(b2dworld);

		// Set up map dimensions
        	mapTileWidth = gameMap.getMapTileWidth();
        	mapTileHeight = gameMap.getMapTileHeight();
        	tileSize = gameMap.getTileSize();
        
        	// Calculate total pixel width and height of entire map
        	float mapFullWidth = mapTileWidth * tileSize * MAP_SCALE;
        	float mapFullHeight = mapTileHeight * tileSize * MAP_SCALE;
        
        	// Calculate camera boundaries and set them in OrthographicCameraController
        	orthographicCameraController.setCameraBoundaries(mapFullWidth, mapFullHeight);
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
        
        	// Check if update outside of world.set is required
		if(entityManager.getNum() > 0) {
			update();
		}else {
	        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		// System.out.println(world.getContactCount());
	        
	        OrthographicCamera camera = new OrthographicCamera();
	        camera.setToOrtho(false, 1, 1);		
	        
			entityManager.entityDraw();
			entityManager.movement(soundEffect);
		}	
	}
	
	@Override
    	public void dispose() {
    		batch.dispose();
        	world.dispose();
        	entityManager.diposeEntities();
		soundEffect.dispose();
    	}
}
