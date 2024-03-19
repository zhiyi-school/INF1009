package Entity;

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
	    	fixtureB.setUserData("equip");
		        
	    }else if("Weapon".equals(fixtureA.getUserData()) && "PlayableCharacter".equals(fixtureB.getUserData())) {
	    	fixtureA.setUserData("equip");
		}
	    
	    // Check for PC and Enemy collision
	    if ("PlayableCharacter".equals(fixtureA.getUserData()) && "Enemy".equals(fixtureB.getUserData())) {
	       	fixtureA.setUserData("PlayableCharacter_fight");
	       	fixtureB.setUserData("Enemy_fight");
	        
	   }else if("Enemy".equals(fixtureA.getUserData()) && "PlayableCharacter".equals(fixtureB.getUserData())) {
		   	fixtureA.setUserData("Enemy_fight");
		   	fixtureB.setUserData("PlayableCharacter_fight");
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
	public PlayableCharacter die(PlayableCharacter pc, NonPlayableCharacter npc) {		
		if(pc.getLives() == 1 && ((String) pc.getFix().getUserData()).contains("fight") && ((String) npc.getFix().getUserData()).contains("fight")) {
		    npc.getFix().setUserData("Enemy");
			pc.getFix().setUserData("PlayableCharacter");
			pc.setLives(pc.getLives() - 1);
			return pc;
		}else if(((String) pc.getFix().getUserData()).contains("fight") && ((String) npc.getFix().getUserData()).contains("fight")){
			npc.getFix().setUserData("Enemy");
			pc.getFix().setUserData("PlayableCharacter");
			pc.setLives(pc.getLives() - 1);
			pc.setDefaultPos();
			return null;
		}
	    return null;
	}
	
	// PlayableCharacter kills NPC
	public NonPlayableCharacter kill(PlayableCharacter pc, NonPlayableCharacter npc) {
		if(((String) pc.getFix().getUserData()).contains("fight") && ((String) npc.getFix().getUserData()).contains("fight")) {
			pc.getFix().setUserData("PlayableCharacter");
			pc.setAttackCheck(false);
			npc.getFix().setUserData("Enemy");
			return npc;
		}
		return null;
	}
	
	// Item equipped, remove from screen
	public boolean equip(NonPlayableCharacter item, World world) {
		if(((String) item.getFix().getUserData()).contains("equip")) {
			item.getFix().setUserData("Weapon");
			item.dispose(world);
		    item.destroy();
			return true;
		}
		return false;
	}
}