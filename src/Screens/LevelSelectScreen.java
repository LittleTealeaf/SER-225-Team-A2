package Screens;

import Engine.GamePanel;
import Game.GameState;
import Game.ScreenCoordinator;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuOption;

public class LevelSelectScreen extends Menu {

    private final ScreenCoordinator coordinator;

    public LevelSelectScreen() {
        coordinator = GamePanel.getScreenCoordinator();
        MenuOption[][] menu = new MenuOption[][]{
                {
                        new MenuOption("Tutorial", 200, 100, () -> coordinator.loadLevel(0))
                }, {
                        new MenuOption("Level One", 200, 150, () -> coordinator.loadLevel(1))
                }, {
                        new MenuOption("Level Two", 200, 200, () -> coordinator.loadLevel(2))
                }, {
                        new MenuOption("Level Three", 200, 250, () -> coordinator.loadLevel(3))
                }, {
                        new MenuOption("Level Four", 200, 300, () -> coordinator.loadLevel(4))
                }, {
                        new MenuOption("Level Five", 200, 350, () -> coordinator.loadLevel(5))
                }, {
                		new MenuOption("Level Six", 200, 400, () -> coordinator.loadLevel(6))
                }, {
                    	new MenuOption("Level Seven", 200, 450, () -> coordinator.loadLevel(7))
                }, {
                		new MenuOption("Boss Battle", 400, 100, () -> coordinator.loadLevel(8))
                }, {
                        new MenuOption(
                                "Hit [Escape] to go back to main menu", 100, 550, () -> GamePanel.getScreenCoordinator().setGameState(GameState.MENU))
                }
        };
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(menu);
    }
}
