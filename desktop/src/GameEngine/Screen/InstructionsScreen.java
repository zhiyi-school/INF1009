package GameEngine.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

import GameEngine.Entity.EntityManager;
import GameEngine.batchSingleton;
import GameEngine.Entity.entityManagerSingleton;
import com.badlogic.gdx.graphics.Color;

public class InstructionsScreen extends Scene {	
	private String instructionsText;
	private Button startButton;
	private Button backButton;
	private Button exitButton;

	private float buttonWidth;
	private float buttonSpacing = 25;
	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing;
	private float startX;
    
	private float mouseX, mouseY;

	// Calling Singleton Classes
    private static SpriteBatch batch = batchSingleton.getInstance();
    private static BitmapFont font = fontSingleton.getInstance();
    private static EntityManager entityManager = entityManagerSingleton.getInstance();
    private static ScreenManager screenManager = screenManagerSingleton.getInstance();
    
	public InstructionsScreen( float buttonWidth, float screenWidth, float screenHeight) {
		super(buttonWidth, screenWidth, screenHeight);
		setStartX(getScreenWidth()/2);
	}
	public void setScreenManager(ScreenManager screenManagerInput) {
		screenManager = screenManagerInput;
	}

	public String getScreen() {
		String screen = "Instruction";
		return screen;
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

	public float getStartX() {
		return startX;
	}

	public void setStartX(float screenWidth) {
		startX = (screenWidth - totalButtonWidth) / 2;
	}

	public void displayInstructions() {
		setInstructionsText("These are the instructions for the game.\n Once the game starts, an audio will be played. \n You must collect the alphabets in order to complete the word. \n To move left, press the A key. \n To move right, press the D key. \n To Jump, press the W key.");

	}
    
	public void returnToMenu() {
	screenManager.setCurrentScreen("Main");
	}

	public void startGame() {
		entityManager.restartGame();
	screenManager.setCurrentScreen("Game");
	}

	public void exitGame() {
    	Gdx.app.exit();
	}
	
	
	@Override
	public void show() {		
    	displayInstructions(); 
    	buttonWidth = getButtonWidth();

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
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        startButton.render();
        backButton.render();
        exitButton.render();
        
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
		float x = Gdx.graphics.getWidth() / 2f;
		float y = Gdx.graphics.getHeight() * 0.8f; 
		font.draw(batch, "Instructions", x, y, 0, Align.center, false);
			
		font.getData().setScale(1.5f);
		float x2 = Gdx.graphics.getWidth() / 2f;
    	float y2 = Gdx.graphics.getHeight() * 0.7f; // Adjust this fraction to move the text higher or lower
    	font.draw(batch, instructionsText, x2, y2, 0, Align.center, false);
		batch.end();
		
	 	// Check for click on button
    	mouseX = Gdx.input.getX();
    	mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
    
        if (backButton.hover(mouseX, mouseY) == true) {
        	backButton.setColour(Color.YELLOW);
        	if (Gdx.input.justTouched()){
        		returnToMenu();
        	}
        }
		else if (startButton.hover(mouseX, mouseY) == true) {
        	startButton.setColour(Color.YELLOW);
        	if (Gdx.input.justTouched()){
        		startGame();
        	}
        }
		else if (exitButton.hover(mouseX, mouseY)==true) {
        	exitButton.setColour(Color.YELLOW);
        	if (Gdx.input.justTouched()){
        		exitGame();
        	}
        }
		else {
        	startButton.setColour(Color.RED);
        	backButton.setColour(Color.RED);
        	exitButton.setColour(Color.RED);
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
	}
}
