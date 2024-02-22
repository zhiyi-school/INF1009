package Entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity{
	
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
	private float ground;
	float scaleFactor = 100f;
	
	protected String image;

	protected abstract void dispose(World world);
	
	// Default Constructor
	public Entity(World world) {
		setImage("");
		setTexture("");
		setPosX(0);
		setPosY(0);
		setAICheck(true);
		createBody(world, 0, 0);
		setGround(0);
	}
	
	// Character Constructor
	public Entity(World world, String textureImage, float posXInput, float posYInput, Boolean aiCheck) {
		setImage(textureImage);
		setTexture(textureImage);
		setAICheck(aiCheck);
		setPosX(posXInput);
		setPosY(posYInput);
		createBody(world, posXInput, posYInput);
		setGround(0);
	}
	
	// Map Constructor
	public Entity(float posXInput, float posYInput, String mapPath) {
		setPosX(posXInput);
		setPosY(posYInput);
		setImage(mapPath);
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
	public void createBody(World world, float posX, float posY) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(posX / 100f, posY / 100f);
		bodyDef.fixedRotation = true; 
		body = world.createBody(bodyDef);
		
		shape = new PolygonShape();
        shape.setAsBox(getTexture().getWidth() / 4f / 100f, getTexture().getHeight() / 4f / 100f);
//        shape.setAsBox(getTexture().getWidth() / 2.5f * 4f, getTexture().getHeight() / 2.5f * 4f);
        
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; 		// Set density to affect how body responds to forces
        fixtureDef.friction = 2f; 	// Set friction to affect sliding

        fixture = body.createFixture(fixtureDef);
        // Set the user data for the fixture to the character instance
        int filename = getImage().lastIndexOf('.');
        String strippedFilename = getImage().substring(0, filename);
        fixture.setUserData(strippedFilename);
        shape.dispose();
	}
	
	// Get x Co-ordinate of Entity
	public float getPosX() {
		return getBody().getPosition().x;
	}
	public void setPosX(float x) {
//		posX = getBody().getPosition().x * scaleFactor;
		posX = x;
	}
	
	// Get y Co-ordinate of Entity
	public float getPosY() {
		return getBody().getPosition().y;
	}
	public void setPosY(float y) {
//		posY = getBody().getPosition().y * scaleFactor;
		posY = y;
	}
	
	// Check if Entity is user controlled
	protected boolean getAICheck() {
		return aiCheck;
	}
	protected void setAICheck(boolean aiInput) {
		aiCheck = aiInput;
	}
	
	protected void setGround(float groundInput) {
		ground = groundInput;
	}
	protected float getGround() {
		return ground;
	}
	public void draw(SpriteBatch batch, boolean Item) {
		
	}

	// Map and Orthographic Camera
	public void update(float deltaTime) {
		
	}
	public void render() {
		
	}

}
