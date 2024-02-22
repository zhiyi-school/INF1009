package Screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
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

public class InstructionsScreen extends Scene {
	
	// private Screen instructionScreen;
	private ScreenManager screenManager;
	
	private String instructionsText;
	private Button startButton;
	private Button backButton;
	private Button exitButton;

	private float buttonWidth; // Assuming all buttons have the same width
	private float screenWidth;
	private float buttonSpacing = 25; // Spacing between buttons
	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Total width of all buttons and spacing
	private float startX = (screenWidth - totalButtonWidth) / 2; // Start x position for the first button
	

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
    
    public InstructionsScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth) {
    	super(batch, shapeRenderer, font, buttonWidth, screenWidth);
    }
	
	public String getInstructionsText() {
		return instructionsText;
	}
	
	public void setInstructionsText(String instructionsText) {
		this.instructionsText = instructionsText; 
	}
	
	public Button getStartButton() {
        return startButton;
    }
	
    public Button getBackButton() {
        return backButton;
    }
	
    public Button getExitButton() {
        return exitButton;
    }
    
    public void displayInstructions() {
    	setInstructionsText("These are the instructions for the game. \n To move left, press the A key. \n To move right, press the D key. \n To Jump, press the Space Bar");
    }
    
    public void returnToMenu() {
        // Resume the game logic here
    }

    public void startGame() {
        // Restart the game logic here
    }

    public void exitGame() {
        Gdx.app.exit(); // Exits the application
    }
	
	
	@Override
    public void show() {

     	batch = getBatch();
    	shapeRenderer = getShape();
        font = getMapFont();
		
    	// Display game over text
    	displayInstructions(); 
    	
    	// Calculate the x position to center the buttons horizontally
        buttonWidth = getButtonWidth();
        screenWidth = getScreenWidth();
        
        // Create Back Button
        backButton = new Button(startX, 100, buttonWidth, 60);
        backButton.setText("Menu");
        backButton.setColour(Color.RED);
        
        // Create Start Button
        startButton = new Button(startX + buttonWidth + buttonSpacing, 100, buttonWidth, 60);
        startButton.setText("Start");
        startButton.setColour(Color.RED);

        // Create Exit Button
        exitButton = new Button(startX + 2 * (buttonWidth + buttonSpacing), 100, buttonWidth, 60);
        exitButton.setText("Exit");
        exitButton.setColour(Color.RED);
        
    }
	
	@Override
	public void render(float delta) {
    	show();
        // Clear the screen
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        startButton.render(shapeRenderer,batch);
        backButton.render(shapeRenderer,batch);
        exitButton.render(shapeRenderer,batch);
        
        //Draw text on screen
        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
		float x = Gdx.graphics.getWidth() / 2f;
	    float y = Gdx.graphics.getHeight() * 0.9f; // Adjust this fraction to move the text higher or lower
	    font.draw(batch, "Instructions", x, y, 0, Align.center, false);
		
		// Draw instruction text
		font.getData().setScale(1.5f);
		float x2 = Gdx.graphics.getWidth() / 2f;
	    float y2 = Gdx.graphics.getHeight() * 0.7f; // Adjust this fraction to move the text higher or lower
	    font.draw(batch, instructionsText, x2, y2, 0, Align.center, false);
	    batch.end();
		
		// Check if the mouse is inside the rectangle
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
        if (backButton.hover(mouseX, mouseY)==true) {
        	backButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Going back to main menu...");
        		screenManager.setCurrentScreen("Main");
        	}
        }
        
        else if (startButton.hover(mouseX, mouseY)==true) {
        	startButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Starting game!");
        		screenManager.setCurrentScreen("Game");
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
        	backButton.setColour(Color.RED);
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
		String screen = "instructions";
		return screen;
	}
	
	@Override 
	public void dispose() {
        // Called when resources associated with this screen should be disposed.
    	// Dispose stage when it's no longer needed
		startButton.dispose();
		backButton.dispose();
		exitButton.dispose();
	}
}
