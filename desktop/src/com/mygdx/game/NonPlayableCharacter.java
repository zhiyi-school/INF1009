package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class NonPlayableCharacter extends Character{
	
	Random rand = new Random();
	
	// Default Constructor
	public NonPlayableCharacter(){
		
	}
	
	// Parameterized Constructor
	public NonPlayableCharacter(Color color, Texture texture, float x, float y, float speed, float health, float attack) {
		super(color, texture, x, y, speed, health, attack);
	}
	
	public void moveAIControlled() {
		
	}
	
	public void resetPosition() {
		setPosX(rand.nextInt(Gdx.graphics.getWidth() + 1));
		setPosY(rand.nextInt(Gdx.graphics.getHeight() + 1));
		
	}
}
