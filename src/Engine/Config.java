package Engine;

import Players.Avatar;
import Utils.Colors;

import java.awt.*;

/*
 * This class holds some constants like window width/height and resource folder locations
 * Tweak these as needed, they shouldn't break anything (keyword shouldn't).
 */
public class Config {
    public static final int FPS = 100;
    public static final String RESOURCES_PATH = "Resources/";
    public static final String MAP_FILES_PATH = "MapFiles/";
    public static final int WIDTH= 800;
    public static final int HEIGHT = 605;
    public static final Color TRANSPARENT_COLOR = Colors.MAGENTA;
    public static Avatar playerAvatar = Avatar.CAT_ORANGE;

    // prevents Config from being instantiated -- it's my way of making a "static" class like C# has
    private Config() {
    	
    }
}
