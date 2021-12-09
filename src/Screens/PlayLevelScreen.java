package Screens;

import Engine.*;
import Game.TimeTracker;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.GameMaps;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Probably the most important screen of all. This is the screen that handles rendering and updating the game, along with the sub-screens of the
 * game, such as the instructions menu and the pause menu. This also contains the time tracker and handles the loading of individual maps
 *
 *
 * @author Thomas Kwashnak
 */
/*
    Some implementation notes: While this works, some smart re-factoring could allow for a much simpler spot. Specifically, making all states into a
    separate screen that specifically handles the updates and drawing, such that updates here only need to send an update to the current "game
    screen" rather than checking the state (just an idea)
 */
public class PlayLevelScreen extends Screen implements PlayerListener, Pausable {

    private static final Stopwatch screenTimer;
    private static final KeyLocker keyLocker;
    private static final SpriteFont[] SPRITE_FONT_INSTRUCTIONS;
    private static final Color COLOR_GREY_BACKGROUND;
    private static Map loadedMap;
    private static Screen alternateScreen;
    private static Player player;
    private static TimeTracker timeTracker;

    static {
        screenTimer = new Stopwatch();
        keyLocker = new KeyLocker();

        /*
        I am sorry for this mess, all it basically does is make an array of all the sprite fonts to render when showing instructions. Definitely a
        better way to do this, maybe through the use of a screen.
         */
        SPRITE_FONT_INSTRUCTIONS = new SpriteFont[]{
                new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20, Color.white), new SpriteFont(
                "To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20, Color.white), new SpriteFont(
                "To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 200, "Times New Roman", 20, Color.white), new SpriteFont(
                "To CROUCH: DOWN arrow key, or 'S'", 130, 230, "Times New Roman", 20, Color.white), new SpriteFont(
                "To SPRINT: Hold SHIFT while moving", 130, 260, "Times New Roman", 20, Color.white), new SpriteFont("To ATTACK: press 'E'", 130, 300,
                                                                                                                    "Times New Roman", 20, Color.white
        ), new SpriteFont(
                "To SPRINT: hold 'SHIFT' while moving", 130, 340, "Times New Roman", 20, Color.white), new SpriteFont(
                "Press X to return", 20, 560, "Times New Roman", 20, Color.white)
        };
        /*
        General settings for each of the sprite fonts (setting the color and outline thickness)
         */
        for (SpriteFont font : SPRITE_FONT_INSTRUCTIONS) {
            font.setOutlineColor(Color.white);
            font.setOutlineThickness(2.0f);
        }

