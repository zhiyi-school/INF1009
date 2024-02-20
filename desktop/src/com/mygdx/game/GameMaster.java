package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameMaster extends ApplicationAdapter{
	private Screen screen1;
   
    private Screen currentScreen;
    
    private ScreenManager screenManager;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;


    @Override
    public void create() {
    	// Initialize the GameScreen
        gameScreen = new GameScreen();
        gameScreen.show(); // Show the GameScreen
        
        // Initialize the ScreenManager
        screenManager = new ScreenManager();
        
    	pauseScreen = new PauseScreen(screenManager, gameScreen);
        // screen1 = new InstructionsScreen();
        currentScreen = pauseScreen;
        currentScreen.show();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        currentScreen.render(delta);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        pauseScreen.dispose();
//        screen2.dispose();
    }
    
/*
    public void changeScreen(int screenNumber) {
//        if (screenNumber == 1) {
//            currentScreen.hide();
//            currentScreen = screen1;
//            currentScreen.show();
//        } 
//        else if (screenNumber == 2) {
//            currentScreen.hide();
//            currentScreen = screen2;
//            currentScreen.show();
//        }
    }
*/
}
