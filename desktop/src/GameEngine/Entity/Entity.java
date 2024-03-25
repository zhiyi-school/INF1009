package GameEngine.Entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import GameLayer.worldSingleton;

public abstract class Entity implements Cloneable{
	
	private BodyDef bodyDef;
	private Body body;
	private PolygonShape shape;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	private Texture texture;
	private Boolean aiCheck;
	private String image;
	private String defaultName;
	
    private World world = worldSingleton.getInstance();

	protected abstract void dispose();
	
	// Default Constructor
	public Entity() {
		setImage("");
		setTexture("");
		setAICheck(true);
		createBody(0, 0);
		setDefaultUserData((String) getFix().getUserData());
	}
	
	// Character Constructor
	public Entity(String textureImage, float posXInput, float posYInput, Boolean aiCheck) {
		setImage(textureImage);
		setTexture(textureImage);
		setAICheck(aiCheck);
		createBody(posXInput, posYInput);
		setDefaultUserData((String) getFix().getUserData());
	}
	
	// Map Constructor
	public Entity(String mapPath) {
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
	public void createBody(float posX, float posY) {
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
        // Set the user data for thdwe fixture to the character instance
        int filename = getImage().lastIndexOf('.');
        String strippedFilename = getImage().substring(0, filename);
        fixture.setUserData(strippedFilename);
        shape.dispose();
	}
	
	// Check if Entity is user controlled
	public boolean getAICheck() {
		return aiCheck;
	}
	public void setAICheck(boolean aiInput) {
		aiCheck = aiInput;
	}
	public String getDefaultUserData() {
		return defaultName;
	}
	public void setDefaultUserData(String defaultNameInput) {
		defaultName = defaultNameInput;
	}

	// Map and Orthographic Camera
	public void render() {
		
	}
	
	public void setPosition(float x, float y) {
	    if (this.body != null) {
	        this.body.setTransform(x, y, this.body.getAngle());
	    }
	}

	// Clone prototype allows child classes to be cloned
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
