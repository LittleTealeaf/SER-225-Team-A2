package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;


// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected SpriteFont instructions;
    protected SpriteFont levelSelect;
    protected SpriteFont extraInfo;
    protected SpriteFont opening;
    protected Map background;
    protected SpriteFont options;
    protected Stopwatch keyTimer = new Stopwatch();
    
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        playGame = new SpriteFont("PLAY GAME", 80, 100, "Comic Sans", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        credits = new SpriteFont("CREDITS", 80, 200, "Comic Sans", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        instructions = new SpriteFont("INSTRUCTIONS", 80, 300, "Comic Sans", 30, new Color(49,207,240));
        instructions.setOutlineColor(Color.black);
        instructions.setOutlineThickness(3);
        levelSelect = new SpriteFont("LEVEL SELECT", 350, 100, "Comic Sans", 30, new Color(49,207,240));
        levelSelect.setOutlineColor(Color.black);
        levelSelect.setOutlineThickness(3);
        opening = new SpriteFont("NARRATIVE", 350, 200, "Comic Sans", 30, new Color(49,207,240));
        opening.setOutlineColor(Color.black);
        opening.setOutlineThickness(3);
        options = new SpriteFont("OPTIONS", 350, 300, "Comic Sans", 30, new Color(49,207,240));
        options.setOutlineColor(Color.black);
        options.setOutlineThickness(3);

        
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyTimer.setWaitTime(200);
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        // update background map (to play tile animations)
    	background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered--;
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 5) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 5;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(255, 215, 0));
            credits.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(49,207,240));
            levelSelect.setColor(new Color(49,207,240));
            opening.setColor(new Color(49,207,240));
            options.setColor(new Color(49,207,240));
            pointerLocationX = 50;
            pointerLocationY = 80;
        } else if (currentMenuItemHovered == 1) {
        	playGame.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(49,207,240));
            credits.setColor(new Color(255, 215, 0));
            levelSelect.setColor(new Color(49,207,240));
            opening.setColor(new Color(49,207,240));
            options.setColor(new Color(49,207,240));
            pointerLocationX = 50;
            pointerLocationY = 180;
        } else if (currentMenuItemHovered == 2) {
        	playGame.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(255,215,0));
            credits.setColor(new Color(49, 207, 240));
            levelSelect.setColor(new Color(49,207,240));
            opening.setColor(new Color(49,207,240));
            options.setColor(new Color(49,207,240));
            pointerLocationX = 50;
            pointerLocationY = 280;
            
        }else if (currentMenuItemHovered == 3) {
        	playGame.setColor(new Color(49, 207, 240));
            levelSelect.setColor(new Color(255,215,0));
            credits.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(49,207,240));
            opening.setColor(new Color(49,207,240));
            options.setColor(new Color(49,207,240));
            pointerLocationX = 320;
            pointerLocationY = 80;
        }
        else if (currentMenuItemHovered == 4) {
        	playGame.setColor(new Color(49, 207, 240));
            opening.setColor(new Color(255,215,0));
            credits.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(49,207,240));
            levelSelect.setColor(new Color(49,207,240));
            options.setColor(new Color(49,207,240));
            pointerLocationX = 320;
            pointerLocationY = 180;
        } else if (currentMenuItemHovered == 5) {
        	playGame.setColor(new Color(49, 207, 240));
            options.setColor(new Color(255,215,0));
            credits.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(49,207,240));
            levelSelect.setColor(new Color(49,207,240));
            opening.setColor(new Color(49,207,240));
            pointerLocationX = 320;
            pointerLocationY = 280;
        }
        
        
        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
                
            }else if (menuItemSelected == 2){
            	screenCoordinator.setGameState(GameState.INSTRUCTIONS);
            }
            else if (menuItemSelected == 3) {
            	screenCoordinator.setGameState(GameState.LEVELSELECT);
            }else if (menuItemSelected == 4) {
            	screenCoordinator.setGameState(GameState.OPENING);
            }
            else if (menuItemSelected ==5) {
            	screenCoordinator.setGameState(GameState.OPTIONS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
    	 background.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        levelSelect.draw(graphicsHandler);
        opening.draw(graphicsHandler);
        options.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }

    public int getMenuItemSelected() {
        return menuItemSelected;
    }
}
