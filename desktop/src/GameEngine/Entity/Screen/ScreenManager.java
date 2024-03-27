package GameEngine.Entity.Screen;

import java.util.ArrayList;
import java.util.Random;

import GameLayer.HUD;
import GameLayer.batchSingleton;
import GameLayer.entityManagerSingleton;
import GameLayer.fontSingleton;
import GameLayer.orthographicCameraControllerSingleton;
import GameLayer.randomSingleton;
import GameLayer.shapeSingleton;
import GameLayer.worldSingleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import GameEngine.Entity.EntityManager;
import GameEngine.Entity.OrthographicCameraController;

public class ScreenManager {

    private final HUD hud;
    private Stage stage;
    
    private MainMenuScreen mainMenuScreen;
    private InstructionsScreen instructionsScreen;
    private PauseScreen pauseScreen;
    private GameScreen gameScreen;
    private GameOverScreen gameOverScreen;
    private Scene currentScreen;
    
    private static World world = worldSingleton.getInstance();
    private static SpriteBatch batch = batchSingleton.getInstance();
	private static Random rand = randomSingleton.getInstance();
    private static BitmapFont font = fontSingleton.getInstance();
    private static ShapeRenderer shapeRenderer = shapeSingleton.getInstance();
	private static EntityManager entityManager = entityManagerSingleton.getInstance();
	private static OrthographicCameraController orthographicCameraController = orthographicCameraControllerSingleton.getInstance();
    
    private Music backgroundMusic;
    private ArrayList<Scene> screensList;

    // Create a constructor
    public ScreenManager() {        
		// Calculate camera boundaries and set them in OrthographicCameraController
		orthographicCameraController.setCameraBoundaries();
		
        hud = new HUD(new Stage(new ScreenViewport()), 300, entityManager.getPCLives()); // Create HUD instance
    	stage = hud.getStage();

        screensList = new ArrayList<Scene>();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("themeSong.mp3"));

        // Create an object of mainMenuScreen, add to the screensList
        mainMenuScreen = new MainMenuScreen(170, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screensList.add(mainMenuScreen);

        // Create an object of instructionsScreen, add to the screensList
        instructionsScreen = new InstructionsScreen(100, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screensList.add(instructionsScreen);

        // Create an object of gameScreen, add to the screensList
        gameScreen = new GameScreen(stage, 50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), hud);
        screensList.add(gameScreen);

        // Create an object of pauseScreen, add to the screensList
        pauseScreen = new PauseScreen(this, gameScreen, 110, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screensList.add(pauseScreen);

        // Create an object of gameOverScreen, add to the screensList
        gameOverScreen = new GameOverScreen(110, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screensList.add(gameOverScreen);     

        setScreenManager(this);
    }


    // This function will always be called to load screen into the game
    public void drawCurrent(float delta) {
        // currentScreen will call getCurrentScreen() to return a Screen
        currentScreen = getCurrentScreen();

        // If currentScreen has nothing assigned, set the current screen to MainMenuScreen
        if (currentScreen == null) {
            setCurrentScreen("Main");
        }
        // If currentScreen has been assigned with a screen, then show the screen
        else {
        	Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            ScreenUtils.clear(0, 0, 0.2f, 1);
            
            currentScreen.show();
            currentScreen.render(delta);

            // Check if the current screen is the game screen
            boolean isGameScreen = currentScreen instanceof GameScreen;

            // Update HUD visibility based on whether it's the game screen or not
            hud.setVisible(isGameScreen);


            // If it's the game screen, render the HUD
            if (isGameScreen) {
                hud.render(entityManager.getPCLives(), entityManager.getPCGuess());
            }
        }
    }
    
    public void resize(int width, int height) {
    	orthographicCameraController.resize(width, height);
		hud.resize(width, height); 		// Resize the HUD
		hud.getStage().getViewport().update(width, height, true);
    }
    
    // Checking for game state
    public void checkGameStart(float MAP_SCALE) {
        backgroundMusic.setLooping(true); 	// Set the music to loop
        backgroundMusic.setVolume(0.05f);
        backgroundMusic.play();

        if (getCurrentScreen() instanceof GameScreen) {
            gameState();
        }

		hud.render(entityManager.getPCLives(), entityManager.getPCGuess());
		stage.act();
		stage.draw();
    }

    public void setScreenManager(ScreenManager screenManagerInput) {
        mainMenuScreen.setScreenManager(screenManagerInput);
        instructionsScreen.setScreenManager(screenManagerInput);
        gameScreen.setScreenManager(screenManagerInput);
        pauseScreen.setScreenManager(screenManagerInput);
        gameOverScreen.setScreenManager(screenManagerInput);
    }
    public HUD getHUD() {
    	return hud;
    }

    // Access to screensList
    public void addScreen(Scene screen) {
        screensList.add(screen);
    }

    public void removeScreen(Scene screen) {
        screensList.remove(screen);
    }

    // This function returns a Scene type
    public Scene getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(String screenString) {
        // Loop through each Screen in screenList
        for (Scene screen : screensList) {
            // There is an abstract method in Scene class called getScreen() that returns a String type
            if (screen.getScreen() == screenString) {
                // If the screenString is in the screenList, assign it to currentScreen
                switchTo(screen);
            }
        }
    }

    // Switching between screens
    public void switchTo(Scene screen) {
        if (screen instanceof MainMenuScreen) {
            currentScreen = mainMenuScreen;
        } else if (screen instanceof GameScreen) {
        	entityManager.getPC("PlayableCharacter").getWordSound().play(1f);
        	hud.setWorldTimer(300);
            currentScreen = gameScreen;
        } else if (screen instanceof GameOverScreen) {
        	if(entityManager.getPC("PlayableCharacter") != null) {
            	entityManager.getPC("PlayableCharacter").setGuess(null);
        	}
            currentScreen = gameOverScreen;
        } else if (screen instanceof InstructionsScreen) {
            currentScreen = instructionsScreen;
        } else if (screen instanceof PauseScreen) {
            currentScreen = pauseScreen;
        }
    }

    public void gameState() {
        // Update Box2d Objects outside of World Simulation
    	entityManager.count();
        if (entityManager.getNum() > 0) {
        	while(entityManager.getNum() != 0) {
        		update();
        	}
        } else {
        	if(entityManager.getPC("PlayableCharacter").checkWin(entityManager.getPCList())) {
        		entityManager.winGame();
        	}
            entityManager.entityDraw();
            entityManager.movement(orthographicCameraController.getMapFullWidth());

            world.step(Gdx.graphics.getDeltaTime(), 6, 2);
            
            if (entityManager.getPC("PlayableCharacter") != null && entityManager.getPCLives() > 0 && hud.getWorldTimer() > 0) {
                orthographicCameraController.camera();
            } else {
                setCurrentScreen("GameOver");
                update();
            }
        }
    }

    public void update() {
        entityManager.entityCollision(this);
    }

    public void screenDispose() {
        font.dispose();
        shapeRenderer.dispose();
        backgroundMusic.dispose();
        hud.dispose(); // Dispose the HUD
        batch.dispose();
        world.dispose();
    }
}
