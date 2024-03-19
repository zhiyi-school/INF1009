package Screen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;


public class GameScreen extends Scene {
	private ScreenManager screenManager;
	private String gameText;
	private Button pauseButton;
	
	private float buttonWidth;
	private float buttonHeight = 60;
	private float screenWidth;
	private float screenHeight;
    
    	private float pauseButtonX = screenWidth - buttonWidth;
    	private float pauseButtonY = screenHeight - buttonHeight;
    	private float mouseX, mouseY;
    
    
    	public GameScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight) {
    		super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
    		setPauseButtonX(getScreenWidth());
    		setPauseButtonY(getScreenWidth());
    	}
	
	public String getScreen() {
		String screen = "Game";
		return screen;
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
    		screenManager.setCurrentScreen("Game");
	}
	
    	public void pauseGame() {
    		screenManager.setCurrentScreen("Pause");
    	}
	
	@Override
    	public void show() {    	
	    	gameText = "MARIO GAME"; 
	
	        buttonWidth = getButtonWidth();
	        screenWidth = getScreenWidth();
	        screenHeight = getScreenHeight();

		// Create Pause Button
	        pauseButton = new Button(pauseButtonX, pauseButtonY, buttonWidth, buttonHeight);
	        pauseButton.setText("I I");
	        pauseButton.setColour(Color.RED);
	}
	
	@Override
	public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
	        Gdx.gl.glClearColor(0, 1, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        
	        pauseButton.render(shapeRenderer,batch, font);
	        
	        batch.begin();
		font.setColor(Color.BLACK);
	        font.getData().setScale(4);
	    	float x = screenWidth / 2f;
		float y = screenHeight * 0.8f; 
	    	font.draw(batch, gameText, x, y, 0, Align.center, false);
		batch.end();
			
		// Check for click on button
	        mouseX = Gdx.input.getX();
	        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
	        if (pauseButton.hover(mouseX, mouseY)==true) {
	        	pauseButton.setColour(Color.YELLOW);
	        	if (Gdx.input.justTouched()){
	        		pauseGame();
	        	}
	        }
		else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
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
	
	@Override 
	public void dispose() {

	}
}
