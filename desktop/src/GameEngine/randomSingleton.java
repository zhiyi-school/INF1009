package GameEngine;

import java.util.Random;

public class randomSingleton {
	private static Random rand;
	
	public static Random getInstance(){
        if (rand == null) {
            rand = new Random();
        }
        return rand;
    }
}
