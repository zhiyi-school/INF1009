package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameMaster extends ApplicationAdapter {
	private TextureAtlas textureAtlas;
    	private TextureRegion originalCharacterRegion, originalWeaponRegion, originalEnemyRegion;
    	private TextureRegion croppedCharacterRegion, croppedWeaponRegion, croppedEnemyRegion;
	
	@Override
	public void create() {
		textureAtlas = new TextureAtlas("Entities.pack");
        
        	// Cropping onto a single Character image
	        originalCharacterRegion = textureAtlas.findRegion("PlayableCharacter");
	        croppedCharacterRegion = new TextureRegion(originalCharacterRegion, 140, 0, 22, 37);
	        
	        // Cropping onto a single Weapon image
	        originalWeaponRegion = textureAtlas.findRegion("Weapon");
	        croppedWeaponRegion = new TextureRegion(originalWeaponRegion, 0, 0, 120, 100);
	        
	        // Cropping onto a single Enemy image
	        originalEnemyRegion = textureAtlas.findRegion("Enemy");
	        croppedEnemyRegion = new TextureRegion(originalEnemyRegion, 0, 0, 50, 64);
	}
	
	@Override
	public void render() {
		batch.begin();
		// Define starting position of Entities and have it enlarged
	        batch.draw(croppedCharacterRegion, 100, 5, croppedCharacterRegion.getRegionWidth() * 3, croppedCharacterRegion.getRegionHeight() * 3);
	        batch.draw(croppedWeaponRegion, 250, 100, croppedWeaponRegion.getRegionWidth(), croppedWeaponRegion.getRegionHeight());
	        batch.draw(croppedEnemyRegion, 400, 20, croppedEnemyRegion.getRegionWidth() * 2, croppedEnemyRegion.getRegionHeight() * 2);
		batch.end();
	}
}
