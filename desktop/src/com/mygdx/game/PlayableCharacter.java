package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class PlayableCharacter extends Character{
	
	private float defaultX;
	private float defaultY;
	
	// Default Constructor
		public PlayableCharacter(){
			
		}
		
		// Parameterized Constructor
		public PlayableCharacter(Color color, Texture texture, float x, float y, float speed, float health, float attack, boolean die) {
			super(color, texture, x, y, speed, health, attack, die);
		}
		
		private void setDefaultX() {
			posX = defaultX;
		}
		private void setDefaultY() {
			posY = defaultY;
		}
		
		public void moveUserControlled() {
			if(Gdx.input.isKeyPressed (Keys.A)) {
				setPosX(getPosX() - speed * Gdx.graphics.getDeltaTime());
			}
			if(Gdx.input.isKeyPressed (Keys.D)) {
				setPosX(getPosX() + speed * Gdx.graphics.getDeltaTime());
			}
			if(Gdx.input.isKeyPressed (Keys.SPACE)) {
				setPosY(getPosY() + speed * Gdx.graphics.getDeltaTime());
			}
			
			if(getPosX() <= 0) {
				setPosX(getPosX());
			}else if(getPosX() >= Gdx.graphics.getWidth()) {
				setPosX(getPosX());
			}
		}
		
		public void resetPosition() {
			setDefaultX();
			setDefaultY();
			
		}

}
