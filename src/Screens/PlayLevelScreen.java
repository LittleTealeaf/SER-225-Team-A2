package Screens;

import Engine.*;
import Game.GameState;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.*;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;

public class PlayLevelScreen extends Screen implements PlayerListener {

    private static final MapFactory[] MAPS;
    private static Map loadedMap;
    private static Screen alternateScreen;
    private static Player player;
    private static final Stopwatch screenTimer;
    private static final KeyLocker keyLocker;
    private State screenState;
    private int currentMap = 0;
    private static final SpriteFont SPRITE_FONT_PAUSE;
    private static final SpriteFont[] SPRITE_FONT_INSTRUCTIONS;

    static {
        screenTimer = new Stopwatch();
        keyLocker = new KeyLocker();

        /**
         * List of maps in the game, each map is given a constructor
         * This is some new java funky stuff :D
         */
        MAPS = new MapFactory[]{
                TestMap::new,
                TestMap2::new,
                TestMap3::new,
                TestMap4::new,
                TestMap5::new
        };

        SPRITE_FONT_PAUSE = new SpriteFont("Pause", 350, 250, "Comic Sans", 30, Color.white);

        SPRITE_FONT_INSTRUCTIONS = new SpriteFont[] {
                new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20,
                        Color.white),
                new SpriteFont("To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20,
                        Color.white),
                new SpriteFont("To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 220, "Times New Roman", 20,
                        Color.white),
                new SpriteFont("To CROUCH: DOWN arrow key, or 'S'", 130, 260, "Times New Roman", 20,
                        Color.white),
                new SpriteFont("Press X to return", 20, 560, "Times New Roman", 20, Color.white)
        };
        for(SpriteFont font : SPRITE_FONT_INSTRUCTIONS) {
            font.setOutlineColor(Color.white);
            font.setOutlineThickness(2.0f);
        }
    }

    public PlayLevelScreen() {
        this(0);
    }

    public PlayLevelScreen(int initialMap) {
        super();
        this.currentMap = initialMap;
        keyLocker.clear();
    }

    @Override
    public void initialize() {
        loadMap(currentMap);
    }

    @Override
    public void update() {
        switch(screenState) {
            case RUNNING -> {
                if (KeyboardAdapter.GAME_PAUSE.isDown() && !keyLocker.isKeyLocked(KeyboardAdapter.GAME_PAUSE)) {
                    screenState = State.PAUSE;
                } else {
                    player.update();
                    loadedMap.update(player);
                }
                keyLocker.setKeys(KeyboardAdapter.GAME_PAUSE);
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
            case LEVEL_WIN_MESSAGE -> {
                if(!(alternateScreen instanceof LevelClearedScreen)) {
                    alternateScreen = new LevelClearedScreen();
                }
                alternateScreen.draw(graphicsHandler);
            }
            case LEVEL_LOSE_MESSAGE -> {
                if(!(alternateScreen instanceof LevelLoseScreen)) {
                    alternateScreen = new LevelLoseScreen(this);
                }
                alternateScreen.draw(graphicsHandler);
            }
            case PAUSE -> {
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
                SPRITE_FONT_PAUSE.draw(graphicsHandler);
                graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
                        Color.black);
            }
            case INSTRUCTIONS -> {
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
                for(SpriteFont sprite : SPRITE_FONT_INSTRUCTIONS) {
                    sprite.draw(graphicsHandler);
                }
                graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
                        Color.BLACK);
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

    public void resetLevel() {

    }

    public void backToMenu() {
        GamePanel.getScreenCoordinator().setGameState(GameState.MENU);
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
