package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMaster extends ApplicationAdapter{
	
	private EntityManager entityManager;
	
	@Override
	public void create() {		
		entityManager = new EntityManager();
		
	}
	
	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		entityManager.entityDraw();
		entityManager.entityMovement();
		entityManager.checkCollide();
	}
}
