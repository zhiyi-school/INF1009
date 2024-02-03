package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class EntityManager {
	
	private List<Entity> entityList;
	Random rand = new Random();
	
	public EntityManager() {
		entityList = new ArrayList<Entity>();
		
		// NPC and PC
		entityList.add(new NonPlayableCharacter(Color.WHITE, texture, rand.nextInt(Gdx.graphics.getHeight() + 1), 
				rand.nextInt(Gdx.graphics.getHeight() + 1), 200, 100, 10));
		entityList.add(new PlayableCharacter(Color.CYAN, texture, 0, 0, 200, 100, 5, false));
		
	}
}
