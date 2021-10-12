package Screens;

import Maps.TestMap;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuItem;

public class LevelSelectScreen extends Menu {

    public LevelSelectScreen() {
        MenuItem[][] menu = new MenuItem[][] {
                {
                    new MenuItem("Tutorial",200,100)
                },
        {
                    new MenuItem("Level One",200,150)
        },{
                    new MenuItem("Level Two",200,200)
        }, {
                    new MenuItem("Level Three",200,250)
        }, {
                    new MenuItem("Level Four",200,300)
        }, {
                    new MenuItem("Level Five",200,350)
        }};
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(menu);
    }

    private void playLevel(int levelNumber) {

    }
}
