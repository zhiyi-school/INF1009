package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;

public class CollisionManager implements ContactListener{
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
	    Fixture fixtureB = contact.getFixtureB();
	    
	    System.out.println(fixtureA.getUserData() instanceof PlayableCharacter);
	    
	    // Check if one fixture belongs to PlayableCharacter and the other to NPC
	    if ((fixtureA.getUserData() instanceof PlayableCharacter && fixtureB.getUserData() instanceof NonPlayableCharacter) ||
	        (fixtureA.getUserData() instanceof NonPlayableCharacter && fixtureB.getUserData() instanceof PlayableCharacter)) {
	        // Debug output to confirm collision detection
	        System.out.println("Collision detected between PlayableCharacter and NPC");
	        
	    }
	}

	@Override
	public void endContact(Contact contact) {
	    // Optional: Implement logic when two objects stop colliding
	}
	
	@Override
	public void preSolve(Contact contact, com.badlogic.gdx.physics.box2d.Manifold oldManifold) {
	    // Optional: Called before collision is resolved, allows you to control physics behavior
	}
	
	@Override
	public void postSolve(Contact contact, com.badlogic.gdx.physics.box2d.ContactImpulse impulse) {
	    // Optional: Called after collision is resolved, allows you to query the impulse of the collision
	}
	
	private void handleCollision(PlayableCharacter player, NonPlayableCharacter npc) {
	    // Remove the NPC
	    npc.destroy();
	}
}
