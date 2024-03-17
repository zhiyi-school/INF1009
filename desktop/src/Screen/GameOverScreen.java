package Screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import Entity.EntityManager;
import Entity.NonPlayableCharacter;
import Entity.PlayableCharacter;


public class GameOverScreen extends Scene {
	private ScreenManager screenManager;
	private EntityManager entityManager;
	
	private String gameOverText;
	private Button startButton;
	private Button mainMenuButton;
	private Button exitButton;
	
	private float buttonWidth; 
	private float buttonSpacing = 25; 
	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; 
	private float startX; 
	private Random rand = new Random();
    
    	private float mouseX, mouseY;
	
    	public GameOverScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight) {
    		super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
    		setStartX(getScreenWidth()/2);
    	}
    
	public String getScreen() {
		String screen = "GameOver";
		return screen;
	}
	
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
    
    	public float getStartX() {
    		return startX;
    	}
	
    	public void setStartX(float screenWidth) {
    		startX = (screenWidth - totalButtonWidth) / 2;
    	}
    
    	public void setEntityManager(EntityManager entityManagerInput) {
    		entityManager = entityManagerInput;
    	}
    
	public EntityManager getEntityManager() {
    		return entityManager;
    	}
	
    	public void displayGameOver() {
	    	if (screenManager.getEntityManager().getPC("PlayableCharacter") != null) {
	        	setGameOverText("WIN! \n THANKS FOR PLAYING");
		}
		else {
		    	setGameOverText("GAME OVER!");
		}
	}
    
    	public void resumeGame() {
		screenManager.setCurrentScreen("Main");
    	}

    	public void restartGamePC() {
	    	PlayableCharacter Player = new PlayableCharacter(screenManager.getWorld(), "PlayableCharacter.png", 10, 50, 0.75f, 3.0f, 5, false, true, Keys.A, Keys.D, Keys.W, Keys.S, "JumpSoundEffect.wav");
	    	screenManager.getEntityManager().addPlayableCharacter(Player);
	    	screenManager.getEntityManager().setNum();
		screenManager.setCurrentScreen("Game");
	}
	
    	public void restartGameNPC() {
	    	NonPlayableCharacter Enemy = new NonPlayableCharacter(screenManager.getWorld(), "Enemy.png", rand.nextFloat(Gdx.graphics.getWidth()), 
	    			rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, true);
//	    	NonPlayableCharacter Item = new NonPlayableCharacter(screenManager.getWorld(), "Weapon.png", rand.nextFloat(Gdx.graphics.getWidth()), 
//	    			rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
	    	NonPlayableCharacter Item = new NonPlayableCharacter(screenManager.getWorld(), "Weapon.png", 10, rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
			screenManager.getEntityManager().addNonPlayableCharacter(Enemy);
	    	screenManager.getEntityManager().addNonPlayableCharacter(Item);
	    	screenManager.getEntityManager().setNum();
	    	screenManager.setCurrentScreen("Game");
    	}

    	public void exitGame() {
        	Gdx.app.exit();
    	}
	
	@Override
    	public void show() {
	        buttonWidth = getButtonWidth();
	        
	        // Create Menu Button
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
	    }
		
	@Override
	public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
	        Gdx.gl.glClearColor(0, 1, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	    	displayGameOver();
	    	if (getGameOverText().contains("GAME OVER")) {
	            startButton.render(shapeRenderer, batch, font);
	            mainMenuButton.render(shapeRenderer, batch, font);
	    	}
	        exitButton.render(shapeRenderer,batch, font);
        
	        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
	    	float x = Gdx.graphics.getWidth() / 2f;
	    	float y = Gdx.graphics.getHeight() * 0.8f; 
		font.draw(batch, gameOverText, x, y, 0, Align.center, false);
		batch.end();
			
	        mouseX = Gdx.input.getX();
	        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        
	        // Check for click on button
	        if (mainMenuButton.hover(mouseX, mouseY)==true) {
	        	mainMenuButton.setColour(Color.YELLOW);
	        	if (Gdx.input.justTouched()){
	                	resumeGame();
	        	}
	        }
		else if (startButton.hover(mouseX, mouseY)==true) {
	        	startButton.setColour(Color.YELLOW);
	        	if (Gdx.input.justTouched()){
	        		if (screenManager.getEntityManager().getPC("PlayableCharacter") != null) {
	        			restartGameNPC();
	        		}else {
	        			restartGamePC();
	        		}
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
	
	@Override 
	public void dispose() {

	}
}
