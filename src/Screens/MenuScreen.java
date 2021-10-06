package Screens;

import Engine.GamePanel;
import Game.GameState;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuItem;

public class MenuScreen extends Menu {
    public MenuScreen() {
        super();
        setMenuItemsAsGrid(new MenuItem[][] {
                {
                        new MenuItem("PLAY GAME", 80, 100, () -> GamePanel.getScreenCoordinator().setGameState(GameState.LEVEL)),
                        new MenuItem("LEVEL SELECT", 350, 100, () -> GamePanel.getScreenCoordinator().setGameState(GameState.LEVELSELECT))
                },{
                        new MenuItem("CREDITS", 80, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.CREDITS)),
                        new MenuItem("NARRATIVE", 350, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.OPENING))
                },{
                        new MenuItem("INSTRUCTIONS", 80, 300, () -> GamePanel.getScreenCoordinator().setGameState(GameState.INSTRUCTIONS)),
                        new MenuItem("OPTIONS", 350, 300, () -> GamePanel.getScreenCoordinator().setGameState(GameState.OPTIONS))
        }});
        setBackground(new TitleScreenMap());
    }
}
