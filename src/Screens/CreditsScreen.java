package Screens;

import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuOption;
import SpriteFont.SpriteFont;

import java.awt.*;

public class CreditsScreen extends Menu {

    public CreditsScreen() {
        super();
        setBackground(new TitleScreenMap());
        setMenuItemsAsGrid(new MenuOption[][]{
                {
                        new MenuOption(
                                "Hit [Escape] to go back to main menu", 100, 450, this::backToMainMenu)
                }
        });
        setDrawables(
                new SpriteFont("Credits", 15, 35, "Times New Roman", 30, Color.black),
                new SpriteFont("Created by Alex Thimineur for Quinnipiac's SER225 Course.", 130, 140, "Times New Roman", 20, Color.black),
                new SpriteFont("Thank you to QU Alumni Brian Carducci, Joseph White,\nand Alex Hutman for their contributions.", 60, 220,
                               "Times New Roman", 20, Color.black
                ), new SpriteFont("Press Space to return to the menu", 20, 560, "Times New Roman", 30, Color.black)
                    );
    }
}
