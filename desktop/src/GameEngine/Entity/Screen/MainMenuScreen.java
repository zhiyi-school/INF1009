package GameEngine.Entity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class MainMenuScreen extends Scene {
	private ScreenManager screenManager;
	
    	private float buttonWidth; 
    	private float buttonSpacing = 25;
    	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; 
    	private float startX;
	
    	private Button startButton;
    	private Button instructionsButton;
    	private Button exitButton;	
    
    	private float mouseX, mouseY;
    
    	public MainMenuScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight) {
    		super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
    		setStartX(getScreenWidth()/2);
    	}
    
    	public String getScreen() {
		String screen = "Main";
		return screen;
    	}
	
    	public float getStartX() {
    		return startX;
    	}
	
    	public void setStartX(float screenWidth) {
    		startX = (screenWidth - totalButtonWidth) / 5;
    	}
    
    	public void startGame() {
    		screenManager.getEntityManager().restartGame(screenManager.getWorld(), screenManager.getCamera());
		screenManager.setCurrentScreen("Game");
    	}
    
    	@Override
    	public void show() {
	        buttonWidth = getButtonWidth();
		    
	        // Create Start Button
	    	startButton = new Button(startX + 70, 100, buttonWidth - 70, 60);
	    	startButton.setText("Start");
	    	startButton.setColour(Color.RED);
	
		// Create Instruction Button
	    	instructionsButton = new Button(startX + buttonWidth + buttonSpacing, 100, buttonWidth, 60);
	    	instructionsButton.setText("Instructions");
	    	instructionsButton.setColour(Color.RED);
	
		// Create Exit Button
	    	exitButton = new Button(startX + 2 * (buttonWidth + buttonSpacing), 100, buttonWidth - 70, 60);
	    	exitButton.setText("Exit");
	    	exitButton.setColour(Color.RED);
    	}

    	public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
		// Clear screen and set to green background
	        Gdx.gl.glClearColor(0, 1, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		// Render the buttons onto the screen
	        startButton.render(shapeRenderer,batch, font);
	        instructionsButton.render(shapeRenderer,batch, font);
	        exitButton.render(shapeRenderer, batch, font);
	        
	        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
	        float x = Gdx.graphics.getWidth() / 2f;
		float y = Gdx.graphics.getHeight() * 0.7f; 
		font.draw(batch, "Welcome to our game!", x, y, 0, Align.center, false);
		batch.end();
			
		// Check for position of on button
		mouseX = Gdx.input.getX();
	        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

	    	// If mouse is inside the start button
	        if (startButton.hover(mouseX, mouseY)==true) {
	        	startButton.setColour(Color.YELLOW); // button changes colour to yellow
			// Detect if is being clicked
	        	if (Gdx.input.justTouched()){
	        		startGame();
	        	}
	        }
		else if (instructionsButton.hover(mouseX, mouseY)==true) {
	        	instructionsButton.setColour(Color.YELLOW); // button changes colour to yellow
			// Detect if is being clicked
	        	if (Gdx.input.justTouched()){
	        		screenManager.setCurrentScreen("Instruction"); 
	        	}
	        }
		else if (exitButton.hover(mouseX, mouseY)==true) {
	        	exitButton.setColour(Color.YELLOW); // button changes colour to yellow
			// Detect if is being clicked
	        	if (Gdx.input.justTouched()){
	        		Gdx.app.exit();
	        	}
	        }
		else {
			// By default colour of buttons is in Red
	        	startButton.setColour(Color.RED);
	        	instructionsButton.setColour(Color.RED);
	        	exitButton.setColour(Color.RED);
	        }
	    }
    
    	public void setScreenManager(ScreenManager screenManagerInput) {
    		screenManager = screenManagerInput;
    	}
    
    	@Override
    	public void dispose() {
    	}
    
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
