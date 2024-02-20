package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenManager {
	
	private MainMenuScreen mainMenuScreen;
	private InstructionsScreen instructionsScreen;
	private PauseScreen pauseScreen;
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;
	// private GameMaster gameScreen;
	private Screen currentScreen;

	
    private ArrayList<Screen> screens;
    

    public ScreenManager() {
        screens = new ArrayList<>();
        mainMenuScreen = new MainMenuScreen(); // Initialize mainMenuScreen
        instructionsScreen = new InstructionsScreen(); // Initialize instructionsScreen
        gameScreen = new GameScreen(); // Initialize gameScreen
        pauseScreen = new PauseScreen(this, gameScreen); // Initialize pauseScreen with appropriate parameters
        gameOverScreen = new GameOverScreen(); // Initialize gameOverScreen
    }


    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public void removeScreen(Screen screen) {
        screens.remove(screen);
    }
    
    public void setCurrentScreen(Screen screen) {
    	currentScreen = screen;
    }
    
    public void switchTo(Screen screen) {
    	if (screen instanceof PauseScreen) {
            currentScreen = pauseScreen;
        } else if (screen instanceof GameScreen) {
        	System.out.println(currentScreen);
        	currentScreen.hide();
            currentScreen = gameScreen;
            currentScreen.show();
        } else if (screen instanceof InstructionsScreen) {
            currentScreen = instructionsScreen;
        } else if (screen instanceof MainMenuScreen) {
            currentScreen = mainMenuScreen;
        } else {
            // Handle other types of screens
        }
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