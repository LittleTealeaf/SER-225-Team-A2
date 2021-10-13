package Screens;

import Engine.GamePanel;
import Game.ScreenCoordinator;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuItem;

public class LevelSelectScreen extends Menu {

    private ScreenCoordinator coordinator;

    public LevelSelectScreen() {
        coordinator = GamePanel.getScreenCoordinator();
        MenuItem[][] menu = new MenuItem[][] {
                {
                    new MenuItem("Tutorial",200,100, () -> coordinator.loadLevel(0))
                },
        {
                    new MenuItem("Level One",200,150, () -> coordinator.loadLevel(1))
        },{
                    new MenuItem("Level Two",200,200, () -> coordinator.loadLevel(2))
        }, {
                    new MenuItem("Level Three",200,250, () -> coordinator.loadLevel(3))
        }, {
                    new MenuItem("Level Four",200,300, () -> coordinator.loadLevel(4))
        }, {
                    new MenuItem("Level Five",200,350, () -> coordinator.loadLevel(5))
        }};
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(menu);
    }
}
