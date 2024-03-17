package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Character extends Entity implements iMoveable{
	
	private String image;
	private float speed;
	private float attack;
	private boolean die;
	private int lives;
	
	// Default Constructor
	public Character(World world) {
		super(world, "", 0, 0, false);
		setImage("");
		setTexture(image);
		setSpeed(1);
		setAttack(0);
		setDie(false);
		setLives(1);
	}
	
	// Parameterized Constructor
	public Character(World world, String textureImage, float posXInput, float posYInput, String image, float speed, float attack, boolean die, int lives, Boolean aiCheck) {
		super(world, textureImage, posXInput, posYInput, aiCheck);
		setImage(image);
		setSpeed(speed);
		setAttack(attack);
		setDie(die);
		setLives(lives);
	}
	
	// NonPlayableCharacter
	public Character(World world, String textureImage, float x, float y, float speed, int lives, float attack, Boolean aiCheck) {
		super(world, textureImage, x, y, aiCheck);
		setSpeed(speed);
		setLives(lives);
		setAttack(attack);
	}
	
	// PlayableCharacter
	public Character(World world, String textureImage, float x, float y, float speed, int lives, float attack, boolean die, Boolean aiCheck) {
		super(world, textureImage, x, y, aiCheck);
		setSpeed(speed);
		setLives(lives);
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
		return speed / 200f;
	}
	public void setSpeed(float speedInput) {
		speed = speedInput ;
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
	
	public int getLives() {
		return lives;
	}
	public void setLives(int livesInput) {
		lives = livesInput;
	}
	
	public void draw(SpriteBatch batch) {
		batch.begin();
		batch.draw(getTexture(), ((getBody().getPosition().x) * 3f) - (getTexture().getWidth() / 110f), (getBody().getPosition().y * 3f)  - (getTexture().getHeight() / 110f), getTexture().getWidth() / 60f, getTexture().getHeight() / 60f);
		batch.end();
	}
	
	public void moveAIControlled(float delta, float mapFullWidth){
		
	}
	public void moveUserControlled(Sound soundEffect, float mapFullWidth) {
		
	}
	
	protected void moveLeft() {
        getBody().applyLinearImpulse(new Vector2(-getSpeed() * Gdx.graphics.getDeltaTime(), 0), getBody().getWorldCenter(), true);
    }
	protected void moveRight() {
        getBody().applyLinearImpulse(new Vector2(getSpeed() * Gdx.graphics.getDeltaTime(), 0), getBody().getWorldCenter(), true);
    }
}
