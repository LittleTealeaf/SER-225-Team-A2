package Screens;

import Engine.GamePanel;
import Game.GameState;
import Maps.TitleScreenMap;
import Menu.Direction;
import Menu.Menu;
import Menu.MenuOption;

public class MenuScreen extends Menu {

    public MenuScreen() {
        super();
        MenuOption[][] menu = new MenuOption[][]{
            {
                new MenuOption("PLAY GAME", 60, 100, () -> GamePanel.getScreenCoordinator().setGameState(GameState.LEVEL)),
                new MenuOption("LEVEL SELECT", 392, 100, () -> GamePanel.getScreenCoordinator().setGameState(GameState.LEVELSELECT))
        }, {
                new MenuOption("CREDITS", 60, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.CREDITS)),
                new MenuOption("NARRATIVE", 392, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.OPENING))
        }, {
                new MenuOption("INSTRUCTIONS", 60, 300, () -> GamePanel.getScreenCoordinator().setGameState(GameState.INSTRUCTIONS)),
                new MenuOption("OPTIONS", 392, 300, () -> GamePanel.getScreenCoordinator().setGameState(GameState.OPTIONS))
        },
        {
            new MenuOption("SELECT DIFFICULTY", 60, 400, () -> GamePanel.getScreenCoordinator().setGameState(GameState.DIFFICULTYSELECT)),
            new MenuOption("QUIT", 392, 400, () -> System.exit(0))
        }
        };
        setMenuItemsAsGrid(menu);
        setBackground(new TitleScreenMap());
    }
}
