package Maps;

import Level.Map;

/**
 * Contains a centralized list of all game maps. Includes the name of the map, the function to create the map.
 */
public class GameMaps {
    public static MapFactory[] MAPS= new MapFactory[]{
            TestTutorial::new, TestMap::new, TestMap2::new, TestMap3::new, TestMap4::new, TestMap5::new, TestMap6::new, TestMap7::new, BossBattle::new
    };

    public interface MapFactory {
        Map generateMap();
    }
}
