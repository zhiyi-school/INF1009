package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class NonPlayableCharacter extends Character{
	
	Random rand = new Random();
	
	// Default Constructor
	public NonPlayableCharacter(){
		super(Color.BLACK, "", 0, 0, 1, 100, 2, false);
		
	}
	
	// Parameterized Constructor
	public NonPlayableCharacter(Color color, String texture, float x, float y, float speed, float health, float attack, Boolean aiCheck) {
		super(color, texture, x, y, speed, health, attack, aiCheck);
	}
	
	public void moveAIControlled() {
		
	}
	
	public void resetPosition() {
		setPosX(rand.nextInt(Gdx.graphics.getWidth() + 1));
		setPosY(rand.nextInt(Gdx.graphics.getHeight() + 1));
		
	}
}
