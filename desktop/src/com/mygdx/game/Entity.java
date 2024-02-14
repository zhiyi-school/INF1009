package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Entity implements iMoveable, iCollide{
	protected Color color;
	protected Texture texture;
	protected Sprite sprite;
	protected float height;
	protected float width;
	protected float posX;
	protected float posY;
	protected Boolean aiCheck;
	
	// Default Constructor
	public Entity() {
		setColor(Color.RED);
		setPosX(0);
		setPosY(0);
		
	}
	
	// Parameterized Constructor
	public Entity(Color colorInput, float posXInput, float posYInput, String texture, Boolean aiCheck) {
		setColor(colorInput);
		setPosX(posXInput);
		setPosY(posYInput);
		setTexture(texture);
		setSprite(getTexture());
		setAICheck(aiCheck);
		
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color colorInput) {
		color = colorInput;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(String textureInput) {
		texture = new Texture(Gdx.files.internal(textureInput));
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Texture texture) {
		sprite = new Sprite(texture);
	}
	
	// There might be inbuild getHeight and getWidth functions
//	protected float getHeight() {
//		return height;
//	}
//	void setHeight(float heightInput) {
//		height = heightInput;
//	}
//	
//	protected float getWidth() {
//		return width;
//	}
//	void setWidth(float widthInput) {
//		width = widthInput;
//	}
	
	public float getPosX() {
		return posX;
	}
	public void setPosX(float x) {
		posX = x;
	}
	
	public float getPosY() {
		return posY;
	}
	public void setPosY(float y) {
		posY = y;
	}
	protected boolean getAICheck() {
		return aiCheck;
	}
	protected void setAICheck(boolean aiInput) {
		aiCheck = aiInput;
	}
	
	public void draw(SpriteBatch batch) {
		
	}
	
	public void moveAIControlled() {
		
	}
	public void moveUserControlled() {
		
	}
	
	public void checkCollide() {
		
	}
	

}
