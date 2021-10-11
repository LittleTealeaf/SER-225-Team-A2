package Screens;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Screen;
import Level.Map;
import Level.PlayerListener;
import Maps.*;
import Players.Cat;

public class PlayLevelScreen extends Screen implements PlayerListener {

    public static MapFactory[] maps;
    private static Map loadedMap;
    private static Cat cat;


    static {
        /**
         * List of maps in the game, each map is given a constructor
         */
        maps = new MapFactory[]{
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
        loadedMap = maps[index].generateMap();
        cat = new Cat(Config.avatar.getFileName(),loadedMap.getPlayerStartPosition().x,loadedMap.getPlayerStartPosition().y);
    }

    private interface MapFactory {
        Map generateMap();
    }

}
