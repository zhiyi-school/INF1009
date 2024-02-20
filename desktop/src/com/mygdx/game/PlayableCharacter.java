package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class PlayableCharacter extends Character{
	
	private static final float JUMP_VELOCITY = 500f; // Adjust this value to control jump height
    private static final float GRAVITY = -1500f; // Adjust this value to control gravity
    private boolean isJumping = false;
    private float verticalVelocity = 0;
    
    private boolean attackCheck;
    
	private float defaultX;
	private float defaultY;
	
	// Default Constructor
	public PlayableCharacter(World world){
		super(world, "", 0, 0, 0, 100, 1, false, true);
	}
	
	// Parameterized Constructor
	public PlayableCharacter(World world, String textureImage, float x, float y, float speed, float health, float attack, boolean die, Boolean aiCheck) {
		super(world, textureImage, x, y, speed, health, attack, die, aiCheck);
		setAttackCheck(false);
	}
	
	private void setDefaultX() {
		setPosX(defaultX);
	}
	private void setDefaultY() {
		setPosY(defaultY);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), getPosX(), getPosY(), getTexture().getWidth() * 3, getTexture().getHeight() * 3);
	}
	
	// Dispose 
	public void dispose(World world) {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
	
	public void destroy(){
		getTexture().dispose();
	}
	
	public boolean getAttackCheck() {
		return attackCheck;
	}
	
	public void setAttackCheck(boolean attackInput) {
		attackCheck = attackInput;
	}
	
	// Movement controls
	public void moveUserControlled(Sound soundEffect) {
		if(!getDie()) {
			if(Gdx.input.isKeyPressed (Keys.A)) {
				moveLeft();
			}
			if(Gdx.input.isKeyPressed (Keys.D)) {
				moveRight();
			}
			if(Gdx.input.isKeyPressed (Keys.SPACE)) {
				soundEffect.play();
				jump();
			}
			jumpUpdate();
			if(getPosX() <= 0) {
				setPosX(0);
			}
			if(getPosX() >= Gdx.graphics.getWidth() - (getTexture().getWidth() * 3)) {
				setPosX(Gdx.graphics.getWidth() - (getTexture().getWidth() * 3));
			}
		}
	}
	
	// Jumping movement
    public void jumpUpdate() {
        // Apply gravity
        verticalVelocity += GRAVITY * Gdx.graphics.getDeltaTime();

        // Update position based on vertical velocity
        setPosY(getPosY() + verticalVelocity * Gdx.graphics.getDeltaTime());
        
        // Set the position of the Box2D body
        getBody().setTransform(getPosX(), getPosY(), 0); // Assuming no rotation
        
        // Check if the character is on the ground
        if (getPosY() <= 0) {
            setPosY(0);
            isJumping = false;
            verticalVelocity = 0;
        }
    }
    
	private void jump() {
        if (!isJumping) {
            verticalVelocity = JUMP_VELOCITY;
            isJumping = true;
        }
    }
	
	public void resetPosition() {
		setDefaultX();
		setDefaultY();
	}
	
	
	protected void moveLeft() {
        setPosX(getPosX() - (getSpeed() * Gdx.graphics.getDeltaTime()));
    }

	protected void moveRight() {
        setPosX(getPosX() + (getSpeed() * Gdx.graphics.getDeltaTime()));
    }

	protected void moveUp() {
        setPosY(getPosY() + (getSpeed() * Gdx.graphics.getDeltaTime()));
    }

	protected void moveDown() {
        setPosY(getPosY() - (getSpeed() * Gdx.graphics.getDeltaTime()));
    }
}