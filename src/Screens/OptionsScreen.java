package Screens;

import Engine.Config;
import Engine.GamePanel;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Game.GameState;
import GameObject.SpriteSheet;
import Maps.LevelSelectMap;
import Menu.Direction;
import Menu.Menu;
import Menu.MenuOption;
import Players.Avatar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OptionsScreen extends Menu {

    public int catColor = 0;
    private BufferedImage cat;

    public OptionsScreen() {
        setBackground(new LevelSelectMap());
        MenuOption[][] items = new MenuOption[][]{
                {

                        new MenuOption("Volume Control:", 75, 150), new MenuOption("Off", 350, 150, GamePanel::setVolumeOff), new MenuOption(
                        "Low", 450, 150, GamePanel::setVolumeLow), new MenuOption("Medium", 550, 150, GamePanel::setVolumeMed), new MenuOption(
                        "High", 680, 150, GamePanel::setVolumeHigh)
                }, {
                        new MenuOption("Player", 100, 300),
                        new MenuOption("Orange", 350, 300, () -> Config.playerAvatar = Avatar.CAT_ORANGE),
                        new MenuOption("Blue", 500, 300, () -> Config.playerAvatar = Avatar.CAT_BLUE),
                        new MenuOption("Green", 630, 300, () -> Config.playerAvatar = Avatar.CAT_GREEN)
                }, {
                        new MenuOption(
                                "Hit [Escape] to go back to main menu", 100, 450, () -> GamePanel.getScreenCoordinator().setGameState(GameState.MENU))
                }
        };
        setMenuItemsAsGrid(items);

        //Sets the smaller fonts
        Font smallerFont = new Font("Comic Sans", Font.PLAIN, 24);
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < items[i].length; j++) {
                items[i][j].setFont(smallerFont);
                if (i == 1) {
                    items[i][j].setNeighborItem(items[2][0], Direction.DOWN);
                }
            }
        }
        items[1][1].setSelected(true);
    }

    public void draw(GraphicsHandler handler) {
        super.draw(handler);
        if (Config.playerAvatar == Avatar.CAT_ORANGE) {
            SpriteSheet orange = new SpriteSheet(ImageLoader.load("Cat.png"), 24, 24);
            cat = orange.getSprite(0, 0);
            handler.drawImage(cat, 210, 225, 100, 100);
        } else if (Config.playerAvatar == Avatar.CAT_BLUE) {
            SpriteSheet blue = new SpriteSheet(ImageLoader.load("CatBlue.png"), 24, 24);
            cat = blue.getSprite(0, 0);
            handler.drawImage(cat, 210, 225, 100, 100);
        } else if (Config.playerAvatar == Avatar.CAT_GREEN) {
            SpriteSheet green = new SpriteSheet(ImageLoader.load("CatGreen.png"), 24, 24);
            cat = green.getSprite(0, 0);
            handler.drawImage(cat, 210, 225, 100, 100);
        } else {
            handler.drawImage(cat, 210, 225, 100, 100);
        }
    }
}
