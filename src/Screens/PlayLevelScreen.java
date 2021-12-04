package Screens;

import Engine.*;
import Game.GameState;
import Game.TimeTracker;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.GameMaps;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PlayLevelScreen extends Screen implements PlayerListener, Pausable {

    private static final Stopwatch screenTimer;
    private static final KeyLocker keyLocker;
    private static final SpriteFont[] SPRITE_FONT_INSTRUCTIONS;
    private static final Color COLOR_GREY_BACKGROUND;
    public static Map loadedMap;
    private static Screen alternateScreen;
    private static Player player;
    private static TimeTracker timeTracker;

    static {
        screenTimer = new Stopwatch();
        keyLocker = new KeyLocker();

        SPRITE_FONT_INSTRUCTIONS = new SpriteFont[]{
                new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20, Color.white), new SpriteFont(
                "To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20, Color.white), new SpriteFont(
                "To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 200, "Times New Roman", 20, Color.white), new SpriteFont(
                "To CROUCH: DOWN arrow key, or 'S'", 130, 230, "Times New Roman", 20, Color.white), new SpriteFont(
                "To SPRINT: Hold SHIFT while moving", 130, 260, "Times New Roman", 20, Color.white), new SpriteFont("To ATTACK: press 'E'", 130, 300,
                                                                                                                    "Times New Roman", 20, Color.white
        ), new SpriteFont("To SPRINT: hold 'SHIFT' while moving", 130, 340, "Times New Roman", 20, Color.white), new SpriteFont(
                "Press X to return", 20, 560, "Times New Roman", 20, Color.white)
        };
        for (SpriteFont font : SPRITE_FONT_INSTRUCTIONS) {
            font.setOutlineColor(Color.white);
            font.setOutlineThickness(2.0f);
        }

        COLOR_GREY_BACKGROUND = new Color(0, 0, 0, 100);
    }

    private State screenState = State.RUNNING;
    private int currentMap;

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
        timeTracker = new TimeTracker();
        loadMap(currentMap);
    }

    @Override
    public void update() {
        switch (screenState) {
            case RUNNING -> {
                if (KeyboardAction.GAME_PAUSE.isDown() && keyLocker.isActionUnlocked(KeyboardAction.GAME_PAUSE)) {
                    pause();
                } else if (KeyboardAction.GAME_INSTRUCTIONS.isDown() && keyLocker.isActionUnlocked(KeyboardAction.GAME_INSTRUCTIONS)) {
                    screenState = State.INSTRUCTIONS;
                    timeTracker.stop();
                } else {
                    player.update();
                    loadedMap.update(player);
                }
            }
            case INSTRUCTIONS -> {
                if (KeyboardAction.GAME_INSTRUCTIONS.isDown() && keyLocker.isActionUnlocked(KeyboardAction.GAME_INSTRUCTIONS)) {
                    screenState = State.RUNNING;
                }
            }
            case PAUSE, LEVEL_LOSE_MESSAGE, GAME_COMPLETED -> {
                if (alternateScreen != null) {
                    alternateScreen.update();
                }
            }
            case PLAYER_DEAD -> screenState = State.LEVEL_LOSE_MESSAGE;
            case LEVEL_COMPLETED -> {
                alternateScreen = new LevelClearedScreen();
                alternateScreen.initialize();
                screenTimer.setWaitTime(750);
                screenState = State.LEVEL_WIN_MESSAGE;
            }
            case LEVEL_WIN_MESSAGE -> {
                alternateScreen.update();
                if (screenTimer.isTimeUp()) {
                    screenState = State.RUNNING;
                    nextLevel();
                }
            }
        }
        keyLocker.setAction(KeyboardAction.GAME_INSTRUCTIONS, KeyboardAction.GAME_PAUSE);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        switch (screenState) {
            case RUNNING, LEVEL_COMPLETED, PLAYER_DEAD -> {
                alternateScreen = null;
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            case LEVEL_WIN_MESSAGE -> {
                if (!(alternateScreen instanceof LevelClearedScreen)) {
                    alternateScreen = new LevelClearedScreen();
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            case LEVEL_LOSE_MESSAGE -> {
                if (!(alternateScreen instanceof LevelLoseScreen)) {
                    alternateScreen = new LevelLoseScreen(this);
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            case PAUSE -> {
                if (!(alternateScreen instanceof PauseScreen)) {
                    alternateScreen = new PauseScreen(loadedMap, player, this);
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            case INSTRUCTIONS -> {
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
                for (SpriteFont sprite : SPRITE_FONT_INSTRUCTIONS) {
                    sprite.draw(graphicsHandler);
                }
                graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), COLOR_GREY_BACKGROUND);
            }
            case GAME_COMPLETED -> {
                if (!(alternateScreen instanceof GameScoreScreen)) {
                    alternateScreen = new GameScoreScreen(timeTracker);
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (alternateScreen != null) {
            alternateScreen.mouseClicked(e);
        }
    }

    public void nextLevel() {
        loadMap(currentMap + 1);
    }

    /**
     * Loads the map into the loadedMap variable, and the player into the player variable
     *
     * @param index index of map to load
     */
    public void loadMap(int index) {
        if (index < GameMaps.MAPS.length) {
            currentMap = index;
            //Load map using the MapFactory
            loadedMap = GameMaps.MAPS[index].generateMap();
            loadedMap.reset();

            //Load the cat using the Config setting
            player = Config.playerAvatar.generatePlayer(loadedMap.getPlayerStartPosition());
            player.setMap(loadedMap);
            player.addListener(this);
            screenState = State.RUNNING;

            //Update Time
            timeTracker.setCurrentLevel(index);
        } else {
            //Add the other menu
            System.out.println("complete game");
            screenState = State.GAME_COMPLETED;
        }
    }

    public Map getLoadedMap() {
        return loadedMap;
    }

    @Override
    public void onLevelCompleted() {
        screenState = State.LEVEL_COMPLETED;
    }

    @Override
    public void onDeath() {
        screenState = State.PLAYER_DEAD;
    }

    @Override
    public void onLevelFinished() {
        //Only complete on final level
        if (currentMap == GameMaps.MAPS.length - 1) {
            timeTracker.stop();
        }
    }

    public void resetLevel() {
        loadedMap.reset();
        player = Config.playerAvatar.generatePlayer(loadedMap.getPlayerStartPosition());
        player.setMap(loadedMap);
        player.addListener(this);
        screenState = State.RUNNING;
    }

    public void backToMenu() {
        GamePanel.getScreenCoordinator().setGameState(GameState.MENU);
    }

    @Override
    public void resume() {
        if (screenState == State.PAUSE || screenState == State.INSTRUCTIONS) {
            screenState = State.RUNNING;
            timeTracker.start();
        }
    }

    @Override
    public void pause() {
        screenState = State.PAUSE;
        timeTracker.stop();
    }

    public void resetHardcore() {
        timeTracker = new TimeTracker();
        loadMap(0);
    }

    public enum State {
        RUNNING,
        LEVEL_COMPLETED,
        PLAYER_DEAD,
        LEVEL_WIN_MESSAGE,
        LEVEL_LOSE_MESSAGE,
        PAUSE,
        INSTRUCTIONS,
        GAME_COMPLETED
    }
}
