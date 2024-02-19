package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Character extends Entity{
	
	private String image;
	protected float speed;
	protected float attack;
	protected boolean die;
	protected float health;
	protected static World worldDefault = new World(new Vector2(0, -9.8f), true);
	
	private SpriteBatch batch = new SpriteBatch();
	
	protected abstract void moveUp();
	protected abstract void moveDown();
	protected abstract void moveLeft();
	protected abstract void moveRight();
	public abstract void draw(SpriteBatch batch);
	
	// Default Constructor
	public Character() {
		super(worldDefault, "", 0, 0, false);
		setTexture(image);
		setImage("");
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
	public void setDie(boolean dieCheck) {
		die = dieCheck;
	}
	
	public float getHealth() {
		return health;
	}
	public void setHealth(float healthInput) {
		health = healthInput;
	}
	
	
	
	

}
