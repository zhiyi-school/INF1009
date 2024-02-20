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
import com.badlogic.gdx.utils.Align;


public class GameScreen implements Screen {
	
	// private Screen gameScreen;
	
	private String gameText;
	private Button pauseButton;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
    
	
	public Button getPauseButton() {
        return pauseButton;
    }
	
	public void gameDisplay() {
		// Game display
	}
	
    public void pauseGame() {
        // Pause the game logic here
    }
	
	@Override
    public void show() {
		// Draw Sprite and Shapes in LibGDX
     	batch = new SpriteBatch();
    	shapeRenderer = new ShapeRenderer();
    	
    	// Display game over text
    	gameText = "MARIO GAME"; 

    	
    	// Calculate the x position to center the buttons horizontally
        float buttonWidth = 50; // Assuming all buttons have the same width
        float buttonHeight = 60; // Assuming all buttons have the same height
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        // Position the pause button in the top right-hand corner
        float pauseButtonX = screenWidth - buttonWidth;
        float pauseButtonY = screenHeight - buttonHeight;
        
        // Create Pause Button
        pauseButton = new Button(pauseButtonX, pauseButtonY, buttonWidth, buttonHeight);
        pauseButton.setText("I I");
        pauseButton.setColour(Color.RED);
        
        font = new BitmapFont();
    }
	
	@Override
	public void render(float delta) {
        // Called to render this screen.
    	
        // Clear the screen
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        pauseButton.render(shapeRenderer,batch);
        
        //Draw text on screen
        batch.begin();
		font.setColor(Color.BLACK);
		
		// Draw instruction text
	    font.getData().setScale(4);
	    float x = Gdx.graphics.getWidth() / 2f;
	    float y = Gdx.graphics.getHeight() * 0.8f; 
	    font.draw(batch, gameText, x, y, 0, Align.center, false);
		batch.end();
		
		// Check if the mouse is inside the rectangle
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
        if (pauseButton.hover(mouseX, mouseY)==true) {
        	pauseButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Game paused.");
        		pauseGame();
        	}
        }
        
        else {
        	pauseButton.setColour(Color.RED);
        }
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override 
	public void dispose() {
        // Called when resources associated with this screen should be disposed.
    	// Dispose stage when it's no longer needed
		pauseButton.dispose();
    	batch.dispose();
    	shapeRenderer.dispose();
        font.dispose();	}

	}
