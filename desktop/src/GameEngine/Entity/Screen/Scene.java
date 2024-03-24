package GameEngine.Entity.Screen;

import com.badlogic.gdx.Screen;

public abstract class Scene implements Screen{

	private float buttonWidth;
	protected float screenWidth;
	protected float screenHeight;
	
	public Scene(float buttonWidth, float screenWidth, float screenHeight){
		setButtonWidth(buttonWidth);
		setScreenWidth(screenWidth);
		setScreenHeight(screenHeight);
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
	
	public float getScreenHeight() {
		return screenHeight;
	}
	
	public void setScreenHeight(float screenHeightInput) {
		screenWidth = screenHeightInput;
	}
	
	public void show () {}
	
	public abstract String getScreen();	
	public abstract void render (float delta);
	public abstract void resize (int width, int height);
	public abstract void pause ();
	public abstract void resume ();
	public abstract void hide ();
	public abstract void dispose ();
}
