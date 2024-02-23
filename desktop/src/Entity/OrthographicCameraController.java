package Entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OrthographicCameraController {
    private OrthographicCamera camera;
    private Viewport viewport;
	private EntityManager entityManager;
	private World world;
    private float cameraMinX, cameraMaxX, cameraMinY, cameraMaxY;
	private static final float MAP_SCALE = 3.0f;
	
	private float mapTileWidth;
	private float mapTileHeight;
	private float tileSize;
	private float mapFullWidth;
	private float mapFullHeight;

    public OrthographicCameraController(float viewportWidth, float viewportHeight, World world) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
        setWorld(world);
    }
    
    // Method to update camera position with boundary checks
    public void updateCameraPosition(float targetX, float targetY) {
    	float cameraX = Math.max(cameraMinX, Math.min(cameraMaxX, targetX));
    	float cameraY = Math.max(cameraMinY, Math.min(cameraMaxY, targetY));
    	camera.position.set(cameraX, cameraY, 0);
    	camera.update();
    }
    
    public float getMapFullWidth() {
    	return mapFullWidth;
    }
    public float getMapFullHeight() {
    	return mapFullHeight;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
    public void setEntityManager(EntityManager entityManagerInput) {
    	entityManager = entityManagerInput;
    }
    public EntityManager getEntityManager() {
    	return entityManager;
    }
    public void setWorld(World worldInput){
    	world = worldInput;
    }
    public World getWorld() {
    	return world;
    }
    
    // Constrain camera to map boundaries
    public void setCameraBoundaries() {
    	
    	// Set up map dimensions
    	mapTileWidth = entityManager.getMap().getMapTileWidth();
    	mapTileHeight = entityManager.getMap().getMapTileHeight();
    	tileSize = entityManager.getMap().getTileSize();
    	
    	// Calculate total pixel width and height of entire map
    	mapFullWidth = (mapTileWidth * tileSize * MAP_SCALE) / 100f;
    	mapFullHeight = (mapTileHeight * tileSize * MAP_SCALE) / 100f;
    	
    	this.cameraMinX = viewport.getWorldWidth() / 2;
    	this.cameraMinY = viewport.getWorldHeight() / 2;
    	this.cameraMaxX = mapFullWidth - cameraMinX;
    	this.cameraMaxY = mapFullHeight - cameraMinY;
    }
    
    public void applyViewport() {
    	viewport.apply();
    }
    
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }
    
    public void camera(SpriteBatch batch) {
		// Update camera position to follow character and ensures it does not go out of map boundaries
		updateCameraPosition((entityManager.getEntity("PlayableCharacter").getBody().getPosition().x * 3f) - (entityManager.getEntity("PlayableCharacter").getTexture().getWidth() / 110f), 
				entityManager.getEntity("PlayableCharacter").getBody().getPosition().y);
		applyViewport();
		// Set the batch projection matrix to camera's combined matrix	
		setProjection(batch);
	}
    public void setProjection(SpriteBatch batch) {
		batch.begin();
		batch.setProjectionMatrix(getCamera().combined);
		batch.end();
	}
}
