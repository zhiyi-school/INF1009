package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class CollisionManager implements ContactListener{
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
	    Fixture fixtureB = contact.getFixtureB();
	    
	    // Check for PC and Item collision
	    if ("PlayableCharacter".equals(fixtureA.getUserData()) && "Weapon".equals(fixtureB.getUserData())) {
		       	// Debug output to confirm collision detection
		       	System.out.println("Collision detected between PlayableCharacter and Weapon");
		       	fixtureB.setUserData("equip");
		        
		   }else if("Weapon".equals(fixtureA.getUserData()) && "PlayableCharacter".equals(fixtureB.getUserData())) {
			   System.out.println("Collision detected between PlayableCharacter and Weapon");	
			   fixtureA.setUserData("equip");
		}
	    
	    // Check for PC and Enemy collision
	    if ("PlayableCharacter".equals(fixtureA.getUserData()) && "Enemy".equals(fixtureB.getUserData())) {
	       	// Debug output to confirm collision detection
	       	System.out.println("Collision detected between PlayableCharacter and Enemy");
	       	fixtureA.setUserData("fight");
	       	fixtureB.setUserData("fight");
	        
	   }else if("Enemy".equals(fixtureA.getUserData()) && "PlayableCharacter".equals(fixtureB.getUserData())) {
		   System.out.println("Collision detected between PlayableCharacter and Enemy");	
		   fixtureA.setUserData("fight");
		   fixtureB.setUserData("fight");
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
	
	
	// NPC kills PlayableCharacter
	public PlayableCharacter die(PlayableCharacter entity, NonPlayableCharacter npc, World world) {
		if("fight".equals(entity.getFix().getUserData())&& "fight".equals(npc.getFix().getUserData())) {
			entity.despawn(world);
		    entity.destroy();
		    npc.getFix().setUserData("Enemy");
			return entity;
		}
	    return null;
	}
	// PlayableCharacter kills NPC
	public NonPlayableCharacter kill(PlayableCharacter entity, NonPlayableCharacter npc, World world) {
		if("fight".equals(entity.getFix().getUserData())&& "fight".equals(npc.getFix().getUserData())) {
			npc.despawn(world);
		    npc.destroy();
		    entity.getFix().setUserData("PlayableCharacter");
		    entity.setAttackCheck(false);
			return npc;
		}
		return null;
	}
	// Item equipped, remove from screen
	public boolean equip(NonPlayableCharacter item, World world) {
		if("equip".equals(item.getFix().getUserData())) {
			item.despawn(world);
		    item.destroy();
			return true;
		}
		return false;
	}
}