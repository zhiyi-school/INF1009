package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class MainMenuScreen extends Scene {
	private ScreenManager screenManager; // edited this
	
	private Screen pauseScreen;
	private Screen currentScreen;
	private Screen instructionsScreen; // edited this
	
	// Calculate the x position to center the buttons horizontally
    private float buttonWidth; // Assuming all buttons have the same width
    private float screenWidth;
    private float buttonSpacing = 25; // Spacing between buttons
    private float totalButtonWidth = 3 * buttonWidth + 2 * buttonSpacing; // Total width of all buttons and spacing
    private float startX = (screenWidth - totalButtonWidth) / 2; // Start x position for the first button
	
	private Button startButton;
	private Button instructionsButton;
	private Button exitButton;	

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    
    private float mouseX, mouseY;
    
    public MainMenuScreen(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth) {
    	super(batch, shapeRenderer, font, buttonWidth, screenWidth);
    }
    
    @Override
    public void show() {
     	batch = getBatch();
    	shapeRenderer = getShape();
        font = getMapFont();

        buttonWidth = getButtonWidth();
        screenWidth = getScreenWidth();
        
    	// Create start button
    	startButton = new Button(startX + 70, 100, buttonWidth - 70, 60);
    	startButton.setText("Start");
    	startButton.setColour(Color.RED);
    	
    	// Create instruction button
    	instructionsButton = new Button(startX + buttonWidth + buttonSpacing, 100, buttonWidth, 60);
    	instructionsButton.setText("Instructions");
    	instructionsButton.setColour(Color.RED);
    	
    	// Create exit button
    	exitButton = new Button(startX + 2 * (buttonWidth + buttonSpacing), 100, buttonWidth - 70, 60);
    	exitButton.setText("Exit");
    	exitButton.setColour(Color.RED);
        
    }

    public void render(float delta) {
    	show();
        // Clear the screen
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        startButton.render(shapeRenderer,batch);
        instructionsButton.render(shapeRenderer,batch);
        exitButton.render(shapeRenderer, batch);
        
        //Draw text on screen
        batch.begin();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
	    float x = Gdx.graphics.getWidth() / 2f;
	    float y = Gdx.graphics.getHeight() * 0.7f; 
	    font.draw(batch, "Welcome to our game!", x, y, 0, Align.center, false);
		batch.end();
		
		// Check if the mouse is inside the rectangle
		mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        System.out.println(startButton);
        
        if (startButton.hover(mouseX, mouseY)==true) {
        	startButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
//        		System.out.println("Start clicked!");
        		screenManager.setCurrentScreen("Game");
        	}
        }
        else if (instructionsButton.hover(mouseX, mouseY)==true) {
        	instructionsButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		System.out.println("Instruction clicked!");
        		screenManager.setCurrentScreen("Instruction"); // edited this
        	}
        }
        else if (exitButton.hover(mouseX, mouseY)==true) {
        	exitButton.setColour(Color.YELLOW);
        	if(Gdx.input.isTouched())
        	{
        		Gdx.app.exit();
        	}
        	
        }
        else {
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
    	// Dispose stage when it's no longer needed
    	startButton.dispose();
    	instructionsButton.dispose();
    	exitButton.dispose();
    }
	
	public String getScreen() {
		String screen = "mainMenu";
		return screen;
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
