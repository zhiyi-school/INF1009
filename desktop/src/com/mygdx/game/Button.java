package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
	
	 private Rectangle bounds;
	 private String text;
	 private BitmapFont font;
	 private Color color;
	 private float posX;
	 private float posY;
	 
	 public Button(float x, float y, float width, float height) {
		 this.posX= x;
		 this.posY= y;
		 this.bounds = new Rectangle(x, y, width, height);
		 this.font = new BitmapFont();
	 }
	 public void setColour(Color colour)
	{
			this.color= colour;
	}
	 public void setText(String Text)
	{
			this.text = Text;
			
	}
	 public boolean isClicked() {
	        return bounds.contains(this.posX, this.posY);
	 }
	 public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
		 // Draw the button rectangle
		 shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		 shapeRenderer.setColor(this.color);
		 shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		 shapeRenderer.end();

		 // Draw the text inside the button
		 batch.begin();
		 font.setColor(Color.BLACK);
		 font.getData().setScale(2);
		 font.draw(batch, text, bounds.x, bounds.y + bounds.height / 2, bounds.width, Align.center, true);
		 batch.end();
	 }

	public void dispose()
	{
		font.dispose();
	}
}
