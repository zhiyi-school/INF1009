package GameEngine.Entity;

import com.badlogic.gdx.Gdx;
import GameEngine.Entity.OrthographicCameraController;

public class orthographicCameraControllerSingleton {
	private static OrthographicCameraController orthographicCameraController;
	
	public static OrthographicCameraController getInstance(){
        if (orthographicCameraController == null) {
        	orthographicCameraController = new OrthographicCameraController(Gdx.graphics.getWidth() / 100f, Gdx.graphics.getHeight() / 100f);
        }
        return orthographicCameraController;
    }
}
