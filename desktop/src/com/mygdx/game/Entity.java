package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity implements iMoveable, iCollide{
	
	// Box2d Variables
	protected BodyDef bodyDef;
	protected Body body;
	protected PolygonShape shape;
	protected FixtureDef fixtureDef;
	protected Fixture fixture;
	private Texture texture;
	protected Sprite sprite;
	
	// Variables to draw entity
	protected float height;
	protected float width;
	protected float posX;
	protected float posY;
	protected Boolean aiCheck;
	
	protected String image;

	protected abstract void destroy();
	
	// Default Constructor
	public Entity() {
		setPosX(0);
		setPosY(0);
	}
	
	// Parameterized Constructor
	public Entity(World world, String textureImage, float posXInput, float posYInput, Boolean aiCheck) {
		setImage(textureImage);
		setTexture(textureImage);
		setPosX(posXInput);
		setPosY(posYInput);
		setAICheck(aiCheck);
		setBody(world);
		setSprite(getTexture());
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
	public void setBody(World world) {
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
        System.out.println(strippedFilename);
        fixture.setUserData(strippedFilename);
        shape.dispose();
	}
	
	// Drawing Sprite of Entity
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Texture texture) {
		sprite = new Sprite(texture);
		sprite.setPosition(getBody().getPosition().x - sprite.getWidth() / 2, getBody().getPosition().y - sprite.getHeight() / 2);
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
	
	public void draw(SpriteBatch batch) {
		
	}	
	public void moveAIControlled(){
		
	}
	public void moveUserControlled() {
		
	}
	public void checkCollide() {
		
	}
	public void movementUpdate() {
		
	}

}
