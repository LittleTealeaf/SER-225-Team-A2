package Engine;

import Players.Avatar;
import Utils.Colors;

import java.awt.*;

/**
 * This class holds some constants like window width/height and resource folder locations
 * Tweak these as needed, they shouldn't break anything (keyword shouldn't).
 */
public class Config {

    /**
     * Path for the Resources folder
     */
    public static final String RESOURCES_PATH = "resources/";
    /**
     * Path for the Map Files folder
     */
    public static final String MAP_FILES_PATH = RESOURCES_PATH + "MapFiles/";
    /**
     * Width of the window
     */
    public static final int WIDTH = 800;
    /**
     * Height of the window
     */
    public static final int HEIGHT = 605;
    /**
     * Transparent Color used in files (like a "green screen" for the image files)
     */
    public static final Color TRANSPARENT_COLOR = Colors.MAGENTA;
    /**
     * The player avatar, defaulting to Orange
     */
    public static Avatar playerAvatar = Avatar.CAT_ORANGE;

    /**
     * prevents Config from being instantiated -- it's my way of making a "static" class like C# has
     */
    private Config() {

    }
}
