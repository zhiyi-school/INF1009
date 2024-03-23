package GameEngine.Entity.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

import GameLayer.batchSingleton;
import GameLayer.fontSingleton;
import GameLayer.shapeSingleton;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
	
	 private Rectangle bounds;
	 private String text;
	 private Color color;
	 
	 private static BitmapFont font = fontSingleton.getInstance();
	 private static SpriteBatch batch = batchSingleton.getInstance();
	    private static ShapeRenderer shapeRenderer = shapeSingleton.getInstance();
	 
	 public Button(float x, float y, float width, float height) {
		 this.bounds = new Rectangle(x, y, width, height);
	 }
	 
	 public void setColour(Color colour) {
		this.color= colour;
	 }
	 
	 public void setText(String text) {
		this.text = text != null ? text : ""; // Ensure text is not null
	}
	 
	 public boolean hover(float touchX, float touchY) {
		 return bounds.contains(touchX, touchY);
	 }
	
	 public void render() {
		 // Draw the button rectangle
		 shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		 shapeRenderer.setColor(color);
		 shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		 shapeRenderer.end();

		 // Draw the text inside the button
		 batch.begin();
		 font.setColor(Color.BLACK);
		 font.getData().setScale(2);
		 font.draw(batch, text, bounds.x, bounds.y + bounds.height / 2, bounds.width, Align.center, true);
		 batch.end();
	 }
}
