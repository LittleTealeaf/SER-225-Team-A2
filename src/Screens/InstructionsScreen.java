package Screens;

import Engine.Drawable;
import Maps.TitleScreenMap;
import Menu.Menu;
import SpriteFont.SpriteFont;

import java.awt.*;

public class InstructionsScreen extends Menu {

    public InstructionsScreen() {
        super();
        setBackground(new TitleScreenMap());

        setDrawables(new Drawable[] {
                new SpriteFont("Credits", 15, 35, "Times New Roman", 30, Color.white),
                new SpriteFont("Created by Alex Thimineur for Quinnipiac's SER225 Course.", 130, 140, "Times New Roman", 20, Color.white),
                new SpriteFont("Thank you to QU Alumni Brian Carducci, Joseph White,\nand Alex Hutman for their contributions.", 60, 220, "Times New Roman",20, Color.white),
                new SpriteFont("Press Space to return to the menu", 20, 560, "Times New Roman", 30, Color.white)

        });
    }
}
