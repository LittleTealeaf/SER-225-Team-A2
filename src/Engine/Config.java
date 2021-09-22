package Engine;

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
    public static int WIDTH = 800;
    public static int HEIGHT = 605;
    public static int WIDTHS = 800;
    public static int HEIGHTS = 605;
    public static int WIDTHM = 950;
    public static int HEIGHTM = 705;
    public static int WIDTHL = 1100;
    public static int HEIGHTL = 710;
    public static final Color TRANSPARENT_COLOR = Colors.MAGENTA;

    // prevents Config from being instantiated -- it's my way of making a "static" class like C# has
    private Config() {}
    
   
}
