package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Character extends Entity{
	
	private Texture texture;
	private String image;
	protected float speed;
	protected float attack;
	protected boolean die;
	protected float health;
	
	// Default Constructor
	public Character() {
		
	}
	
	// Parameterized Constructor
	public Character(Color colorInput, float posXInput, float posYInput, Texture texture, String image, float speed, float attack, boolean die, float health) {
		super(colorInput, posXInput, posYInput);
		setTexture(texture);
		setImage(image);
		setSpeed(speed);
		setAttack(attack);
		setDie(die);
		setHealth(health);
	}
	
	// NonPlayableCharacter
	public Character(Color colorInput, Texture texture, float x, float y, float speed, float health, float attack) {
		super(colorInput, x, y);
		setTexture(texture);
		setSpeed(speed);
		setHealth(health);
		setAttack(attack);
	}
	
	// PlayableCharacter
		public Character(Color colorInput, Texture texture, float x, float y, float speed, float health, float attack, boolean die) {
			super(colorInput, x, y);
			setTexture(texture);
			setSpeed(speed);
			setHealth(health);
			setAttack(attack);
			setDie(die);
		}
	
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture textureInput) {
		texture = textureInput;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String imageInput) {
		image = imageInput;
	}
	
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speedInput) {
		speed = speedInput;
	}
	
	public float getAttack() {
		return attack;
	}
	public void setAttack(float attackInput) {
		attack = attackInput;
	}
	
	public boolean getDie() {
		return die;
	}
	public void setDie(boolean dieCheck) {
		die = dieCheck;
	}
	
	public float getHealth() {
		return health;
	}
	public void setHealth(float healthInput) {
		health = healthInput;
	}
	
	public void moveLeft() {
		
	}
	public void moveRight() {
		
	}

}
