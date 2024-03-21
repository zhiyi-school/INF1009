package GameEngine.Entity;

import com.badlogic.gdx.audio.Sound;

public interface iMoveable {
	public void moveAIControlled(float delta, float mapFullWidth);
	public void moveUserControlled(Sound soundEffect, float mapFullWidth);

}
