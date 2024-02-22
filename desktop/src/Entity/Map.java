package Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Rectangle;

public class Map extends Entity {

	private BodyDef bodyDef;
	private Body body;
	private PolygonShape shape;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	
	private TiledMap map;
    private OrthogonalTiledMapRenderer maprenderer;
    private OrthographicCameraController orthographicCameraController;

    public Map(float x, float y, String mapPath, float mapscaleInput, OrthographicCameraController orthographicCameraController) {
        super(x, y, mapPath);
        this.map = new TmxMapLoader().load(mapPath);
        this.maprenderer = new OrthogonalTiledMapRenderer(map, mapscaleInput / 100f);
        this.orthographicCameraController = orthographicCameraController;
    }
    
    // Create physics static bodies by iterating over all map objects
    public void mapObjects(World b2dworld) {
    	bodyDef = new BodyDef();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();

        for (RectangleMapObject rectangleObject : this.map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleObject.getRectangle();

            bodyDef.type = BodyType.StaticBody;
            bodyDef.position.set((rect.x + rect.width / 2) / 100f, (rect.y + rect.height / 2) / 100f);

            body = b2dworld.createBody(bodyDef);

            shape.setAsBox(rect.width / 200f, rect.height / 200f);
            fixtureDef.shape = shape;
            fixture = body.createFixture(fixtureDef);
            int filename = getImage().lastIndexOf('.');
            String strippedFilename = getImage().substring(0, filename);
            fixture.setUserData(strippedFilename);
        }
        shape.dispose();
    }
    
    public Body getBody() {
		return body;
	}
	public Fixture getFix() {
		return fixture;
	}
    
    // Setup map dimensions
    public int getMapTileWidth() {
    	return map.getProperties().get("width", Integer.class);
    }
    
    public int getMapTileHeight() {
    	return map.getProperties().get("height", Integer.class);
    }
    
    public int getTileSize() {
    	return map.getProperties().get("tilewidth", Integer.class);
    }

    // Abstract classes
    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch batch) {
        maprenderer.setView(orthographicCameraController.getCamera());
        maprenderer.render();
    }

    public TiledMap getMap() {
        return map;
    }

    public void dispose(World world) {
        map.dispose();
        maprenderer.dispose();
    }
    public void destroy() {
		getTexture().dispose();
	}
    public void despawn(World world) {
		getTexture().dispose();
		getBody().destroyFixture(getFix());
		world.destroyBody(getBody());
	}
}

