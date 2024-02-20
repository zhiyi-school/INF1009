package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;


public class GameOverScreen implements Screen {
	
	// private Screen gameOverScreen;
	private ScreenManager screenManager;
	
	private String gameOverText;
	private Button startButton;
	private Button mainMenuButton;
	private Button exitButton;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
	
	public String getGameOverText() {
		return gameOverText;
	}
	
	public void setGameOverText(String gameOverText) {
		this.gameOverText = gameOverText; 
	}
	
	public Button getStartButton() {
        return startButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
	
    public void displayGameOver() {
    	setGameOverText("GAME OVER!");
    }
    
    public void resumeGame() {
        // Resume the game logic here
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
    	
    	// Display game over text
    	displayGameOver();
    	
    	// Calculate the x position to center the buttons horizontally
        float buttonWidth = 110; // Assuming all buttons have the same width
        float screenWidth = Gdx.graphics.getWidth();
        float buttonSpacing = 25; // Spacing between buttons
        float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Total width of all buttons and spacing
        float startX = (screenWidth - totalButtonWidth) / 2; // Start x position for the first button
    	
        // Create Back Button
        mainMenuButton = new Button(startX + 10, 100, buttonWidth, 60);
        mainMenuButton.setText("Menu");
        mainMenuButton.setColour(Color.RED);
        
        // Create Start Button
        startButton = new Button(startX + buttonWidth + buttonSpacing, 100, buttonWidth + 10, 60);
        startButton.setText("Restart");
        startButton.setColour(Color.RED);

        // Create Exit Button
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
        
        startButton.render(shapeRenderer,batch);
        mainMenuButton.render(shapeRenderer,batch);
        exitButton.render(shapeRenderer,batch);
        
        //Draw text on screen
        batch.begin();
		font.setColor(Color.BLACK);
		
		// Draw game over text
	    font.getData().setScale(4);
	    float x = Gdx.graphics.getWidth() / 2f;
	    float y = Gdx.graphics.getHeight() * 0.8f; 
	    font.draw(batch, gameOverText, x, y, 0, Align.center, false);
		batch.end();
		
		// Check if the mouse is inside the rectangle
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
        if (mainMenuButton.hover(mouseX, mouseY)==true) {
        	mainMenuButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Going back to main menu...");
                resumeGame();
        	}
        }
        
        else if (startButton.hover(mouseX, mouseY)==true) {
        	startButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Restarting game!");
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
        	startButton.setColour(Color.RED);
        	mainMenuButton.setColour(Color.RED);
        	exitButton.setColour(Color.RED);
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
		String screen = "gameOver";
		return screen;
	}
	
	@Override 
	public void dispose() {
        // Called when resources associated with this screen should be disposed.
    	// Dispose stage when it's no longer needed
		startButton.dispose();
		mainMenuButton.dispose();
		exitButton.dispose();
    	batch.dispose();
    	shapeRenderer.dispose();
        font.dispose();	}

	}
