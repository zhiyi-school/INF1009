package Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import Entity.EntityManager;
import Entity.OrthographicCameraController;

public class ScreenManager {
	
	private MainMenuScreen mainMenuScreen;
	private InstructionsScreen instructionsScreen;
	private PauseScreen pauseScreen;
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;
	private Scene currentScreen;
	private EntityManager entityManager;
	private OrthographicCameraController orthographicCameraController;
	private World world;


	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
	
    private ArrayList <Scene> screensList;
    

    public ScreenManager(EntityManager entityManager, World world, OrthographicCameraController orthographicCameraController, SpriteBatch batch) {
        screensList = new ArrayList<Scene>();
    	shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        
        mainMenuScreen = new MainMenuScreen(batch, shapeRenderer, font, 170, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Initialize mainMenuScreen
        instructionsScreen = new InstructionsScreen(batch, shapeRenderer, font, 100, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Initialize instructionsScreen
        gameScreen = new GameScreen(batch, shapeRenderer, font, 50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Initialize gameScreen
        pauseScreen = new PauseScreen(this, gameScreen, batch, shapeRenderer, font, 110, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Initialize pauseScreen with appropriate parameters
        gameOverScreen = new GameOverScreen(batch, shapeRenderer, font, 110, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Initialize gameOverScreen
        
        setEntityManager(entityManager);
        setWorld(world);
        setCamera(orthographicCameraController);
        setScreenManager(this);
    }
    
    public void setScreenManager(ScreenManager screenManagerInput) {
    	mainMenuScreen.setScreenManager(screenManagerInput);
        instructionsScreen.setScreenManager(screenManagerInput);
        gameScreen.setScreenManager(screenManagerInput);
        pauseScreen.setScreenManager(screenManagerInput);
        gameOverScreen.setScreenManager(screenManagerInput);
    }
    public void show(float delta) {
    	pauseScreen.show();
    	pauseScreen.render(delta);
    }

    public void addScreen(Scene screen) {
    	screensList.add(screen);
    }

    public void removeScreen(Screen screen) {
    	screensList.remove(screen);
    }
    
    public Scene getCurrentScreen() {
    	return currentScreen;
    }
    
    public void setEntityManager(EntityManager entityManagerInput) {
    	entityManager = entityManagerInput;
    }
    public EntityManager getEntityManager() {
    	return entityManager;
    }
    public void setCamera(OrthographicCameraController orthographicCameraControllerInput) {
    	orthographicCameraController = orthographicCameraControllerInput;
    }
    public OrthographicCameraController getCamera() {
    	return orthographicCameraController;
    }
    public void setWorld(World worldInput) {
    	world = worldInput;
    }
    public World getWorld() {
    	return world;
    }
    
    public void setCurrentScreen(String screen) {
    	switch(screen) {
	    	case "Main":
	    		currentScreen = mainMenuScreen;
				switchTo(mainMenuScreen);
				break;
			
	    	case "Pause":
	    		switchTo(pauseScreen);
				break;
	    		
	    	case "Game":
	    		switchTo(gameScreen);
				break;
	    	
	    	case "GameOver":
	    		switchTo(gameOverScreen);
				break;
	    		
	    	case "Instruction":
	    		switchTo(instructionsScreen);
				break;
    	}	
    }
    
    public void switchTo(Screen screen) {
    	if (screen instanceof MainMenuScreen) {
    		currentScreen = mainMenuScreen;
            
        } else if (screen instanceof GameScreen) {
            currentScreen = gameScreen;

        } else if (screen instanceof GameOverScreen) {
            currentScreen = gameOverScreen;
            
        } else if (screen instanceof InstructionsScreen) {
            currentScreen = instructionsScreen;
            
        } else if (screen instanceof PauseScreen) {
            currentScreen = pauseScreen;
            
        } 
    }
    public void drawCurrent(float delta) {
		currentScreen = getCurrentScreen();  
      	if(currentScreen == null) {
      		setCurrentScreen("Main");
      	}else {
        	currentScreen.show();
    		currentScreen.render(delta, currentScreen.getBatch(), currentScreen.getShape(), currentScreen.getMapFont());
      	}
    }
    
    public void checkGameStart(Box2DDebugRenderer debugRenderer, float MAP_SCALE) {
    	if(getCurrentScreen() instanceof GameScreen) {
    		// Clear the screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			ScreenUtils.clear(0, 0, 0.2f, 1);

			// Debugger
			debugRenderer.render(world, orthographicCameraController.getCamera().combined.cpy().scl(MAP_SCALE));
			gameState(getCurrentScreen().getBatch(), world);	
    	}
    }
    
    public void gameState(SpriteBatch batch, World world) {
    	if(entityManager.getNum() > 0) {
			update();
		}else {
			entityManager.entityDraw(batch);
			entityManager.movement(orthographicCameraController.getMapFullWidth());
			
			world.step(Gdx.graphics.getDeltaTime(), 6, 2);
			
			if(entityManager.getEntity("PlayableCharacter") != null) {
				orthographicCameraController.camera(batch);
			}else {
				setCurrentScreen("GameOver");
				update();
			}
		}
    }
    
    public void update() {
		// Update Box2d Objects outside of World Simulation
		entityManager.collisionEquip(world);
		entityManager.collisionFight(world);
	}
    
    public void screenDispose() {
    	font.dispose();
    	shapeRenderer.dispose();
    	gameOverScreen.dispose();
    	gameScreen.dispose();
    	instructionsScreen.dispose();
    	mainMenuScreen.dispose();
    	pauseScreen.dispose();
    }
}