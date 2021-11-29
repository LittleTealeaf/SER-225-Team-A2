package Screens;

import Engine.GamePanel;
import Game.GameState;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.Direction;
import Menu.MenuOption;

public class DifficultySelectScreen extends Menu {

    public DifficultySelectScreen() {
        MenuOption[][] menu = new MenuOption[][]{
                {
                		new MenuOption("Normal", 100, 150, null),
                		new MenuOption("Hard", 320, 150, null),
                		new MenuOption("Hardcore", 500, 150, null)
                },
                {
                        new MenuOption(
                                "Back to Main Menu", 225, 375, () -> GamePanel.getScreenCoordinator().setGameState(GameState.MENU))
                }
        };
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(menu);
      menu[0][0].setNeighborItem(menu[1][0], Direction.DOWN);
      menu[0][1].setNeighborItem(menu[1][0], Direction.DOWN);
      menu[0][2].setNeighborItem(menu[1][0], Direction.DOWN);
      menu[1][0].setNeighborItem(menu[0][1],Direction.UP);
    }
}
