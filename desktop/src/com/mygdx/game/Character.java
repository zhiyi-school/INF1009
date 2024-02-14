package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Character extends Entity{
	
	private String image;
	protected float speed;
	protected float attack;
	protected boolean die;
	protected float health;
	
	private SpriteBatch batch = new SpriteBatch();
	
	// Default Constructor
	public Character() {
		super(Color.YELLOW, 0, 0, "", false);
		setTexture(image);
		setImage("");
		setSpeed(1);
		setAttack(0);
		setDie(false);
		setHealth(100);
		
	}
	
	// Parameterized Constructor
	public Character(Color colorInput, float posXInput, float posYInput, String texture, String image, float speed, float attack, boolean die, float health, Boolean aiCheck) {
		super(colorInput, posXInput, posYInput, texture, aiCheck);
		setImage(image);
		setSpeed(speed);
		setAttack(attack);
		setDie(die);
		setHealth(health);
	}
	
	// NonPlayableCharacter
	public Character(Color colorInput, String texture, float x, float y, float speed, float health, float attack, Boolean aiCheck) {
		super(colorInput, x, y, texture, aiCheck);
		setSpeed(speed);
		setHealth(health);
		setAttack(attack);
	}
	
	// PlayableCharacter
		public Character(Color colorInput, String texture, float x, float y, float speed, float health, float attack, boolean die, Boolean aiCheck) {
			super(colorInput, x, y, texture, aiCheck);
			setSpeed(speed);
			setHealth(health);
			setAttack(attack);
			setDie(die);
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
	
	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), getPosX(), getPosY(), getTexture().getWidth(), getTexture().getHeight());
	}
	

}
