package Screen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;


public class GameScreen extends Scene {
	
	// private Screen gameScreen;
	private ScreenManager screenManager;
	
	private String gameText;
	private Button pauseButton;
	

	private float buttonWidth; // Assuming all buttons have the same width
	private float buttonHeight = 60; // Assuming all buttons have the same height
	private float screenWidth;
	private float screenHeight = Gdx.graphics.getHeight();
    
    // Position the pause button in the top right-hand corner
    float pauseButtonX = screenWidth - buttonWidth;
    float pauseButtonY = screenHeight - buttonHeight;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
    
    public GameScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight) {
    	super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
    	setPauseButtonX(getScreenWidth());
    	setPauseButtonY(getScreenWidth());
    }
    public float getPauseButtonX() {
    	return pauseButtonX;
    }
    public void setPauseButtonX(float screenWidth) {
    	pauseButtonX = screenWidth - buttonWidth;
    }
    public float getPauseButtonY() {
    	return pauseButtonY;
    }
    public void setPauseButtonY(float screenWidth) {
    	pauseButtonY = screenWidth - buttonWidth;
    }
    
	public Button getPauseButton() {
        return pauseButton;
    }
	
	public void gameDisplay() {
		// Game display
    	screenManager.setCurrentScreen("Game");
	}
	
    public void pauseGame() {
        // Pause the game logic here
    	screenManager.setCurrentScreen("Pause");
    }
	
	@Override
    public void show() {    	
    	// Display game over text
    	gameText = "MARIO GAME"; 

    	// Calculate the x position to center the buttons horizontally
        buttonWidth = getButtonWidth();
        screenWidth = Gdx.graphics.getWidth();
        
        // Create Pause Button
        pauseButton = new Button(pauseButtonX, pauseButtonY, buttonWidth, buttonHeight);
        pauseButton.setText("I I");
        pauseButton.setColour(Color.RED);
    }
	
	@Override
	public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
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
        	if(Gdx.input.justTouched())
        	{
        		System.out.println("Game paused.");
        		pauseGame();
        	}
        }else if(Gdx.input.isKeyPressed (Keys.ESCAPE)) {
        	System.out.println("Game paused.");
    		pauseGame();
        }
        
        else {
        	pauseButton.setColour(Color.RED);
        }
	}
	
	public void setScreenManager(ScreenManager screenManagerInput) {
    	screenManager = screenManagerInput;
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
	
	public String getScreen() {
		String screen = "game";
		return screen;
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
