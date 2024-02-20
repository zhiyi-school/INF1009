package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMaster extends ApplicationAdapter {
	private EntityManager entityManager;
	private World world;
    private Sound soundEffect;
	
	@Override
	public void create() {
		world = new World(new Vector2(0, -9.8f), true);
		soundEffect = Gdx.audio.newSound(Gdx.files.internal("JumpSoundEffect.wav"));
		entityManager = new EntityManager(world);
	}	
	
	public void update() {
		entityManager.collisionEquip(world);
		entityManager.collisionFight(world);
	}
	
	@Override
	public void render() {
		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
//        System.out.println(entityManager.getNum());
        
        // Check if update outside of world.set is required
		if(entityManager.getNum() > 0) {
			update();
		}else {
	        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
//	        System.out.println(world.getContactCount());
	        
	        OrthographicCamera camera = new OrthographicCamera();
	        camera.setToOrtho(false, 1, 1);		
	        
			entityManager.entityDraw();
			entityManager.movement(soundEffect);
		}	
	}
	
	@Override
    public void dispose() {
        world.dispose();
        entityManager.diposeEntities();
		soundEffect.dispose();
    }
}
