package Screens;

import Engine.Drawable;
import Engine.GamePanel;
import Game.GameState;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuOption;
import SpriteFont.SpriteFont;

import java.awt.*;

public class InstructionsScreen extends Menu {

    public InstructionsScreen() {
        super();
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(new MenuOption[][]{
                {
                        new MenuOption("Hit [Escape] to go back to main menu", 100, 500, () -> GamePanel.getScreenCoordinator().setGameState(GameState.MENU))
                }
        });
        setDrawables(new Drawable[]{
                new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20, Color.white),
                new SpriteFont("To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20, Color.white),
                new SpriteFont("To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 220, "Times New Roman", 20, Color.white),
                new SpriteFont("To CROUCH: DOWN arrow key, or 'S'", 130, 260, "Times New Roman", 20, Color.white),
                new SpriteFont("PRESS P for PAUSE", 90, 400, "Times New Roman", 20, Color.white),
                new SpriteFont("PRESS X for INSTRUCTIONS", 90, 422, "Times New Roman", 20, Color.white)

        });
    }
}
