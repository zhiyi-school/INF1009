package Screen;

import java.util.ArrayList;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;

import Entity.EntityManager;

public class ScreenManager {
	
	private MainMenuScreen mainMenuScreen;
	private InstructionsScreen instructionsScreen;
	private PauseScreen pauseScreen;
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;
	// private GameMaster gameScreen;
	private Screen currentScreen;
	private EntityManager entityManager;
	private World world;

	
    private ArrayList<Screen> screensList;
    

    public ScreenManager(EntityManager entityManager, World world) {
        screensList = new ArrayList<Screen>();
        mainMenuScreen = new MainMenuScreen(); // Initialize mainMenuScreen
        screensList.add(mainMenuScreen);
        
        instructionsScreen = new InstructionsScreen(); // Initialize instructionsScreen
        screensList.add(instructionsScreen);
        
        gameScreen = new GameScreen(); // Initialize gameScreen
        screensList.add(gameScreen);
        
        pauseScreen = new PauseScreen(this, gameScreen); // Initialize pauseScreen with appropriate parameters
        screensList.add(pauseScreen);
        
        gameOverScreen = new GameOverScreen(); // Initialize gameOverScreen
        screensList.add(gameOverScreen);
        
        setEntityManager(entityManager);
        setWorld(world);
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

    public void addScreen(Screen screen) {
    	screensList.add(screen);
    }

    public void removeScreen(Screen screen) {
    	screensList.remove(screen);
    }
    
    public Screen getCurrentScreen() {
    	return currentScreen;
    }
    
    public void setEntityManager(EntityManager entityManagerInput) {
    	entityManager = entityManagerInput;
    }
    public EntityManager getEntityManager() {
    	return entityManager;
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
				
			default:
				currentScreen = gameScreen;
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
//        currentScreen.show();
//    	System.out.println(currentScreen);
    }
    
    /*
    public void switchToGameScreen() {
        currentScreen.dispose(); // Dispose current screen if necessary
        gameScreen = new GameMaster(this); // Reinitialize GameScreen
        currentScreen = gameScreen; // Set current screen to GameScreen
    }
    
    public void switchTo(Screen screen) {    	
    	// if instruction button is clicked, go to Instruction Screen
    	
    	// if start button is clicked, go to Game Screen
    	
    	// if pause button is clicked, go to Pause Screen 
    	
    	// after collision occurs, show Game Over Screen
    	
    	// if exit button is clicked, exit the game
    }
    

    public void drawScreens() {
        for (Screen screen : screens) {
            screen.update();
        }
    }

    public Screen getCurrentScreen() {

	
    } 
    */
}