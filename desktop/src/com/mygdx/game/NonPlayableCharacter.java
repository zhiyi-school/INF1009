package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class NonPlayableCharacter extends Character{
	
	private Random rand = new Random();
	
	private float moveSpeed = 50; // Adjust the speed of movement
    private float elapsedTime;
    private boolean isMovingRight;
    
	
	// Default Constructor
	public NonPlayableCharacter(World world){
		super(world, "", 0, 0, 1, 100, 2, false);
	}
	
	// Parameterized Constructor
	public NonPlayableCharacter(World world, String textureImage, float x, float y, float speed, float health, float attack, Boolean aiCheck) {
		super(world, textureImage, x, y, speed, health, attack, aiCheck);
	}
	
	public void resetPosition() {
		setPosX(rand.nextInt(Gdx.graphics.getWidth() + 1));
		setPosY(rand.nextInt(Gdx.graphics.getHeight() + 1));
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), getPosX(), getPosY(), getTexture().getWidth() * 2, getTexture().getHeight() * 2);
	}
	public void draw(SpriteBatch batch, boolean Item) {
		if(Item) {
			batch.draw(getTexture(), getPosX(), getPosY(), getTexture().getWidth() * 0.5f, getTexture().getHeight() * 0.5f);
		}
	}
	
	// Dispose 
	public void dispose(World world) {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
	public void destroy() {
		getTexture().dispose();
	}
	
	// AI movement
	public void moveAIControlled(float delta) {
		elapsedTime += delta;

        // Toggle direction every 2 seconds
        if (elapsedTime >= 4 || getPosX() <= 0) {
            isMovingRight = !isMovingRight; // Toggle direction
            elapsedTime = 0; // Reset timer
        }else if(getPosX() >= Gdx.graphics.getWidth() - (getTexture().getWidth() * 2)) {
        	isMovingRight = !isMovingRight; // Toggle direction
        }

        // Move left if isMovingRight is false, otherwise move right
        if (!isMovingRight) {
            moveLeft();
        } else{
            moveRight();
        }
        // Update the NPC's position based on Box2D body
        getBody().setTransform(getPosX(), getPosY(), 0);
    }
	
	public void moveLeft() {
		setPosX(Math.max(0, getPosX() - moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
	}
	public void moveRight() {
		setPosX(Math.max(0, getPosX() + moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
	}
	public void moveUp() {
		setPosY(Math.max(0, getPosY() + moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
	}
	public void moveDown() {
		setPosY(Math.max(0, getPosY() - moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
	}
}
