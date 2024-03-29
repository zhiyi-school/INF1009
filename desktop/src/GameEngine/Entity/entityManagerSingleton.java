package GameEngine.Entity;

import GameEngine.Entity.EntityManager;

public class entityManagerSingleton {
	private static EntityManager entityManager;
	
	public static EntityManager getInstance(){
        if (entityManager == null) {
        	entityManager = new EntityManager();
        }
        return entityManager;
    }
}
