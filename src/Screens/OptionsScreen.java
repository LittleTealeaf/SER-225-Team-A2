package Screens;

import Engine.GamePanel;
import Game.ScreenCoordinator;
import Maps.LevelSelectMap;
import Menu.Menu;
import Menu.MenuItem;

import java.awt.*;

public class OptionsScreen extends Menu {

    public OptionsScreen() {
        setBackground(new LevelSelectMap());
        MenuItem[][] items = new MenuItem[][]{
                {
                        new MenuItem("Volume Control:", 100, 150),
                        new MenuItem("Low", 400, 150, () -> GamePanel.setVolumeLow()),
                        new MenuItem("Medium", 500, 150, () -> GamePanel.setVolumeMed()),
                        new MenuItem("High", 630, 150, () -> GamePanel.setVolumeHigh())
                },{
                new MenuItem("Player",100,300),
                new MenuItem("Green",630,300),
                new MenuItem("Blue",500,300),
                new MenuItem("Orange",350,300)
        }
        };
        setMenuItemsAsGrid(items);

        //Sets the smaller fonts
        Font smallerFont = new Font("Comic Sans",Font.PLAIN,24);
        for(int i = 0; i < 1; i++) {
            for(int j = 1; j < items[i].length; j++) {
                items[i][j].setFont(smallerFont);
            }
        }
    }
}
