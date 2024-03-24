package GameEngine.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import GameLayer.batchSingleton;
import GameLayer.worldSingleton;

public class PlayableCharacter extends Character{
	private Sound soundEffect;
	private Sound wordSound;
    private boolean attackCheck;
	private int rightKey;
	private int leftKey;
	private int jumpKey;
	private int downKey;
	private float defaultX;
	private float defaultY;
	private ArrayList<String> words;
	private String guess;
	private String score;

    private World world = worldSingleton.getInstance();
    private SpriteBatch batch = batchSingleton.getInstance();
    
	// Default Constructor
	public PlayableCharacter(){
		super("", 0, 0, 0, 100, 1, false, true);
	}
	
	// Parameterized Constructor
	public PlayableCharacter(String textureImage, float x, float y, float speed, int lives, float attack, boolean die, Boolean aiCheck, int leftKey, int rightKey, int jumpKey, int downKey, String soundEffect) {
		super(textureImage, x, y, speed, lives, attack, die, aiCheck);
		words = new ArrayList<String>();
		setAttackCheck(false);
		setLeftKey(leftKey);
		setRightKey(rightKey);
		setJumpKey(jumpKey);
		setDownKey(downKey);
		setSoundEffect(soundEffect);
		setGuess(null);
		setScore("");
		setDefaultX(getBody().getPosition().x);
		setDefaultY(getBody().getPosition().y);
		readFile();
	}
	
	private void setDefaultX(float inputX) {
		defaultX = inputX;
	}
	private float getDefaultX() {
		return defaultX;
	}
	private void setDefaultY(float inputY) {
		defaultY = inputY;
	}
	private float getDefaultY() {
		return defaultY;
	}	
	public int getLeftKey() {
		return leftKey;
	}
	public void setLeftKey(int leftKeyInput) {
		leftKey = leftKeyInput;
	}
	public int getRightKey() {
		return rightKey;
	}
	public void setRightKey(int rightKeyInput) {
		rightKey = rightKeyInput;
	}
	public int getJumpKey() {
		return jumpKey;
	}
	public void setJumpKey(int jumpKeyInput) {
		jumpKey = jumpKeyInput;
	}
	public int getDownKey() {
		return downKey;
	}
	public void setDownKey(int downKeyInput) {
		downKey = downKeyInput;
	}
	public void disposeSoundEffect() {
		soundEffect.dispose();
	}
	public Sound getSoundEffect() {
		return soundEffect;
	}
	public void setSoundEffect(String soundInput) {
		soundEffect = Gdx.audio.newSound(Gdx.files.internal(soundInput));
	}
	public boolean getAttackCheck() {
		return attackCheck;
	}
	public void setAttackCheck(boolean attackInput) {
		attackCheck = attackInput;
	}
	public String getGuess() {
		return guess;
	}
	public void setGuess(String guessInput) {
		guess = guessInput;
	}
	public String getWord(int i) {
		return words.get(i);
	}
	public int getWordSize() {
		return words.size();	
	}
	public void setWordSound(String wordInput) {
		wordSound = Gdx.audio.newSound(Gdx.files.internal("words/" + wordInput + ".mp3"));
	}
	public Sound getWordSound() {
		return wordSound;
	}
	public void setScore(String scoreInput) {
		if(score == null) {
			score = scoreInput;
		}else {
			score += scoreInput;
		}
	}
	public String getScore() {
		return score;
	}
	public boolean checkWin(List<PlayableCharacter> pcList) {
		if(!(getScore()).equals(getGuess().substring(0, getScore().length()))) {
			this.destroy();
			this.dispose();
			pcList.remove(this);
			return true;
		}else if(getScore().equals(getGuess())){
			return true;
		}else {
			return false;
		}
	}

	public void draw() {
		batch.begin();
		batch.draw(getTexture(), ((getBody().getPosition().x) * 3f) - (getTexture().getWidth() / 80f), (getBody().getPosition().y * 3f)  - (getTexture().getHeight() / 110f), getTexture().getWidth() / 50f, getTexture().getHeight() / 50f);
		batch.end();
	}
	
	// Dispose 
	public void dispose() {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
	public void destroy(){
		getTexture().dispose();
	}
	
	public void setDefaultPos() {
		getBody().setLinearVelocity(new Vector2(0, 0));
		getBody().setTransform(new Vector2(getDefaultX(), getDefaultY()), 0);
	}
	
	// Movement controls
	public void moveUserControlled(float mapFullWidth) {
		if(!getDie()) {
			if(Gdx.input.isKeyPressed (getLeftKey())) {
				moveLeft();
			}
			if(Gdx.input.isKeyPressed (getRightKey())) {
				moveRight();
			}
			if(Gdx.input.isKeyPressed (getJumpKey())) {
				jump();
			}
			if(Gdx.input.isKeyPressed (getDownKey())) {
				moveDown();
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
	
	// Movement
    public void jump() {
    	// Jump only if on the ground
    	if(getBody().getLinearVelocity().y == 0) {
			getSoundEffect().play(0.01f);
    		getBody().applyLinearImpulse(new Vector2(0, getSpeed() * 17.5f), getBody().getWorldCenter(), true);
    	}
//		getBody().setTransform(new Vector2(getBody().getPosition().x, getBody().getPosition().y + (getSpeed() * 2)), 0);
    }	
    public void moveDown() {
//		getBody().setTransform(new Vector2(getBody().getPosition().x, getBody().getPosition().y - (getSpeed() * 2)), 0);
    }
	protected void moveLeft() {
//		getBody().setTransform(new Vector2(getBody().getPosition().x - (getSpeed() * 2), getBody().getPosition().y), 0);
        getBody().applyLinearImpulse(new Vector2(-getSpeed() / 1.65f, 0), getBody().getWorldCenter(), true);
    }
	protected void moveRight() {
//		getBody().setTransform(new Vector2(getBody().getPosition().x + (getSpeed() * 2), getBody().getPosition().y), 0);
        getBody().applyLinearImpulse(new Vector2(getSpeed() / 1.65f, 0), getBody().getWorldCenter(), true);
    }
	
	private void readFile() {
		try {
			File myObj = new File("words/words.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				words.add(myReader.nextLine());
			}
			myReader.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("An error occurred.");
    		e.printStackTrace();
	    }
	}
}
