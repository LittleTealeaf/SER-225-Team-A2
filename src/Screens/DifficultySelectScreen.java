package Screens;

import Engine.DifficultyHolder;
import Engine.GamePanel;
import Game.GameState;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.Direction;
import Menu.MenuOption;
import Menu.MenuOption.CloseOnExecute;

public class DifficultySelectScreen extends Menu {
	
	private final DifficultyHolder difficultyHolder;
	
	//these values also correspond to the health given at each difficulty
	private final static int NORMAL = 3;
	private final static int HARD = 2;
	private final static int HARDCORE = 1;

    public DifficultySelectScreen() {
    	difficultyHolder = GamePanel.getDifficultyHolder();
        MenuOption[][] menu = new MenuOption[][]{
                {
                		new MenuOption("Normal", 100, 150, () -> difficultyHolder.setDifficulty(NORMAL), CloseOnExecute.CLOSE),
                		new MenuOption("Hard", 320, 150, () -> difficultyHolder.setDifficulty(HARD), CloseOnExecute.CLOSE),
                		new MenuOption("Hardcore", 500, 150, () -> difficultyHolder.setDifficulty(HARDCORE), CloseOnExecute.CLOSE)
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
      menu[1][0].setNeighborItem(menu[0][1], Direction.UP);
    }
    
}
