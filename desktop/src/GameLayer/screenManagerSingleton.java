package GameLayer;

import GameEngine.Entity.Screen.ScreenManager;

public class screenManagerSingleton {
	private static ScreenManager screenManager;
	
	public static ScreenManager getInstance(){
        if (screenManager == null) {
        	screenManager = new ScreenManager();
        }
        return screenManager;
    }
}
