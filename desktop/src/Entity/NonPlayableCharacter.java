package Entity;

import com.badlogic.gdx.physics.box2d.World;

public class NonPlayableCharacter extends Character{
	
    private float elapsedTime;
    private boolean isMovingRight;
    
	// Default Constructor
	public NonPlayableCharacter(World world){
		super(world, "", 0, 0, 1, 100, 2, false);
	}
	
	// Parameterized Constructor
	public NonPlayableCharacter(World world, String textureImage, float x, float y, float speed, float health, float attack, Boolean aiCheck) {
		super(world, textureImage, x, y, speed, health, attack, aiCheck);
	}
	
	// Dispose 
	public void dispose(World world) {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
	public void destroy() {
		getTexture().dispose();
	}
	
	// AI movement
	public void moveAIControlled(float delta, float mapFullWidth) {
		elapsedTime += delta;

        // Toggle direction every 10 seconds
        if (elapsedTime >= 10 || getBody().getPosition().x <= 0) {
            isMovingRight = !isMovingRight; 
            elapsedTime = 0; 
        }else if(getBody().getPosition().x >= mapFullWidth - (getTexture().getWidth() * 2)) {
        	isMovingRight = !isMovingRight; 
        }

        // Move left if isMovingRight is false, otherwise move right
        if (!isMovingRight) {
            moveLeft();
        } else{
            moveRight();
        }
    }
}
