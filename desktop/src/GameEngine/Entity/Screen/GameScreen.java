package GameEngine.Entity.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Input.Keys;

import GameLayer.HUD; // Import HUD class

public class GameScreen extends Scene {
	private final Stage stage;
	private ScreenManager screenManager;
	private String gameText;
	private Button pauseButton;
	private HUD hud; // Add HUD instance

	private float buttonWidth;
	private float buttonHeight = 60;
	private float gameTime = 0;

	private float mouseX, mouseY;

	public GameScreen(Stage stage, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth, float screenHeight, HUD hud) {
		super(batch, shapeRenderer, font, buttonWidth, screenWidth, screenHeight);
		this.stage = stage;
		this.buttonWidth = buttonWidth;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		setPauseButtonPositions();
		this.hud = hud; // Assign the passed HUD instance

		//Add HUD stage to GameScreen Stage
		stage.addActor(hud);
	}




	public String getScreen() {
		return "Game";
	}

	private void setPauseButtonPositions() {
		float pauseButtonX = screenWidth - buttonWidth;
		float pauseButtonY = screenHeight - buttonHeight;
		pauseButton = new Button(pauseButtonX, pauseButtonY, buttonWidth, buttonHeight);
		pauseButton.setText("II");
		pauseButton.setColour(Color.RED);
	}

	public void pauseGame() {
		screenManager.setCurrentScreen("Pause");
	}

	@Override
	public void show() {
		gameText = "MARIO GAME";
	}


	@Override
	public void render(float delta, SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameTime += delta;

		// Debug output
		System.out.println("HUD visibility: " + hud.isVisible());

		pauseButton.render(shapeRenderer, batch, font);

		batch.begin();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		float x = screenWidth / 2f;
		float y = screenHeight * 0.8f;
		font.draw(batch, gameText, x, y, 0, Align.center, false);

		font.setColor(Color.WHITE);
		font.getData().setScale(2);
		font.draw(batch, "Time: " + (int) gameTime, 100, screenHeight - 20);
		batch.end();

		hud.render();

		mouseX = Gdx.input.getX();
		mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		if (pauseButton.hover(mouseX, mouseY)) {
			pauseButton.setColour(Color.YELLOW);
			if (Gdx.input.justTouched()) {
				pauseGame();
			}
		} else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			pauseGame();
		} else {
			pauseButton.setColour(Color.RED);
		}

		// Debug output
		System.out.println("Acting stage...");
		stage.act(delta);
		System.out.println("Drawing stage...");
		stage.draw();
	}





	public void setScreenManager(ScreenManager screenManagerInput) {
		screenManager = screenManagerInput;
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
