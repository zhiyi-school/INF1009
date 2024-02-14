package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity implements iMoveable, iCollide{
	protected Color color;
	protected Texture texture;
	protected Sprite sprite;
	protected Body body;
	protected float height;
	protected float width;
	protected float posX;
	protected float posY;
	protected Boolean aiCheck;
	World world = new World(new Vector2(0, -10), true);
	
	// Default Constructor
	public Entity() {
		setColor(Color.RED);
		setPosX(0);
		setPosY(0);
		
	}
	
	// Parameterized Constructor
	public Entity(Color colorInput, float posXInput, float posYInput, String texture, Boolean aiCheck) {
		setColor(colorInput);
		setPosX(posXInput);
		setPosY(posYInput);
		setTexture(texture);
		setAICheck(aiCheck);
		setBody(world);
		setSprite(getTexture());
		
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color colorInput) {
		color = colorInput;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(String textureInput) {
		texture = new Texture(Gdx.files.internal(textureInput));
	}
	
	
	public Body getBody() {
		return body;
	}
	public void setBody(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(getPosX(), getPosY());
		body = world.createBody(bodyDef);
		
	}
	
	public void setFix() {
		// Create a FixtureDef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f; // Make the object bounce a little bit
		
		// Create a PolygonShape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10, 10); // Set the shape as a box with a width and height of 10

		// Attach the shape to the FixtureDef
		fixtureDef.shape = shape;

		// Attach the FixtureDef to the Body
		body.createFixture(fixtureDef);

		// Dispose of the shape when you're done with it
		shape.dispose();
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Texture texture) {
		sprite = new Sprite(texture);
		sprite.setPosition(getBody().getPosition().x - sprite.getWidth() / 2, getBody().getPosition().y - sprite.getHeight() / 2);
	}
	
	// There might be inbuild getHeight and getWidth functions
//	protected float getHeight() {
//		return height;
//	}
//	void setHeight(float heightInput) {
//		height = heightInput;
//	}
//	
//	protected float getWidth() {
//		return width;
//	}
//	void setWidth(float widthInput) {
//		width = widthInput;
//	}
	
	public float getPosX() {
		return posX;
	}
	public void setPosX(float x) {
		posX = x;
	}
	
	public float getPosY() {
		return posY;
	}
	public void setPosY(float y) {
		posY = y;
	}
	protected boolean getAICheck() {
		return aiCheck;
	}
	protected void setAICheck(boolean aiInput) {
		aiCheck = aiInput;
	}
	
	public void draw(SpriteBatch batch) {
		
	}
	
	public void moveAIControlled() {
		
	}
	public void moveUserControlled() {
		
	}
	
	public void checkCollide() {
		
	}
	

}
