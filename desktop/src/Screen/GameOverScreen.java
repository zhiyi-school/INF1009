package Screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import Entity.EntityManager;
import Entity.NonPlayableCharacter;
import Entity.PlayableCharacter;


public class GameOverScreen extends Scene {
	
	// private Screen gameOverScreen;
	private ScreenManager screenManager;
	private EntityManager entityManager;
	
	private String gameOverText;
	private Button startButton;
	private Button mainMenuButton;
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
	
    public GameOverScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth) {
    	super(batch, shapeRenderer, font, buttonWidth, screenWidth);
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
    public void setEntityManager(EntityManager entityManagerInput) {
    	entityManager = entityManagerInput;
    }
    public EntityManager getEntityManager() {
    	return entityManager;
    }
	
    public void displayGameOver() {
    	if(screenManager.getEntityManager().checkGame()) {
        	setGameOverText("WIN!");
		}else {
	    	setGameOverText("GAME OVER!");
		}
    }
    
    public void resumeGame() {
        // Resume the game logic here
		screenManager.setCurrentScreen("Main");
    }

    public void restartGamePC() {
        // Restart the game logic here
    	PlayableCharacter Player = new PlayableCharacter(screenManager.getWorld(), "PlayableCharacter.png", 10, 50, 0.75f, 100, 5, false, true);
    	screenManager.getEntityManager().addPlayableCharacter(Player);
//    	NonPlayableCharacter Enemy = new NonPlayableCharacter(screenManager.getWorld(), "Enemy.png", 200, 30, 200, 100, 10, true);
//    	NonPlayableCharacter Item = new NonPlayableCharacter(screenManager.getWorld(), "Weapon.png", 185, 75, 200, 100, 10, false);
//    	screenManager.getEntityManager().addNonPlayableCharacter(Enemy);
//    	screenManager.getEntityManager().addNonPlayableCharacter(Item);
		screenManager.setCurrentScreen("Game");
    }
    public void restartGameNPC() {
        // Restart the game logic here
    	NonPlayableCharacter Enemy = new NonPlayableCharacter(screenManager.getWorld(), "Enemy.png", 200, 30, 200, 100, 10, true);
    	NonPlayableCharacter Item = new NonPlayableCharacter(screenManager.getWorld(), "Weapon.png", 185, 75, 200, 100, 10, false);
    	screenManager.getEntityManager().addNonPlayableCharacter(Enemy);
    	screenManager.getEntityManager().addNonPlayableCharacter(Item);
    	
		screenManager.setCurrentScreen("Game");
    }
    
    public void restartGame() {    	
    	screenManager.getEntityManager().restartGame(screenManager.getWorld(), screenManager.getCamera());
		screenManager.setCurrentScreen("Game");
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
    	displayGameOver();
    	
    	// Calculate the x position to center the buttons horizontally
        buttonWidth = getButtonWidth();
        screenWidth = getScreenWidth();
        
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
    	
    }
	
	@Override
	public void render(float delta) {
    	show();
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
//        		if(screenManager.getEntityManager().getEntity("PlayableCharacter") != null) {
//        			System.out.println("Restart NPC");
//        			restartGameNPC();
//        		}else {
//        			System.out.println("Restart PC");
//                    restartGamePC();
//        		}
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