        COLOR_GREY_BACKGROUND = new Color(0, 0, 0, 100);
    }

    private State screenState = State.RUNNING;
    private int currentMap;

    /**
     * Creates a new PlayLevelScreen that starts at the first level
     */
    public PlayLevelScreen() {
        this(0);
    }

    /**
     * Creates a new PlayLevelScreen that starts at the indicated level
     *
     * @param initialMap Index of the map to load into
     */
    public PlayLevelScreen(int initialMap) {
        super();
        this.currentMap = initialMap;
        keyLocker.clear();
    }

    public static Map getLoadedMap() {
        return loadedMap;
    }

    @Override
    public void initialize() {
        timeTracker = new TimeTracker();
        loadMap(currentMap);
    }

    /**
     * Updates based on the current state
     */
    @Override
    public void update() {
        /*
        Switches between each of the states
         */
        switch (screenState) {
            /*
            If the game is in "running" state, then checks if the pause button or the instructions button is pressed. If either of them are,
            then set that to the current state. If neither are pressed, then updates the player and further update the loaded map with that player
            */
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
             /*
                Checks if the instructions button is pressed down again, which it will then return back to the running screen state
                 */
            case INSTRUCTIONS -> {

                if (KeyboardAction.GAME_INSTRUCTIONS.isDown() && keyLocker.isActionUnlocked(KeyboardAction.GAME_INSTRUCTIONS)) {
                    screenState = State.RUNNING;
                }
            }
            /*
            If the Pause, Losing, or Completed screen are showing, then update the alternate screen (if it is not null).
            For people looking to optimize this better with recent changes, move the "if not alternateScreen instance of PauseScreen" code from the
             draw method to here for each one
            */
            case PAUSE, LEVEL_LOSE_MESSAGE, GAME_COMPLETED -> {

                if (alternateScreen != null) {
                    alternateScreen.update();
                }
            }
            /*
            If the level is completed, then show the levelCleared screen, and set the wait time before the next level is loaded.
             */
            case LEVEL_COMPLETED -> {

                alternateScreen = new LevelClearedScreen();
                alternateScreen.initialize();
                screenTimer.setWaitTime(750);
                screenState = State.LEVEL_WIN_MESSAGE;
            }
            /*
            If the level win message is showing, wait until the screen timer is up before calling the nextLevel method
            */
            case LEVEL_WIN_MESSAGE -> {

                alternateScreen.update();
                if (screenTimer.isTimeUp()) {
                    screenState = State.RUNNING;
                    nextLevel();
                }
            }
        }
        //Updates keylocker
        keyLocker.updateActions(KeyboardAction.GAME_INSTRUCTIONS, KeyboardAction.GAME_PAUSE);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        /*
        Renders the screen based on what the state is
         */
        switch (screenState) {
            /*
            If the game is either running or completed, then render the map, player, and time tracker
            */
            case RUNNING, LEVEL_COMPLETED -> {

                alternateScreen = null;
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            /*
            If the level win message is shown, ensures that the alternateScreen is an instance of the LevelCleared screen, and then render that and
             the time tracker
            */
            case LEVEL_WIN_MESSAGE -> {

                if (!(alternateScreen instanceof LevelClearedScreen)) {
                    alternateScreen = new LevelClearedScreen();
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            /*
            Ensures that the alternate screen is an instance of the LevelLoseScreen, and then renders the alternate screen and the time tracker
             */
            case LEVEL_LOSE_MESSAGE -> {
                if (!(alternateScreen instanceof LevelLoseScreen)) {
                    alternateScreen = new LevelLoseScreen(this);
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            /*
            Ensures that the alternate screen is an instance of the pause screen, and then renders the alternate screen and the time tracker
             */
            case PAUSE -> {
                if (!(alternateScreen instanceof PauseScreen)) {
                    alternateScreen = new PauseScreen(loadedMap, player, this);
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
                timeTracker.draw(graphicsHandler);
            }
            /*
            Renders loaded map, player, the instructions, and a semi-transparent box that shades out the background
             */
            case INSTRUCTIONS -> {
                loadedMap.draw(graphicsHandler);
                player.draw(graphicsHandler);
                graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), COLOR_GREY_BACKGROUND);
                for (SpriteFont sprite : SPRITE_FONT_INSTRUCTIONS) {
                    sprite.draw(graphicsHandler);
                }
            }
            /*
            Ensures that the alternate screen is an instance of the GameScoreScreen, and then renders the alternate screen
             */
            case GAME_COMPLETED -> {
                if (!(alternateScreen instanceof GameScoreScreen)) {
                    alternateScreen = new GameScoreScreen(timeTracker);
                    alternateScreen.initialize();
                }
                alternateScreen.draw(graphicsHandler);
            }
        }
    }

    /**
     * If the alternate screen is not null, then delegates the mouse press to the alternate screen
     *
     * @param e Mouse Event representing the mouse click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (alternateScreen != null) {
            alternateScreen.mouseClicked(e);
        }
    }

    /**
     * Loads the next level map
     */
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
            screenState = State.GAME_COMPLETED;
        }
    }

    /**
     * Sets the State to Level Completed to display the "Level Completed" screen
     */
    @Override
    public void onLevelCompleted() {
        screenState = State.LEVEL_COMPLETED;
    }

    /**
     * Sets the state to Level Lose to display the "Level Lose" screen
     */
    @Override
    public void onDeath() {
        screenState = State.LEVEL_LOSE_MESSAGE;
    }

    /**
     * When a level is finished (meaning, when the player hits the level end box), if the current level is the last level, then stop the stopwatch
     * timer
     */
    @Override
    public void onLevelFinished() {
        //Only complete on final level
        if (currentMap == GameMaps.MAPS.length - 1) {
            timeTracker.stop();
        }
    }

    /**
     * Resets the current level without instantiating a new one. We do this so that checkpoints keep their states, and the player respawns at the
     * last checkpoint for that map if they reached it
     */
    public void resetLevel() {
        loadedMap.reset();
        player = Config.playerAvatar.generatePlayer(loadedMap.getPlayerStartPosition());
        player.setMap(loadedMap);
        player.addListener(this);
        screenState = State.RUNNING;
    }

    /**
     * Resumes back to the playing state (from instructions or the pause menu only) and restarts the time tracker
     */
    @Override
    public void resume() {
        if (screenState == State.PAUSE || screenState == State.INSTRUCTIONS) {
            screenState = State.RUNNING;
            timeTracker.start();
        }
    }

    /**
     * Pauses the game, setting the state to "Pause", and stops the time tracker
     */
    @Override
    public void pause() {
        screenState = State.PAUSE;
        timeTracker.stop();
    }

    /**
     * Goes back to the very start of the game, and resets the time tracker
     */
    public void resetHardcore() {
        timeTracker = new TimeTracker();
        loadMap(0);
    }

    /**
     * The current state of the game
     */
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
