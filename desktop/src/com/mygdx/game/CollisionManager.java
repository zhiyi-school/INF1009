package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
			   	fixtureA.setUserData("equip");
		}
	    
	    // Check for PC and Enemy collision
	    if ("PlayableCharacter".equals(fixtureA.getUserData()) && "Enemy".equals(fixtureB.getUserData())) {
	       	// Debug output to confirm collision detection
	       	System.out.println("Collision detected between PlayableCharacter and Enemy");
	       	fixtureA.setUserData("fight");
	       	fixtureB.setUserData("fight");
	        
	   }else if("Enemy".equals(fixtureA.getUserData()) && "PlayableCharacter".equals(fixtureB.getUserData())) {
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
	
	public boolean handleCollision(PlayableCharacter entity, World world) {
	    // Remove Playable Character
		if("".equals(entity.getFix().getUserData())) {
			entity.despawn(world);
		    entity.destroy();
			return true;
		}
		return false;
	}
	
	public boolean die(PlayableCharacter entity, World world) {
	    entity.despawn(world);
	    entity.destroy();
		return true;
	}
	public boolean kill(NonPlayableCharacter entity, World world) {
	    entity.despawn(world);
	    entity.destroy();
		return true;
	}
	public boolean equip(NonPlayableCharacter entity, World world) {
	    // Remove the NPC or Item
		if("equip".equals(entity.getFix().getUserData())) {
		    entity.despawn(world);
		    entity.destroy();
			return true;
		}
		return false;
	}
	
	
	
	public boolean equip1(PlayableCharacter entity, NonPlayableCharacter item, World world) {
		Body body = entity.getBody();

        // Remove the original fixtures
        world.destroyBody(entity.getBody());
        world.destroyBody(item.getBody());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(entity.getTexture().getWidth() + item.getTexture().getWidth() / 2.5f, entity.getTexture().getHeight() / 2.5f);
        
        // Create a new fixture that represents the combination of the two original fixtures
        // This is a simplified example, you'll need to create a shape that represents the combination
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.shape.setRadius(1f); // Set the radius of the new fixture

        // Add the new fixture to one of the bodies
        body.createFixture(fixtureDef);

        fixtureDef.shape.dispose();	
        return true;
	}
}
