package Maps;

import Level.Map;
import Tilesets.CommonTileset;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class LevelSelectMap extends Map {

    public LevelSelectMap() {
        super("level_select_map.txt", new CommonTileset(), new Point(1, 9));
    }

}
