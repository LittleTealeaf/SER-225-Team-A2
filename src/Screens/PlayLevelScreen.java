package Screens;

import Engine.*;
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
    private KeyLocker keyLocker = new KeyLocker();
    private State screenState;
    private int currentMap = 0;




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
        this.currentMap = initialMap;
    }

    @Override
    public void initialize() {
        loadMap(currentMap);
    }

    @Override
    public void update() {
        switch(screenState) {
            case RUNNING -> {

            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        switch(screenState) {
            case RUNNING, LEVEL_COMPLETED, PLAYER_DEAD -> {
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
            }

        }
    }

    @Override
    public void onLevelCompleted() {

    }

    @Override
    public void onDeath() {

    }

    public void nextLevel() {
        currentMap++;
        initialize();
    }

    /**
     * Loads the map into the loadedMap variable, and the player into the player variable
     * @param index
     */
    private void loadMap(int index) {
        //Load map using the MapFactory
        loadedMap = MAPS[index].generateMap();

        //Load the cat using the Config setting
        player = Config.playerAvatar.generatePlayer(loadedMap.getPlayerStartPosition());
    }

    private interface MapFactory {
        Map generateMap();
    }

    public enum State {
        RUNNING, LEVEL_COMPLETED, PLAYER_DEAD, LEVEL_WIN_MESSAGE, LEVEL_LOSE_MESSAGE, LEVEL_SELECT, PAUSE,
        INSTRUCTIONS, OPTIONS
    }

}
