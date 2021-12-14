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

/**
 * Gives the user the ability to modify settings regarding the game, including setting the volume and the player color. This class demonstrates an
 * example of extending the {@link Menu} class properly.
 *
 * @author Thomas Kwashnak
 */
public class OptionsScreen extends Menu {

    private BufferedImage cat;

    /**
     * Creates a new OptionsMenu, adding the display elements and functionality of options
     */
    public OptionsScreen() {
        //This sets up the background to be a new LevelSelectMap()
        setBackground(new LevelSelectMap());

        /*
        This declares the 2d array of menu options. In essence, it describes the grid format that the options will be organized by, so that the
         Menu class can handle most of the linking between MenuOptions (see the menu options class)
         */
        MenuOption[][] items = new MenuOption[][]{
                {
                        //Each of these curly brackets ("{}") describes a line, or elements that will be displayed next to each other
                        new MenuOption("Volume Control:", 75, 150),
                        /*
                        "GamePanel::setVolumeOff" is a new java syntax that references a specific method. In simple terms, "GamePanel::setVolumeOff"
                        creates an anonymous class based on the Runnable interface (which contains a run() method), and implements the run() method to
                        just call GamePanel.setVolumeOff(). Lambdas and manual anonymous classes can also be used here
                         */
                        new MenuOption("Off", 350, 150, GamePanel::setVolumeOff),
                        new MenuOption("Low", 450, 150, GamePanel::setVolumeLow),
                        new MenuOption("Medium", 550, 150, GamePanel::setVolumeMed),
                        new MenuOption("High", 680, 150, GamePanel::setVolumeHigh)
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

        //This call sets the menu items as a grid, and configures most of the links needed to create a menu
        setMenuItemsAsGrid(items);

        //this sets the fonts of all items, as well as overriding and properly configuring the last row to all link to the "Back to menu" option
        Font smallerFont = new Font("Comic Sans", Font.PLAIN, 24);
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < items[i].length; j++) {
                items[i][j].setFont(smallerFont);
                if (i == 1) {
                    items[i][j].setNeighborItem(items[2][0], Direction.DOWN);
                }
            }
        }
        //Initializes the first selected option
        items[1][1].setSelected(true);
    }

    /**
     * Custom drawing instructions to display the cat based on the chosen option
     *
     * @param handler Graphics Handler used for the current frame.
     */
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
