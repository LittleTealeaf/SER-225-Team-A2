package Screens;

import Engine.GamePanel;
import Game.GameState;
import Game.ScreenCoordinator;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.Direction;
import Menu.MenuOption;

public class LevelSelectScreen extends Menu {

    private final ScreenCoordinator coordinator;

    public LevelSelectScreen() {
        final int x_left_column = 150, x_right_column = 350;
        coordinator = GamePanel.getScreenCoordinator();
        MenuOption[][] menu = new MenuOption[][]{
                {
                        new MenuOption("Tutorial", x_left_column, 100, () -> coordinator.loadLevel(0))
                }, {
                        new MenuOption("Level One", x_left_column, 150, () -> coordinator.loadLevel(1)),
                        new MenuOption("Level Five", x_right_column, 150, () -> coordinator.loadLevel(5))
                }, {
                        new MenuOption("Level Two", x_left_column, 200, () -> coordinator.loadLevel(2)),
                        new MenuOption("Level Six", x_right_column, 200, () -> coordinator.loadLevel(6))
                }, {
                        new MenuOption("Level Three", x_left_column, 250, () -> coordinator.loadLevel(3)),
                        new MenuOption("Level Seven", x_right_column, 250, () -> coordinator.loadLevel(7))
                }, {
                        new MenuOption("Level Four", x_left_column, 300, () -> coordinator.loadLevel(4)),
                        new MenuOption("Boss Battle", x_right_column, 300, () -> coordinator.loadLevel(8))
                }, {
                        new MenuOption(
                                "Back to Main Menu", x_left_column, 375, this::backToMainMenu)
                }
        };
        menu[4][1].setNeighborItem(menu[5][0], Direction.DOWN);
        menu[1][1].setNeighborItem(menu[0][0],Direction.UP);
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(menu);
    }
}
