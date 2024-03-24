package GameEngine.Entity.Screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;

import GameEngine.Entity.EntityManager;
import GameEngine.Entity.NonPlayableCharacter;
import GameEngine.Entity.PlayableCharacter;
import GameLayer.batchSingleton;
import GameLayer.entityManagerSingleton;
import GameLayer.fontSingleton;
import GameLayer.randomSingleton;
import GameLayer.screenManagerSingleton;
import GameLayer.shapeSingleton;
import GameLayer.worldSingleton;


public class GameOverScreen extends Scene {
	private String gameOverText;
	private Button startButton;
	private Button mainMenuButton;
	private Button exitButton;
	
	private float buttonWidth; 
	private float buttonSpacing = 25; 
	private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; 
	private float startX; 
	
	private static Random rand = randomSingleton.getInstance();
    private static World world = worldSingleton.getInstance();
    private static SpriteBatch batch = batchSingleton.getInstance();
    private static BitmapFont font = fontSingleton.getInstance();
    private static ShapeRenderer shapeRenderer = shapeSingleton.getInstance();
    private static EntityManager entityManager = entityManagerSingleton.getInstance();
    private static ScreenManager screenManager = screenManagerSingleton.getInstance();
    
    	private float mouseX, mouseY;
	
    	public GameOverScreen(float buttonWidth, float screenWidth, float screenHeight) {
    		super(buttonWidth, screenWidth, screenHeight);
    		setStartX(getScreenWidth()/2);
    	}
    	public void setScreenManager(ScreenManager screenManagerInput) {
    		screenManager = screenManagerInput;
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
	
    	public void displayGameOver() {
	    	if (entityManager.getPC("PlayableCharacter") != null) {
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
	    	PlayableCharacter Player = new PlayableCharacter("PlayableCharacter.png", 10, 50, 0.75f, 3, 5, false, true, Keys.A, Keys.D, Keys.W, Keys.S, "JumpSoundEffect.wav");
	    	entityManager.addPlayableCharacter(Player);
		screenManager.setCurrentScreen("Game");
	}
	
    	public void restartGameNPC() {
	    	NonPlayableCharacter Enemy = new NonPlayableCharacter("Enemy.png", rand.nextFloat(Gdx.graphics.getWidth()), 
	    			rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, true);
//	    	NonPlayableCharacter Item = new NonPlayableCharacter(screenManager.getWorld(), "Weapon.png", rand.nextFloat(Gdx.graphics.getWidth()), 
//	    			rand.nextFloat(Gdx.graphics.getHeight()) + 10, 200, 100, 10, false);
	    	NonPlayableCharacter Item = new NonPlayableCharacter("Weapon.png", 50, rand.nextFloat(Gdx.graphics.getHeight()), 200, 100, 10, false);
	    	entityManager.addNonPlayableCharacter(Enemy);
	    	entityManager.addNonPlayableCharacter(Item);
	    	
	    	NonPlayableCharacter test0 = new NonPlayableCharacter("letters_img/C.png", 40, 60, 200, 100, 10, false);
			entityManager.addNonPlayableCharacter(test0);
			
			NonPlayableCharacter test1 = new NonPlayableCharacter("letters_img/A.png", 60, 40, 200, 100, 10, false);
			entityManager.addNonPlayableCharacter(test1);
			
			NonPlayableCharacter test2 = new NonPlayableCharacter("letters_img/T.png", 60, 40, 200, 100, 10, false);
			entityManager.addNonPlayableCharacter(test2);
			
			NonPlayableCharacter Door = new NonPlayableCharacter("DoorClosed.png", 10, 400, 200, 100, 10, false);
			entityManager.addNonPlayableCharacter(Door);

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
	public void render(float delta) {
	        Gdx.gl.glClearColor(0, 1, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	    	displayGameOver();
	    	if(!getGameOverText().contains("WIN")) {
	            startButton.render();
	    	}
            mainMenuButton.render();
	        exitButton.render();
        
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
	        		if (entityManager.getPC("PlayableCharacter") != null) {
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
