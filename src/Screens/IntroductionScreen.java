package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

public class IntroductionScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont introLabel;
    protected SpriteFont startGameLabel;

    public IntroductionScreen(ScreenCoordinator screenCoordinator) {this.screenCoordinator = screenCoordinator;}

    @Override
    public void initialize() {
        /*
        todo: fix intro story
         */
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        introLabel = new SpriteFont("Fluffy grew up in the house his whole life. \n\n\nHis master never let him outside because he said \n\n\nthere was monsters. Outside the window, Fluffy could see \n\n\nthat the world outside was beautify land of wonder. \n\n\nOne day his master forgot to close the door and \n\n\nFluffy decided to go outside. Before he knew it, \n\n\nhe was lost and struggling to find his way home...", 60, 700, "Times New Roman", 20, Color.white);
        startGameLabel = new SpriteFont("Press Space to start the game", 20, 560, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }


        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.LEVEL);
        }
        introLabel.moveUp(0.5f);
        if (introLabel.getY() < -380) {
            screenCoordinator.setGameState(GameState.LEVEL);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        introLabel.drawWithParsedNewLines(graphicsHandler);
        startGameLabel.draw(graphicsHandler);

    }
}
