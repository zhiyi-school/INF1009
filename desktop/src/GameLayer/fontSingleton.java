package GameLayer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class fontSingleton {
	private static BitmapFont font;
	
	public static BitmapFont getInstance(){
        if (font == null) {
            font = new BitmapFont();
        }
        return font;
    }
}
