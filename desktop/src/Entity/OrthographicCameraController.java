package Entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OrthographicCameraController {
    private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraMinX, cameraMaxX, cameraMinY, cameraMaxY;

    public OrthographicCameraController(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }
    
    // Method to update camera position with boundary checks
    public void updateCameraPosition(float targetX, float targetY) {
    	float cameraX = Math.max(cameraMinX, Math.min(cameraMaxX, targetX));
    	float cameraY = Math.max(cameraMinY, Math.min(cameraMaxY, targetY));
    	camera.position.set(cameraX, cameraY, 0);
    	camera.update();
    }
    
    // Constrain camera to map boundaries
    public void setCameraBoundaries(float mapFullWidth, float mapFullHeight) {
    	this.cameraMinX = viewport.getWorldWidth() / 2;
    	this.cameraMinY = viewport.getWorldHeight() / 2;
    	this.cameraMaxX = mapFullWidth - cameraMinX;
    	this.cameraMaxY = mapFullHeight - cameraMinY;
    }
    
    public void applyViewport() {
    	viewport.apply();
    }
    
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
