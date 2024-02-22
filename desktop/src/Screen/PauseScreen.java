package Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;


public class PauseScreen extends Scene {
	private ScreenManager screenManager; // edited this
	private Screen gameScreen;
	
	private String pauseText;
	private Button resumeButton;
	private Button restartButton;
//	private Button instructionsButton;
	private Button exitButton;
	
	private float buttonWidth; // Assuming all buttons have the same width
	private float buttonSpacing = 25; // Spacing between buttons
	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Total width of all buttons and spacing
	private float startX; // Start x position for the first button
	
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
    
 // Modify the constructor to accept a GameScreen parameter
    public PauseScreen(ScreenManager screenManager, Screen screen, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight) {
    	super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
    	setStartX(getScreenWidth()/2);
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
    public float getStartX() {
    	return startX;
    }
    public void setStartX(float screenWidth) {
    	startX = (screenWidth - totalButtonWidth) / 2;
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
    	screenManager.getEntityManager().restartGame(screenManager.getWorld(), screenManager.getCamera());
    	screenManager.switchTo(gameScreen);
    }

    public void exitGame() {
        Gdx.app.exit(); // Exits the application
    }
    
    @Override
    public void show() {
    	displayPause();
    	
    	// Calculate the x position to center the buttons horizontally
        buttonWidth = getButtonWidth();
        
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
    	
     
    }

    @Override
    public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
        // Called to render this screen.
    	
//    	batch = getBatch();
        // Clear the screen
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        resumeButton.render(shapeRenderer,batch);
        restartButton.render(shapeRenderer,batch);
        exitButton.render(shapeRenderer,batch);
        
        //Draw text on screen
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
    
    public void setScreenManager(ScreenManager screenManagerInput) {
    	screenManager = screenManagerInput;
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
    
    public String getScreen() {
		String screen = "pause";
		return screen;
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
