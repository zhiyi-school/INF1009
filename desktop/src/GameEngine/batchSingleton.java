package GameEngine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class batchSingleton {
	private static SpriteBatch batch;
	
	public static SpriteBatch getInstance(){
        if (batch == null) {
            batch = new SpriteBatch();
        }
        return batch;
    }
}
