package GameEngine.Screen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class shapeSingleton {
	private static ShapeRenderer shape;
	
	public static ShapeRenderer getInstance(){
        if (shape == null) {
            shape = new ShapeRenderer();
        }
        return shape;
    }
}
