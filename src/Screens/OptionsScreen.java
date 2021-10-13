package Screens;

import Engine.Config;
import Engine.GamePanel;
import Engine.GraphicsHandler;
import Game.GameState;
import Maps.LevelSelectMap;
import Menu.Menu;
import Menu.MenuOption;
import Players.Avatar;
import Menu.Direction;

import java.awt.*;

public class OptionsScreen extends Menu {

    public OptionsScreen() {
        setBackground(new LevelSelectMap());
        MenuOption[][] items = new MenuOption[][]{
                {
                        new MenuOption("Volume Control:", 100, 150),
                        new MenuOption("Low", 400, 150, () -> GamePanel.setVolumeLow()),
                        new MenuOption("Medium", 500, 150, () -> GamePanel.setVolumeMed()),
                        new MenuOption("High", 630, 150, () -> GamePanel.setVolumeHigh())
                },{
                new MenuOption("Player",100,300),
                new MenuOption("Orange",350,300, () -> Config.playerAvatar = Avatar.CAT_ORANGE),
                new MenuOption("Blue",500,300, () -> Config.playerAvatar = Avatar.CAT_BLUE),
                new MenuOption("Green",630,300, () -> Config.playerAvatar = Avatar.CAT_GREEN)
        },{
                new MenuOption("Hit [Escape] to go back to main menu",100,450, () -> GamePanel.getScreenCoordinator().setGameState(GameState.MENU))
                }
        };
        setMenuItemsAsGrid(items);

        //Sets the smaller fonts
        Font smallerFont = new Font("Comic Sans",Font.PLAIN,24);
        for(int i = 0; i < 2; i++) {
            for(int j = 1; j < items[i].length; j++) {
                items[i][j].setFont(smallerFont);
                if(i == 1) {
                    items[i][j].setNeighborItem(items[2][0], Direction.DOWN);
                }
            }
        }
    }

    public void draw(GraphicsHandler handler) {
        super.draw(handler);
//        handler.drawImage(new BufferedImage());
    }
}
