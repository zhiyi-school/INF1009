package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PlayableCharacter extends Character{
	
	private static final float JUMP_VELOCITY = 500f; // Adjust this value to control jump height
    private static final float GRAVITY = -1500f; // Adjust this value to control gravity
    private boolean isJumping = false;
    private float verticalVelocity = 0;
    
    private boolean attackCheck;
    
	private float defaultX;
	private float defaultY;
	
	// Default Constructor
	public PlayableCharacter(World world){
		super(world, "", 0, 0, 0, 100, 1, false, true);
	}
	
	// Parameterized Constructor
	public PlayableCharacter(World world, String textureImage, float x, float y, float speed, float health, float attack, boolean die, Boolean aiCheck) {
		super(world, textureImage, x, y, speed, health, attack, die, aiCheck);
		setAttackCheck(false);
	}
	
	private void setDefaultX() {
		setPosX(defaultX);
	}
	private void setDefaultY() {
		setPosY(defaultY);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), ((getBody().getPosition().x) * 3f) - (getTexture().getWidth() / 80f), (getBody().getPosition().y * 3f)  - (getTexture().getHeight() / 110f), getTexture().getWidth() / 50f, getTexture().getHeight() / 50f);
	}
	
	// Dispose 
	public void dispose(World world) {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
	public void destroy(){
		getTexture().dispose();
	}
	
	public boolean getAttackCheck() {
		return attackCheck;
	}
	public void setAttackCheck(boolean attackInput) {
		attackCheck = attackInput;
	}
	
	// Movement controls
	public void moveUserControlled(Sound soundEffect, float mapFullWidth) {
		if(!getDie()) {
			if(Gdx.input.isKeyPressed (Keys.A)) {
				if(getBody().getPosition().x <= 0) {
					setPosX(0);
				}else {
					moveLeft();
				}
			}
			if(Gdx.input.isKeyPressed (Keys.D)) {
				moveRight();
			}
			if(Gdx.input.isKeyPressed (Keys.SPACE)) {
				soundEffect.play(0.01f);
				jump();
			}
			if(getBody().getPosition().x <= 0) {
				setPosX(0);
			}
			if(getBody().getPosition().x >= mapFullWidth) {
				setPosX(mapFullWidth);
			}
			
		}
	}
	// Jumping movement. Applies Gravity
    public void jump() {
//    	getBody().setLinearVelocity(new Vector2(0, 0));
        getBody().applyLinearImpulse(new Vector2(0, getSpeed()), getBody().getWorldCenter(), true);
    }	
	
	protected void moveLeft() {
//    	getBody().setLinearVelocity(new Vector2(0, 0));
        getBody().applyLinearImpulse(new Vector2(-getSpeed() / 1.5f, 0), getBody().getWorldCenter(), true);
    }

	protected void moveRight() {
//    	getBody().setLinearVelocity(new Vector2(0, 0));
        getBody().applyLinearImpulse(new Vector2(getSpeed() / 1.5f, 0), getBody().getWorldCenter(), true);
    }
	
	public void resetPosition() {
		setDefaultX();
		setDefaultY();
	}
}
