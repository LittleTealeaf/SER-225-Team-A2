package Game;

import Engine.GameWindow;

import Engine.ScreenManager;

/*
 * The game starts here
 * This class just starts up a GameWindow and attaches the ScreenCoordinator to the ScreenManager instance in the GameWindow
 * From this point on the ScreenCoordinator class will dictate what the game does
 */
public class Game {

    public static ThreadManager thread;



    public static void main(String[] args) {
        new Game();
    }

    public Game() {
    	ScreenCoordinator c1 = new ScreenCoordinator();
        GameWindow gameWindow = new GameWindow(c1);
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
        screenManager.setCurrentScreen(c1);
//        DEBUG USE ONLY
//        screenManager.setCurrentScreen(new DebugScreen());
    }


}
