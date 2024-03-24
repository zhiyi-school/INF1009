package GameLayer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class worldSingleton {
	private static World world;
	
	public static World getInstance(){
        if (world == null) {
            world = new World(new Vector2(0, -9.8f), true);
        }
        return world;
    }
}
