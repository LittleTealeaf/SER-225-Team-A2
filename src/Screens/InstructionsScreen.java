package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the credits screen
public class InstructionsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont instructionsJump;
    protected SpriteFont instructions2Label;
    protected SpriteFont instructions3Label;
    protected SpriteFont instructions4label;
    protected SpriteFont returnInstructionsLabel;
    protected SpriteFont extraInfo;
    protected SpriteFont extraInfo2;
    
    public InstructionsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        instructionsJump = new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20, Color.white);
       instructions2Label = new SpriteFont("To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20, Color.white);
       instructions3Label = new SpriteFont("To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 220, "Times New Roman",20, Color.white);
       instructions4label = new SpriteFont("To CROUCH: DOWN arrow key, or 'S'", 130,260, "Times New Roman", 20, Color.white);
      
        returnInstructionsLabel = new SpriteFont("Press SPACE to return to the menu", 20, 560, "Times New Roman", 30, Color.white);
        
        extraInfo = new SpriteFont("PRESS P for PAUSE", 90, 400, "Times New Roman", 20,Color.white);
        extraInfo2 = new SpriteFont("PRESS X for INSTRUCTIONS", 90, 422, "Times New Roman", 20,Color.white);
        keyLocker.lockKey(Key.SPACE);
    }

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
        instructions2Label.draw(graphicsHandler);
        instructions3Label.draw(graphicsHandler);
        instructions4label.draw(graphicsHandler);
        instructionsJump.draw(graphicsHandler);
        
        extraInfo.draw(graphicsHandler);
        extraInfo2.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}
