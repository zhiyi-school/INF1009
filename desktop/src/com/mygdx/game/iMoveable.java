package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public interface iMoveable {
	public void moveAIControlled(float delta);
	public void moveUserControlled(Sound soundEffect);

}