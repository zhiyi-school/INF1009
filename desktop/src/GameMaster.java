import GameLayer.HUD; // Import HUD class
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import GameEngine.Entity.EntityManager;
import GameEngine.Entity.OrthographicCameraController;
import GameEngine.Entity.Screen.ScreenManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameMaster extends ApplicationAdapter {
	private SpriteBatch batch;
	private EntityManager entityManager;
	private World world;
	private HUD hud; // Declare HUD instance
	private Stage stage;// Add stage variable

	// For Viewport and Camera
	private OrthographicCameraController orthographicCameraController;
	private ScreenManager screenManager;

	// Constant variable for enlarging objects
	private static final float MAP_SCALE = 3.0f;

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

		Gdx.input.setInputProcessor(stage);
		screenManager = new ScreenManager(entityManager, world, orthographicCameraController, batch);


	}

	@Override
	public void render() {
		try {
			float delta = Gdx.graphics.getDeltaTime();

			// The app will always call screenManager to render screens using drawCurrent() method
			screenManager.drawCurrent(delta);
			screenManager.checkGameStart(MAP_SCALE);

		}
		catch(Exception e){
			System.out.println(e);
			Gdx.app.exit();

		}
		finally{

		}
	}

	@Override
	public void resize(int width, int height) {
		screenManager.resize(width, height);
	}

	@Override
	public void dispose() {
		entityManager.diposeEntities(world);
		screenManager.screenDispose();
		batch.dispose();
		world.dispose();
	}
}
