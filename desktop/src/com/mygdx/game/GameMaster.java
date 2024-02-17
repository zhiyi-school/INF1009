package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMaster extends ApplicationAdapter{
	
	private EntityManager entityManager;
	private World world;
	
	
	@Override
	public void create() {
		world = new World(new Vector2(0, -9.8f), true);		
		entityManager = new EntityManager(world);
		entityManager.setCollision(world);
	}	
	
	@Override
	public void render() {
		
		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 1, 1);		
        
		entityManager.entityDraw();
		entityManager.movement();
		//entityManager.checkCollide();
		//entityManager.checker();
		
		
	}
	
	@Override
    public void dispose() {
        world.dispose();
        entityManager.diposeEntities();
    }
}
