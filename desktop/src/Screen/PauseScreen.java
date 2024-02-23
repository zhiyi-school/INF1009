package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;


public class PauseScreen extends Scene {
	private ScreenManager screenManager;
	private Scene gameScreen;
	
	private String pauseText;
	private Button resumeButton;
	private Button restartButton;
	private Button exitButton;
	
	private float buttonWidth; 
	private float buttonSpacing = 25;
	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; 
	private float startX; 
	
    	private float mouseX, mouseY;
    
    public PauseScreen(ScreenManager screenManager, Scene screen, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight) {
    	super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
    	setStartX(getScreenWidth()/2);
        this.screenManager = screenManager;
        if (screen instanceof GameScreen) {
            this.gameScreen = (GameScreen) screen; 
        } else {
            // Handle the case where screen is not an instance of GameScreen
        }
    }
    
    	public String getScreen() {
		String screen = "Pause";
		return screen;
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
        	screenManager.switchTo(gameScreen);
    	}
    
    	public void restartGame() {
    		screenManager.getEntityManager().restartGame(screenManager.getWorld(), screenManager.getCamera());
    		screenManager.switchTo(gameScreen);
    	}

    	public void exitGame() {
        	Gdx.app.exit();
    	}
    
    	@Override
    	public void show() {
	    	displayPause();
	    	
	        buttonWidth = getButtonWidth();

		// Create Resume Button
	    	resumeButton = new Button(startX, 100, buttonWidth + 10, 60);
	    	resumeButton.setText("Resume");
	    	resumeButton.setColour(Color.RED);

		// Create Restart Button
	    	restartButton = new Button(startX + buttonWidth + buttonSpacing, 100, buttonWidth + 10, 60);
	    	restartButton.setText("Restart");
	    	restartButton.setColour(Color.RED);

		// Create Exit Button
	    	exitButton = new Button(startX + 2 * (buttonWidth + buttonSpacing), 100, buttonWidth, 60);
	    	exitButton.setText("Exit");
	    	exitButton.setColour(Color.RED);
	    	
	    }

    	@Override
    	public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
	        Gdx.gl.glClearColor(0, 1, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        
	        resumeButton.render(shapeRenderer,batch, font);
	        restartButton.render(shapeRenderer,batch, font);
	        exitButton.render(shapeRenderer,batch, font);
	        
	        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
		float x = Gdx.graphics.getWidth() / 2f;
		float y = Gdx.graphics.getHeight() * 0.8f; 
		font.draw(batch, pauseText, x, y, 0, Align.center, false);
		batch.end();
			
		// Check for click on button
	        mouseX = Gdx.input.getX();
	        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

	        if (resumeButton.hover(mouseX, mouseY)==true) {
	        	resumeButton.setColour(Color.YELLOW);
	        	if (Gdx.input.isTouched()){
	        		resumeGame();
	        		
	        	}
	        }
		else if (restartButton.hover(mouseX, mouseY)==true) {
	        	restartButton.setColour(Color.YELLOW);
	        	if (Gdx.input.isTouched()){
	        		restartGame();
	        	}
	        }
		else if (exitButton.hover(mouseX, mouseY)==true) {
	        	exitButton.setColour(Color.YELLOW);
	        	if (Gdx.input.isTouched()){
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

    	@Override
    	public void dispose() {
    	
    	}
}
