package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Character extends Entity{
	
	private String image;
	private float speed;
	private float attack;
	private boolean die;
	private float health;
	
	protected abstract void moveUp();
	protected abstract void moveDown();
	protected abstract void moveLeft();
	protected abstract void moveRight();
	public abstract void draw(SpriteBatch batch);
	
	// Default Constructor
	public Character(World world) {
		super(world, "", 0, 0, false);
		setImage("");
		setTexture(image);
		setSpeed(1);
		setAttack(0);
		setDie(false);
		setHealth(100);
	}
	
	// Parameterized Constructor
	public Character(World world, String textureImage, float posXInput, float posYInput, String image, float speed, float attack, boolean die, float health, Boolean aiCheck) {
		super(world, textureImage, posXInput, posYInput, aiCheck);
		setImage(image);
		setSpeed(speed);
		setAttack(attack);
		setDie(die);
		setHealth(health);
	}
	
	// NonPlayableCharacter
	public Character(World world, String textureImage, float x, float y, float speed, float health, float attack, Boolean aiCheck) {
		super(world, textureImage, x, y, aiCheck);
		setSpeed(speed);
		setHealth(health);
		setAttack(attack);
	}
	
	// PlayableCharacter
	public Character(World world, String textureImage, float x, float y, float speed, float health, float attack, boolean die, Boolean aiCheck) {
		super(world, textureImage, x, y, aiCheck);
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
	public void setDie(boolean dieInput) {
		die = dieInput;
	}
	
	public float getHealth() {
		return health;
	}
	public void setHealth(float healthInput) {
		health = healthInput;
	}
	
	
	
	

}
