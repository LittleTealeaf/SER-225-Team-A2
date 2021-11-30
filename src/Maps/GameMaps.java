package Maps;

import Level.Map;

/**
 * Contains a centralized list of all game maps. Includes the name of the map, the function to create the map.
 */
public class GameMaps {
    public static final MapManager[] MAPS;

    static {
        /*
        List of all maps registered in the game, in order
         */
        MapFactory[] MAP_FACTORIES = new MapFactory[]{
                TestTutorial::new,
                TestMap::new,
                TestMap2::new,
                TestMap3::new,
                TestMap4::new,
                TestMap5::new,
                TestMap6::new,
                TestMap7::new,
                BossBattle::new
        };

        MAPS = new MapManager[MAP_FACTORIES.length];
        for(int i = 0; i < MAPS.length; i++) {
            MAPS[i] = new MapManager(MAP_FACTORIES[i]);
        }
    }

    /**
     * Sub-class that pulls out
     */
    public static class MapManager implements MapFactory {

        private MapFactory mapFactory;
        private String name;

        public MapManager(MapFactory mapFactory) {
            this.mapFactory = mapFactory;

            Map map = mapFactory.generateMap();
            name = map.getName();
        }

        public String getName() {
            return name;
        }

        public Map generateMap() {
            return mapFactory.generateMap();
        }
    }

    public interface MapFactory {
        Map generateMap();
    }
}
