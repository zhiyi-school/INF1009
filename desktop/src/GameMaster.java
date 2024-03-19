import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import Entity.EntityManager;
import Entity.OrthographicCameraController;
import Screen.ScreenManager;

public class GameMaster extends ApplicationAdapter {
	private SpriteBatch batch;
	private EntityManager entityManager;
	private World world;

	// For Viewport and Camera
	private OrthographicCameraController orthographicCameraController;
	private ScreenManager screenManager;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;
	
	//private Box2DDebugRenderer debugRenderer; 
	
	@Override
	public void create() {
		// Debugger
		
		// Creating the World for the game
		world = new World(new Vector2(0, -9.8f), true);
		batch = new SpriteBatch();
      
        // Create Viewport and Camera
        orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth() / 100f, Gdx.graphics.getHeight() / 100f, world);
        entityManager = new EntityManager(world, orthographicCameraController);
        orthographicCameraController.setEntityManager(entityManager);
    	
    	// Calculate camera boundaries and set them in OrthographicCameraController
    	orthographicCameraController.setCameraBoundaries();
        
        // Initialize the ScreenManager
        screenManager = new ScreenManager(entityManager, world, orthographicCameraController, batch);
        
        //debugRenderer = new Box2DDebugRenderer();
	}	
	
	@Override
	public void render() {
//		try {
	      	float delta = Gdx.graphics.getDeltaTime();
	      	
	      	// The app will always call screenManager to render screens using drawCurrent() method
      		screenManager.drawCurrent(delta);
      		screenManager.checkGameStart(MAP_SCALE);
      		
      		//debugRenderer.render(world, orthographicCameraController.getCamera().combined);

//		}
//		catch(Exception e){
//			System.out.println(e);
//			Gdx.app.exit();
//			
//		}
//		finally{
//			
//		}
	}

	@Override
	public void resize(int width, int height) {
    		orthographicCameraController.resize(width, height);
	}
	
	@Override
    	public void dispose() {
			entityManager.diposeEntities(world);
			screenManager.screenDispose();
    		batch.dispose();
			world.dispose();
			//debugRenderer.dispose();
    	}
}
