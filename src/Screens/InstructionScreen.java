package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

public class InstructionScreen extends Screen {
	protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont instructionsLabel;
    protected SpriteFont instructionsLeft;
    protected SpriteFont instructionsRight;
    protected SpriteFont instructionsJump;
    protected SpriteFont instructionsDuck;
    protected SpriteFont instructionsAttack;
    protected SpriteFont instructionsPause;
    protected SpriteFont returnInstructionsLabel;

    public InstructionScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    //@Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        instructionsLabel = new SpriteFont("Instructions", 275, 105, "Comic Sans", 50, Color.white);
        instructionsLabel.setOutlineColor(Color.black);
        instructionsLabel.setOutlineThickness(3);
        instructionsLeft = new SpriteFont("Left Arrow to move left ", 105, 175, "Comic Sans", 30, Color.white);
        instructionsLeft.setOutlineColor(Color.black);
        instructionsLeft.setOutlineThickness(3);
        instructionsRight = new SpriteFont("Right Arrow to move right", 105, 225, "Comic Sans", 30, Color.white);
        instructionsRight.setOutlineColor(Color.black);
        instructionsRight.setOutlineThickness(3);
        instructionsJump = new SpriteFont("Up Arrow to jump", 105, 275, "Comic Sans", 30, Color.white);
        instructionsJump.setOutlineColor(Color.black);
        instructionsJump.setOutlineThickness(3);
        instructionsDuck = new SpriteFont("Down Arrow to duck", 105, 325, "Comic Sans", 30, Color.white);
        instructionsDuck.setOutlineColor(Color.black);
        instructionsDuck.setOutlineThickness(3);
        instructionsAttack = new SpriteFont("Press 1 To Attack (after unlocking)", 105, 375, "Comic Sans", 30, Color.white);
        instructionsAttack.setOutlineColor(Color.black);
        instructionsAttack.setOutlineThickness(3);
        instructionsPause = new SpriteFont("P to pause the game", 105, 425, "Comic Sans", 30, Color.white);
        instructionsPause.setOutlineColor(Color.black);
        instructionsPause.setOutlineThickness(3);
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 560, "Comic Sans", 30, Color.white);
        returnInstructionsLabel.setOutlineColor(Color.black);
        returnInstructionsLabel.setOutlineThickness(3);
        keyLocker.lockKey(Key.SPACE);
    }
    
    //@Override
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
    	background.draw(graphicsHandler);
    	graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
        instructionsLabel.draw(graphicsHandler);
        instructionsLeft.draw(graphicsHandler);
        instructionsRight.draw(graphicsHandler);
        instructionsJump.draw(graphicsHandler);
        instructionsDuck.draw(graphicsHandler);
        instructionsAttack.draw(graphicsHandler);
        instructionsPause.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
        
    }
}
