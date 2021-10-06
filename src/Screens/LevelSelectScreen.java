package Screens;

import Menu.Menu;
import Menu.MenuItem;

public class LevelSelectScreen extends Menu {

    public LevelSelectScreen() {
        MenuItem[][] menu = new MenuItem[][] {
        {
                    new MenuItem("Level One",200,100)
        },{
                    new MenuItem("Level Two",200,180)
        }, {
                    new MenuItem("Level Three",200,340)
        }, {
                    new MenuItem("Level Four",200,420)
        }, {
                    new MenuItem("Level Five",200,420)
        }};
        setMenuItemsAsGrid(menu);
    }

    private void playLevel(int levelNumber) {

    }
}
