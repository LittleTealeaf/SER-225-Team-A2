package Screens;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Screen;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.*;
import Players.Cat;
import Utils.Stopwatch;

public class PlayLevelScreen extends Screen implements PlayerListener {

    private static final MapFactory[] MAPS;
    private static Map loadedMap;
    private static Player player;
    private Stopwatch screenTimer = new Stopwatch();



    static {
        /**
         * List of maps in the game, each map is given a constructor
         */
        MAPS = new MapFactory[]{
                () -> new TestMap(),
                () -> new TestMap2(),
                () -> new TestMap3(),
                () -> new TestMap4(),
                () -> new TestMap5()
        };
    }

    public PlayLevelScreen() {
        this(0);
    }

    public PlayLevelScreen(int initialMap) {
        super();

        loadMap(initialMap);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {

    }

    @Override
    public void onLevelCompleted() {

    }

    @Override
    public void onDeath() {

    }

    private void loadMap(int index) {
        //Load map using the MapFactory
        loadedMap = MAPS[index].generateMap();

        //Load the cat using the Config setting
        player = new Cat(Config.avatar.getFileName(),loadedMap.getPlayerStartPosition());
    }

    private interface MapFactory {
        Map generateMap();
    }

}
