package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;

public class PauseScreen implements Screen{
	
   
    @Override
    public void show() {
        // Called when this screen becomes the current screen.
    	Gdx.gl.glClearColor(1, 1, 0, 1);
    }

    @Override
    public void render(float delta) {
        // Called to render this screen.
    	// Clear the screen
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        // Called when the screen size is changed.
    }

    @Override
    public void pause() {
        // Called when the game is paused.
    }

    @Override
    public void resume() {
        // Called when the game resumes from a paused state.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen.
    }

    @Override
    public void dispose() {
        // Called when resources associated with this screen should be disposed.
    }
}
