package Game;

import Engine.GameWindow;
import Engine.ScreenManager;

/**
 * The game starts here
 * This class just starts up a GameWindow and attaches the ScreenCoordinator to the ScreenManager instance in the GameWindow
 * From this point on the ScreenCoordinator class will dictate what the game does
 *
 * <br>
 * Please note that this java program runs on Java 17, and as such will not compile or execute on any lower java version. Please make sure that
 * Java 17 is installed, as well as make sure Java 17 is set as the interpreter and compiler for IntelliJ, Eclipse, or other IDEs that you use.
 */
public class Game {

    public Game() {
    }

    public static void main(String[] args) {
        //Loads all maps before anything else happens
//        new GameMaps();

        ScreenCoordinator c1 = new ScreenCoordinator();
        GameWindow gameWindow = new GameWindow(c1);
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
        screenManager.setCurrentScreen(c1);
    }
}
