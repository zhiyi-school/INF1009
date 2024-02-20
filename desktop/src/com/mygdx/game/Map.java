package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Rectangle;

public class Map extends Entity {
	private TiledMap map;
    private OrthogonalTiledMapRenderer maprenderer;
    private OrthographicCameraController orthographicCameraController;
    private float mapscale;

    public Map(float x, float y, String mapPath, float mapscale, OrthographicCameraController orthographicCameraController) {
        super(x, y);
        this.mapscale = mapscale;
        this.map = new TmxMapLoader().load(mapPath);
        this.maprenderer = new OrthogonalTiledMapRenderer(map, mapscale);
        this.orthographicCameraController = orthographicCameraController;
    }
    
    // Create physics static bodies by iterating over all map objects
    public void mapObjects(World b2dworld) {
    	BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for (RectangleMapObject rectangleObject : this.map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = rectangleObject.getRectangle();

            bodyDef.type = BodyType.StaticBody;
            bodyDef.position.set(rect.x + rect.width / 2, rect.y + rect.height / 2);

            body = b2dworld.createBody(bodyDef);

            shape.setAsBox(rect.width / 2, rect.height / 2);
            fixtureDef.shape = shape;
            Fixture fixture = body.createFixture(fixtureDef);
            
            fixture.setUserData("Ground");
        }

        shape.dispose();
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
}
