package GameEngine.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import GameLayer.batchSingleton;
import GameLayer.worldSingleton;

public class NonPlayableCharacter extends Character{
	
    private float elapsedTime;
    private boolean isMovingRight;
    private World world = worldSingleton.getInstance();
    private SpriteBatch batch = batchSingleton.getInstance();
    
	// Default Constructor
	public NonPlayableCharacter(){
		super("", 0, 0, 1, 100, 2, false);
	}
	
	// Parameterized Constructor
	public NonPlayableCharacter(String textureImage, float x, float y, float speed, int lives, float attack, Boolean aiCheck) {
		super(textureImage, x, y, speed, lives, attack, aiCheck);
	}
	
	// Dispose 
	public void dispose() {
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
        
        // Checking if the Player is at Map Boundaries
		if(getBody().getPosition().x <= 0) {
			getBody().setTransform(new Vector2(0, getBody().getPosition().y), 0);
		}
		if(getBody().getPosition().x >= (mapFullWidth / 3f)) {
			getBody().setTransform(new Vector2(mapFullWidth / 3f, getBody().getPosition().y), 0);
		}
    }
}
