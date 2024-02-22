package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Scene implements Screen{

	private float buttonWidth;
	private float screenWidth;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
	
	public Scene(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font, float buttonWidth, float screenWidth){
		setBatch(batch);
		setShape(shapeRenderer);
		setMapFont(font);
		setButtonWidth(buttonWidth);
		setScreenWidth(screenWidth);
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
	public void setBatch(SpriteBatch batchInput) {
		batch = batchInput;
	}
	
	public ShapeRenderer getShape() {
		return shapeRenderer;
	}
	public void setShape(ShapeRenderer shapeRendererInput) {
		shapeRenderer = shapeRendererInput;
	}
	
	public BitmapFont getMapFont() {
		return font;
	}
	public void setMapFont(BitmapFont fontInput) {
		font = fontInput;
	}
	
	public float getButtonWidth() {
		return buttonWidth;
	}
	public void setButtonWidth(float buttonWidthInput) {
		buttonWidth = buttonWidthInput;
	}
	
	public float getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(float screenWidthInput) {
		screenWidth = screenWidthInput;
	}
	
	public void show () {
     	batch = getBatch();
    	shapeRenderer = getShape();
        font = getMapFont();
	}
	public abstract void render (float delta);
	public abstract void resize (int width, int height);
	public abstract void pause ();
	public abstract void resume ();
	public abstract void hide ();
	public abstract void dispose ();
}
