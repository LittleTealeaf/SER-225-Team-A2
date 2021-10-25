package Screens;

import Engine.*;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.awt.event.MouseEvent;

// This is the class for the level lose screen
public class LevelLoseScreenOld extends Screen {
	protected SpriteFont loseMessage;
	protected SpriteFont instructions;
	protected KeyLocker keyLocker = new KeyLocker();
	protected PlayLevelScreen playLevelScreen;


	public LevelLoseScreenOld(PlayLevelScreen screen) {
		this.playLevelScreen = screen;
	}

	
	@Override
	public void initialize() {
		loseMessage = new SpriteFont("You lose!", 350, 270, "Comic Sans", 30, Color.white);
		instructions = new SpriteFont("Press Space to try again or Escape to go back to the main menu", 120, 300,
				"Comic Sans", 20, Color.white);
		keyLocker.lockKey(Key.SPACE);
		keyLocker.lockKey(Key.ESC);
	}

	@Override
	public void update() {
		if (Keyboard.isKeyUp(Key.SPACE)) {
			keyLocker.unlockKey(Key.SPACE);
		}
		if (Keyboard.isKeyUp(Key.ESC)) {
			keyLocker.unlockKey(Key.ESC);
		}

		// if space is pressed, reset level. if escape is pressed, go back to main menu
		if (Keyboard.isKeyDown(Key.SPACE)) {
			playLevelScreen.resetLevel();
			
		} else if (Keyboard.isKeyDown(Key.ESC)) {
			playLevelScreen.backToMenu();
		}
	}

	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawFilledRectangle(0, 0, Config.WIDTH, Config.HEIGHT,Color.black);
		loseMessage.draw(graphicsHandler);
		instructions.draw(graphicsHandler);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

}
