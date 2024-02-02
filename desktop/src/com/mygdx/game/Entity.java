package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;

public class Entity implements iMoveable{
	protected Color color;
	protected float height;
	protected float width;
	protected float posX;
	protected float posY;
	
	// Default Constructor
	public Entity() {
		
	}
	
	// Parameterized Constructor
	public Entity(Color colorInput, float posXInput, float posYInput) {
		setColor(colorInput);
		setPosX(posXInput);
		setPosY(posYInput);
		
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color colorInput) {
		color = colorInput;
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
	
	public void moveAIControlled() {
		
	}
	public void moveUserControlled() {
		
	}
	

}
