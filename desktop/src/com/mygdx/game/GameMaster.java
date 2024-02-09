package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameMaster extends ApplicationAdapter{
	private Screen screen1;
   
    private Screen currentScreen;

    @Override
    public void create() {
        screen1 = new MainMenuScreen();
        //screen2 = new PauseScreen();
        currentScreen = screen1;
        currentScreen.show();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        currentScreen.render(delta);
    }

    @Override
    public void dispose() {
        screen1.dispose();
//        screen2.dispose();
    }

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
}

