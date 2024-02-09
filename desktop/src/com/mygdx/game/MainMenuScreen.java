package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MainMenuScreen implements Screen {
	
	private Screen pause_Screen;
	private Screen currentScreen;
	private Button start_button;
	private SpriteBatch batch;

	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    @Override
    public void show() {
    	
    	pause_Screen = new PauseScreen();
    	currentScreen= this;
    	
    	// Draw Sprite and Textures in LibGDX
     	batch = new SpriteBatch();
    	shapeRenderer = new ShapeRenderer();

    	start_button = new Button(200,300,100,50);
    	start_button.setText("Start");
    	start_button.setColour(Color.RED);
        font = new BitmapFont();
        
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        start_button.render(shapeRenderer,batch);
        if (Gdx.input.isTouched()) {
            
            // Check if touch coordinates are within the clickable area
            if (start_button.isClicked()==true) {
              // The texture has been clicked
              System.out.println("Start clicked!");
//              currentScreen.hide();
//              currentScreen=pause_Screen;
//              currentScreen.show();
              this.hide();
              pause_Screen.show();
            }
//            if (pause_button.contains(touchX, touchY)) {
//                // The texture has been clicked
//                System.out.println("Texture clicked!");
//            }
            
        }
       
        
    }

    @Override
    public void resize(int width, int height) {
        
    }
    
    @Override
    public void dispose() {
    	// Dispose stage when it's no longer needed
    	batch.dispose();
    	shapeRenderer.dispose();
        font.dispose();
    }

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
