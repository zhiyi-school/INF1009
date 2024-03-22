import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import GameEngine.Entity.EntityManager;
import GameEngine.Entity.OrthographicCameraController;
import GameEngine.Entity.Screen.ScreenManager;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMaster extends ApplicationAdapter {
	private EntityManager entityManager;
	private Stage stage;// Add stage variable

	// For Viewport and Camera
	private OrthographicCameraController orthographicCameraController;
	private ScreenManager screenManager;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;

	@Override
	public void create() {
		// Create Viewport and Camera
		orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth() / 100f, Gdx.graphics.getHeight() / 100f);
		entityManager = new EntityManager(orthographicCameraController);
		orthographicCameraController.setEntityManager(entityManager);

		// Calculate camera boundaries and set them in OrthographicCameraController
		orthographicCameraController.setCameraBoundaries();

		Gdx.input.setInputProcessor(stage);
		screenManager = new ScreenManager(entityManager, orthographicCameraController);


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
