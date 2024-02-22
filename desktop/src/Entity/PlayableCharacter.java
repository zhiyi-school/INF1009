package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PlayableCharacter extends Character{
	private Sound soundEffect;
    private boolean attackCheck;
	private int rightKey;
	private int leftKey;
	private int jumpKey;
	
	private float defaultX;
	private float defaultY;
	
	// Default Constructor
	public PlayableCharacter(World world){
		super(world, "", 0, 0, 0, 100, 1, false, true);
	}
	
	// Parameterized Constructor
	public PlayableCharacter(World world, String textureImage, float x, float y, float speed, float health, float attack, boolean die, Boolean aiCheck, int leftKey, int rightKey, int jumpKey, String soundEffect) {
		super(world, textureImage, x, y, speed, health, attack, die, aiCheck);
		setAttackCheck(false);
		setLeftKey(leftKey);
		setRightKey(rightKey);
		setJumpKey(jumpKey);
		setSoundEffect(soundEffect);
	}
	
	private void setDefaultX() {
		setPosX(defaultX);
	}
	private void setDefaultY() {
		setPosY(defaultY);
	}
	
	public int getLeftKey() {
		return leftKey;
	}
	private void setLeftKey(int leftKeyInput) {
		leftKey = leftKeyInput;
	}
	public int getRightKey() {
		return rightKey;
	}
	private void setRightKey(int rightKeyInput) {
		rightKey = rightKeyInput;
	}
	public int getJumpKey() {
		return jumpKey;
	}
	private void setJumpKey(int jumpKeyInput) {
		jumpKey = jumpKeyInput;
	}
	public void disposeSoundEffect() {
		soundEffect.dispose();
	}
	public Sound getSoundEffect() {
		return soundEffect;
	}
	private void setSoundEffect(String soundInput) {
		soundEffect = Gdx.audio.newSound(Gdx.files.internal(soundInput));
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
	public void moveUserControlled(float mapFullWidth) {
		if(!getDie()) {
			if(Gdx.input.isKeyPressed (getLeftKey())) {
				if(getBody().getPosition().x <= 0) {
					setPosX(0);
				}else {
					moveLeft();
				}
			}
			if(Gdx.input.isKeyPressed (getRightKey())) {
				moveRight();
			}
			if(Gdx.input.isKeyPressed (getJumpKey())) {
				getSoundEffect().play(0.05f);
				jump();
			}
			if(getBody().getPosition().x <= 0) {
				getBody().setTransform(new Vector2(0, getBody().getPosition().y), 0);
			}
//			System.out.println(mapFullWidth / 3f - (getTexture().getWidth() / 80f));
			if(getBody().getPosition().x >= (mapFullWidth / 3f)) {
				getBody().setTransform(new Vector2(mapFullWidth / 3f, getBody().getPosition().y), 0);
			}
			
		}
	}
	// Jumping movement. Applies Gravity
    public void jump() {
    	if(getBody().getLinearVelocity().y == 0) {
    		getBody().applyLinearImpulse(new Vector2(0, getSpeed() * 17.5f), getBody().getWorldCenter(), true);
    	}
    }	
	
	protected void moveLeft() {
        getBody().applyLinearImpulse(new Vector2(-getSpeed() / 1.65f, 0), getBody().getWorldCenter(), true);
    }

	protected void moveRight() {
        getBody().applyLinearImpulse(new Vector2(getSpeed() / 1.65f, 0), getBody().getWorldCenter(), true);
    }
	
	public void resetPosition() {
		setDefaultX();
		setDefaultY();
	}
}
