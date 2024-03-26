package GameEngine.Entity;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import GameLayer.orthographicCameraControllerSingleton;
import GameLayer.worldSingleton;

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
    
    private World world = worldSingleton.getInstance();
	private static OrthographicCameraController orthographicCameraController = orthographicCameraControllerSingleton.getInstance();

    public Map(String mapPath, float mapscaleInput) {
        super(mapPath);
        this.map = new TmxMapLoader().load(mapPath);
        this.maprenderer = new OrthogonalTiledMapRenderer(map, mapscaleInput / 100f);
    }
    
    // Create physics static bodies by iterating over all map objects
    public void mapObjects() {
    	bodyDef = new BodyDef();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();

        for (RectangleMapObject rectangleObject : this.map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleObject.getRectangle();

            bodyDef.type = BodyType.StaticBody;
            bodyDef.position.set((rect.x + rect.width / 2) / 100f, (rect.y + rect.height / 2) / 100f);

            body = world.createBody(bodyDef);

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

    public void render() {
        maprenderer.setView(orthographicCameraController.getCamera());
        maprenderer.render();
    }

    public TiledMap getMap() {
        return map;
    }
    
    public int getMapWidth() {
    	return getMapTileWidth() * getTileSize();
    }
    public int getMapHeight() {
    	return getMapTileHeight() * getTileSize();
    }
    
    // Dispose
    public void dispose() {
        map.dispose();
        maprenderer.dispose();
    }
    public void destroy() {
		getTexture().dispose();
	}
}

