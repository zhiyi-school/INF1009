package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class NonPlayableCharacter extends Character{
	
	Random rand = new Random();
	private int randomNum = 0;
	
	private float moveSpeed = 50; // Adjust the speed of movement
    private int moveDir; // Indicates whether the NPC is
	protected static World worldDefault = new World(new Vector2(0, -9.8f), true);
    
	
	// Default Constructor
	public NonPlayableCharacter(){
		super(worldDefault, "", 0, 0, 1, 100, 2, false);
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
	
	
	public void despawn(World world) {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
	
	public void destroy() {
		getTexture().dispose();
	}
	
	public void moveAIControlled() {
		if (randomNum % 2 == 0) {
			moveDir = 1;	// Move Right
        } else if(randomNum % 2 == 1){
        	moveDir = 2;	// Move Left
        }
//        } else if(randomNum % 5 == 2){
//        	moveDir = 3;	// Move Up
//        } else if(randomNum % 5 == 3){
//        	moveDir = 4;	// Move Down
//        }
		
		npcMove(moveDir);
        
        // Check if NPC reaches the boundaries, then change direction
        if (getPosX() >= Gdx.graphics.getWidth() - (getTexture().getWidth() * 2) || getPosX() <= 0 || getPosY() >= Gdx.graphics.getHeight() - (getTexture().getHeight() * 2) || getPosY() <= 0) {
        	moveDir = 0;
        	randomNum = rand.nextInt(100);
        }
        
        body.setTransform(getPosX(), getPosY(), 0);
    }
	
	private void npcMove(int direction) {
		switch(moveDir) {
		    case 1:
		    	setPosX(Math.max(0, getPosX() + moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
		        break;
		    case 2:
		    	setPosX(Math.max(0, getPosX() - moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
		        break;
		    case 3:
		    	setPosY(Math.max(0, getPosY() + moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
		    	break;
		    case 4:
		    	setPosY(Math.max(0, getPosY() - moveSpeed * Gdx.graphics.getDeltaTime())); // Ensure x-coordinate cannot go below minX
		    	break;
		}
	}
	
	public void moveLeft() {
		
	}
	public void moveRight() {
		
	}
	public void moveUp() {
		
	}
	public void moveDown() {
		
	}
}
