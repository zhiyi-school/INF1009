package GameEngine.Entity;

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
	    
	    // Check for PC and Letter collision
	    if("PlayableCharacter".equals(fixtureA.getUserData()) && ((String) fixtureB.getUserData()).contains("letters_img")) {
	    	String userData = ((String) fixtureB.getUserData()).substring(12) + "_add";
	    	fixtureB.setUserData(userData);
	    }else if("PlayableCharacter".equals(fixtureB.getUserData()) && ((String) fixtureA.getUserData()).contains("letters_img")) {
	    	String userData = ((String) fixtureA.getUserData()).substring(12) + "_add";
	    	fixtureA.setUserData(userData);
	    }
	    
	    // Check for PC and Spike collision
	    if (("PlayableCharacter".equals(fixtureA.getUserData()) && "Spike".equals(fixtureB.getUserData()))) {
		   	fixtureB.setUserData("Spike_contact");
        }else if("Spike".equals(fixtureA.getUserData()) && "PlayableCharacter".equals(fixtureB.getUserData())) {
		   	fixtureA.setUserData("Spike_contact");
        }
	    
	    // Check for PC and Door collision
	    if("PlayableCharacter".equals(fixtureA.getUserData()) && ((String) fixtureB.getUserData()).contains("Opened")) {
	    	fixtureB.setUserData("endGame");
	    }else if("PlayableCharacter".equals(fixtureB.getUserData()) && ((String) fixtureA.getUserData()).contains("Opened")) {
	    	fixtureA.setUserData("endGame");
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
		if(pc.getLives() == 1 && (((String) pc.getFix().getUserData()).contains("fight") && ((String) npc.getFix().getUserData()).contains("fight") || ((String) npc.getFix().getUserData()).contains("contact"))) {
			npc.getFix().setUserData(npc.getDefaultUserData());
			pc.getFix().setUserData(pc.getDefaultUserData());
			pc.setLives(pc.getLives() - 1);
			return pc;
		}else if((((String) pc.getFix().getUserData()).contains("fight") && ((String) npc.getFix().getUserData()).contains("fight")) || ((String) npc.getFix().getUserData()).contains("contact")){
			npc.getFix().setUserData(npc.getDefaultUserData());
			pc.getFix().setUserData(pc.getDefaultUserData());
			pc.setLives(pc.getLives() - 1);
			pc.setDefaultPos();
			return null;
		}
	    return null;
	}
	
	// PlayableCharacter kills NPC
	public NonPlayableCharacter kill(PlayableCharacter pc, NonPlayableCharacter npc) {
		if(((String) pc.getFix().getUserData()).contains("fight") && ((String) npc.getFix().getUserData()).contains("fight")) {
			pc.getFix().setUserData(pc.getDefaultUserData());
			pc.setAttackCheck(false);
			npc.getFix().setUserData(npc.getDefaultUserData());
			return npc;
		}
		return null;
	}
	
	// Adding Letters to Score 
	public NonPlayableCharacter addScore(PlayableCharacter pc, NonPlayableCharacter chars) {
		if(((String) chars.getFix().getUserData()).contains("_add")) {
			if(((String) chars.getFix().getUserData()).substring(0, 1).equals(pc.getGuess().substring(pc.getScore().length(), pc.getScore().length() + 1))) {
				pc.setScore(((String) chars.getFix().getUserData()).substring(0, 1));
				chars.getFix().setUserData(chars.getDefaultUserData());
			}else if(pc.getLives() > 0) {
				pc.setLives(pc.getLives() - 1);
				chars.getFix().setUserData(chars.getDefaultUserData());
			}
			return chars;
		}
		return null;
	}
	
	// Item equipped, remove from screen
	public boolean equip(NonPlayableCharacter item, World world) {
		if(((String) item.getFix().getUserData()).contains("equip")) {
			item.getFix().setUserData(item.getDefaultUserData());
			item.dispose();
		    item.destroy();
			return true;
		}
		return false;
	}
}