package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Screens.*;

import java.awt.event.MouseEvent;

/**
 * Displays the screen based on the current Game State
 *
 * @author Thomas Kwashnak
 */
public class ScreenCoordinator extends Screen {

    protected Screen currentScreen = new DefaultScreen();

    // keep track of gameState so ScreenCoordinator knows which Screen to show
    protected GameState gameState, previousGameState;

    private int initialMap = 0;

    public GameState getGameState() {
        return gameState;
    }

    // Other Screens can set the gameState of this class to force it to change the currentScreen
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void initialize() {
        // start game off with Menu Screen
        gameState = GameState.MENU;
    }

    @Override
    public void update() {

        do {
            // if previousGameState does not equal gameState, it means there was a change in gameState
            // this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
            if (previousGameState != gameState) {
                //This will return an error if there are unimplemented game states
                currentScreen = switch (gameState) {
                    case MENU -> new MenuScreen();
                    case LEVEL -> new PlayLevelScreen(initialMap);
                    case CREDITS -> new CreditsScreen();
                    case INSTRUCTIONS -> new InstructionsScreen();
                    case LEVELSELECT -> new LevelSelectScreen();
                    case OPENING -> new OpeningScreen(this);
                    case DIFFICULTYSELECT -> new DifficultySelectScreen();
                    case OPTIONS -> new OptionsScreen();
                };
                //Resets initial map to 0 (in the instance it was used from level select)
                initialMap = 0;
                //Clears the screen if the current screen has changed
                Keyboard.clear();
                currentScreen.initialize();
            }
            previousGameState = gameState;

            // call the update method for the currentScreen
            currentScreen.update();
        } while (previousGameState != gameState);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // call the draw method for the currentScreen
        currentScreen.draw(graphicsHandler);
    }

    /**
     * Delegates the mouse clicked event to the current screen
     *
     * @param e Mouse Event to Delegate
     */
    public void mouseClicked(MouseEvent e) {
        currentScreen.mouseClicked(e);
    }

    public void loadLevel(int level) {
        this.initialMap = level;
        this.gameState = GameState.LEVEL;
    }
}
