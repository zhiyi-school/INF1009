package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity implements iMoveable{
	
	// Box2d Variables
	private BodyDef bodyDef;
	private Body body;
	private PolygonShape shape;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	private Texture texture;
	
	// Variables to draw entity
	private float posX;
	private float posY;
	private Boolean aiCheck;
	
	protected String image;

	protected abstract void despawn(World world);
	protected abstract void destroy();
	public abstract void draw(SpriteBatch batch);
	
	// Default Constructor
	public Entity(World world) {
		setImage("");
		setTexture("");
		setPosX(0);
		setPosY(0);
		setAICheck(true);
		createBody(world);
	}
	
	// Parameterized Constructor
	public Entity(World world, String textureImage, float posXInput, float posYInput, Boolean aiCheck) {
		setImage(textureImage);
		setTexture(textureImage);
		setPosX(posXInput);
		setPosY(posYInput);
		setAICheck(aiCheck);
		createBody(world);
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String imageInput) {
		image = imageInput;
	}
	
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(String textureImage) {
		texture = new Texture(Gdx.files.internal(textureImage));
	}
	
	// Creating Box2D for Entity
	public Body getBody() {
		return body;
	}
	public Fixture getFix() {
		return fixture;
	}
	public void createBody(World world) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(getPosX(), getPosY());
		body = world.createBody(bodyDef);
		
		shape = new PolygonShape();
        shape.setAsBox(getTexture().getWidth() / 2.5f, getTexture().getHeight() / 2.5f);
        
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; 		// Set density to affect how body responds to forces
        fixtureDef.friction = 0.4f; 	// Set friction to affect sliding
        fixtureDef.restitution = 0.1f; 	// Set restitution to affect how bouncy the object is

        fixture = body.createFixture(fixtureDef);
        // Set the user data for the fixture to the character instance
        int filename = getImage().lastIndexOf('.');
        String strippedFilename = getImage().substring(0, filename);
        fixture.setUserData(strippedFilename);
        shape.dispose();
	}
	
	// Get x Co-ordinate of Entity
	public float getPosX() {
		return posX;
	}
	public void setPosX(float x) {
		posX = x;
	}
	
	// Get y Co-ordinate of Entity
	public float getPosY() {
		return posY;
	}
	public void setPosY(float y) {
		posY = y;
	}
	
	// Check if Entity is user controlled
	protected boolean getAICheck() {
		return aiCheck;
	}
	protected void setAICheck(boolean aiInput) {
		aiCheck = aiInput;
	}
	
	public void moveAIControlled(){
		
	}
	public void moveUserControlled() {
		
	}

}
