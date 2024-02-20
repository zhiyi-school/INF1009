package Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;


public class PauseScreen implements Screen {
	private ScreenManager screenManager; // edited this
	private Screen gameScreen;
	
	private String pauseText;
	private Button resumeButton;
	private Button restartButton;
//	private Button instructionsButton;
	private Button exitButton;
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
    
 // Modify the constructor to accept a GameScreen parameter
    public PauseScreen(ScreenManager screenManager, Screen screen) {
        this.screenManager = screenManager;
        if (screen instanceof GameScreen) {
            this.gameScreen = (GameScreen) screen; // Assign the parameter to the member variable
        } else {
            // Handle the case where screen is not an instance of GameScreen
        }
    }
    
    public String getPauseText() {
		return pauseText;
	}
	
	public void setPauseText(String pauseText) {
		this.pauseText = pauseText; 
	}
	
	public Button getRestartButton() {
        return restartButton;
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
    
    public void displayPause() {
    	setPauseText("Game paused.");
    }
    
    public void resumeGame() {
        // Resume the game logic here
        screenManager.switchTo(gameScreen); // Switch to the GameScreen
    }
    
    public void restartGame() {
        // Restart the game logic here
    }

    public void exitGame() {
        Gdx.app.exit(); // Exits the application
    }
    
    @Override
    public void show() {
        
    	// Draw Sprite and Shapes in LibGDX
     	batch = new SpriteBatch();
    	shapeRenderer = new ShapeRenderer();
    	
    	// Display pause text
    	displayPause();
    	
    	// Calculate the x position to center the buttons horizontally
        float buttonWidth = 110; // Assuming all buttons have the same width
        float screenWidth = Gdx.graphics.getWidth();
        float buttonSpacing = 25; // Spacing between buttons
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Total width of all buttons and spacing
        float startX = (screenWidth - totalButtonWidth) / 2; // Start x position for the first button
    	
    	// Create Resume Button
    	resumeButton = new Button(startX, 100, buttonWidth + 10, 60);
    	resumeButton.setText("Resume");
    	resumeButton.setColour(Color.RED);
    	
    	// Create Restart Button
    	restartButton = new Button(startX + buttonWidth + buttonSpacing, 100, buttonWidth + 10, 60);
    	restartButton.setText("Restart");
    	restartButton.setColour(Color.RED);
    	
    	// Create Instructions Button
    	exitButton = new Button(startX + 2 * (buttonWidth + buttonSpacing), 100, buttonWidth, 60);
    	exitButton.setText("Exit");
    	exitButton.setColour(Color.RED);
    	
        font = new BitmapFont();
     
    }

    @Override
    public void render(float delta) {
        // Called to render this screen.
    	
        // Clear the screen
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        resumeButton.render(shapeRenderer,batch);
        restartButton.render(shapeRenderer,batch);
        exitButton.render(shapeRenderer,batch);
        
        //Draw text on screen
        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
		float x = Gdx.graphics.getWidth() / 2f;
	    float y = Gdx.graphics.getHeight() * 0.8f; 
		font.draw(batch, pauseText, x, y, 0, Align.center, false);
		batch.end();
		
		// Check if the mouse is inside the rectangle
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
        if (resumeButton.hover(mouseX, mouseY)==true) {
        	resumeButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Resuming game...");
        		resumeGame();
        	}
        }
        
        else if (restartButton.hover(mouseX, mouseY)==true) {
        	restartButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Restarting game...");
        		restartGame();
        	}
        	
        }
        
        else if (exitButton.hover(mouseX, mouseY)==true) {
        	exitButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Exiting game!");
        		exitGame();
        	}
        	
        }
        
        else {
        	resumeButton.setColour(Color.RED);
        	restartButton.setColour(Color.RED);
        	exitButton.setColour(Color.RED);
        }
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
    	// Dispose stage when it's no longer needed
    	resumeButton.dispose();
    	restartButton.dispose();
    	exitButton.dispose();
    	batch.dispose();
    	shapeRenderer.dispose();
        font.dispose();
    }
}
