import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import GameEngine.Entity.EntityManager;
import GameEngine.Entity.OrthographicCameraController;
import GameEngine.Entity.Screen.ScreenManager;
import GameLayer.entityManagerSingleton;
import GameLayer.orthographicCameraControllerSingleton;
import GameLayer.screenManagerSingleton;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMaster extends ApplicationAdapter {
	private EntityManager entityManager;
	
	// Add stage variable
	private Stage stage;

	// For Viewport and Camera
	private static OrthographicCameraController orthographicCameraController;
	private ScreenManager screenManager;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;

	@Override
	public void create() {
		// Create Viewport and Camera
		orthographicCameraController = orthographicCameraControllerSingleton.getInstance();
		entityManager = entityManagerSingleton.getInstance();
		orthographicCameraController.setEntityManager(entityManager);

		Gdx.input.setInputProcessor(stage);
		screenManager = screenManagerSingleton.getInstance();

	}

	@Override
	public void render() {
//		try {
			float delta = Gdx.graphics.getDeltaTime();

			// The app will always call screenManager to render screens using drawCurrent() method
			screenManager.drawCurrent(delta);
			screenManager.checkGameStart(MAP_SCALE);

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
		screenManager.resize(width, height);
	}

	@Override
	public void dispose() {
		entityManager.diposeEntities();
		screenManager.screenDispose();
	}
}
