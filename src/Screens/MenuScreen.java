package Screens;

import Engine.GamePanel;
import Game.GameState;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuOption;

public class MenuScreen extends Menu {

    public MenuScreen() {
        super();
        setMenuItemsAsGrid(new MenuOption[][]{
                {
                        new MenuOption("PLAY GAME", 80, 100, () -> GamePanel.getScreenCoordinator().setGameState(GameState.LEVEL)),
                        new MenuOption("LEVEL SELECT", 350, 100, () -> GamePanel.getScreenCoordinator().setGameState(GameState.LEVELSELECT))
                }, {
                        new MenuOption("CREDITS", 80, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.CREDITS)),
                        new MenuOption("NARRATIVE", 350, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.OPENING))
                }, {
                        new MenuOption("INSTRUCTIONS", 80, 300, () -> GamePanel.getScreenCoordinator().setGameState(GameState.INSTRUCTIONS)),
                        new MenuOption("OPTIONS", 350, 300, () -> GamePanel.getScreenCoordinator().setGameState(GameState.OPTIONS))
                }
        });
        setBackground(new TitleScreenMap());
    }
}
