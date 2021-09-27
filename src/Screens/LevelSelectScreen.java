package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.LevelSelectMap;
import Maps.TitleScreenMap;
import Screens.PlayLevelScreen.PlayLevelScreenState;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;

// This is the class for the main menu screen
public class LevelSelectScreen extends Screen {
	protected ScreenCoordinator screenCoordinator;
	protected int currentLevelHovered = 0; // current menu item being "hovered" over
	protected int LevelSelected = -1;
	protected SpriteFont levelOne;
	protected SpriteFont levelTwo;
	protected SpriteFont levelThree;
	protected SpriteFont levelFour;
	protected SpriteFont levelFive;
	protected Map background;
	protected Stopwatch keyTimer = new Stopwatch();
	protected int pointerLocationX, pointerLocationY;
	protected KeyLocker keyLocker = new KeyLocker();
	protected PlayLevelScreen playLevelScreen;

	public LevelSelectScreen(PlayLevelScreen screen) {
		this.playLevelScreen = screen;

	}

	@Override
	public void initialize() {

		levelOne = new SpriteFont("Level One", 200, 100, "Comic Sans", 30, new Color(49, 207, 240));
		levelOne.setOutlineColor(Color.black);
		levelOne.setOutlineThickness(3);
		levelTwo = new SpriteFont("Level Two", 200, 180, "Comic Sans", 30, new Color(49, 207, 240));
		levelTwo.setOutlineColor(Color.black);
		levelTwo.setOutlineThickness(3);
		levelThree = new SpriteFont("Level Three", 200, 260, "Comic Sans", 30, new Color(49, 207, 240));
		levelThree.setOutlineColor(Color.black);
		levelThree.setOutlineThickness(3);
		levelFour = new SpriteFont("Level Four", 200, 340, "Comic Sans", 30, new Color(49, 207, 240));
		levelFour.setOutlineColor(Color.black);
		levelFour.setOutlineThickness(3);
		levelFive = new SpriteFont("Level Five", 200, 420, "Comic Sans", 30, new Color(49, 207, 240));
		levelFive.setOutlineColor(Color.black);
		levelFive.setOutlineThickness(3);

		background = new LevelSelectMap();
		background.setAdjustCamera(false);
		keyTimer.setWaitTime(200);

		keyLocker.lockKey(Key.SPACE);
	}

	public void update() {
		// update background map (to play tile animations)
		background.update(null);

		// if down or up is pressed, change menu item "hovered" over (blue square in
		// front of text will move along with currentMenuItemHovered changing)
		if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
			keyTimer.reset();
			currentLevelHovered++;
		} else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp()) {
			keyTimer.reset();
			currentLevelHovered--;
		}

		// if down is pressed on last menu item or up is pressed on first menu item,
		// "loop" the selection back around to the beginning/end
		if (currentLevelHovered > 4) {
			currentLevelHovered = 0;
		} else if (currentLevelHovered < 0) {
			currentLevelHovered = 4;
		}

		// sets location for blue square in front of text (pointerLocation) and also
		// sets color of spritefont text based on which menu item is being hovered
		if (currentLevelHovered == 0) {
			levelOne.setColor(new Color(255, 215, 0));
			levelTwo.setColor(new Color(49, 207, 240));
			levelThree.setColor(new Color(49, 207, 240));
			levelFour.setColor(new Color(49, 207, 240));
			levelFive.setColor(new Color(49, 207, 240));
			pointerLocationX = 170;
			pointerLocationY = 80;
		} else if (currentLevelHovered == 1) {
			levelOne.setColor(new Color(49, 207, 240));
			levelThree.setColor(new Color(49, 207, 240));
			levelTwo.setColor(new Color(255, 215, 0));
			levelFour.setColor(new Color(49, 207, 240));
			levelFive.setColor(new Color(49, 207, 240));
			pointerLocationX = 170;
			pointerLocationY = 160;
		} else if (currentLevelHovered == 2) {
			levelOne.setColor(new Color(49, 207, 240));
			levelThree.setColor(new Color(255, 215, 0));
			levelTwo.setColor(new Color(49, 207, 240));
			levelFour.setColor(new Color(49, 207, 240));
			levelFive.setColor(new Color(49, 207, 240));
			pointerLocationX = 170;
			pointerLocationY = 240;
		} else if (currentLevelHovered == 3) {
			levelOne.setColor(new Color(49, 207, 240));
			levelFour.setColor(new Color(255, 215, 0));
			levelThree.setColor(new Color(49, 207, 240));
			levelTwo.setColor(new Color(49, 207, 240));
			levelFive.setColor(new Color(49, 207, 240));
			pointerLocationX = 170;
			pointerLocationY = 320;
		} else if (currentLevelHovered == 4) {
			levelOne.setColor(new Color(49, 207, 240));
			levelFive.setColor(new Color(255, 215, 0));
			levelThree.setColor(new Color(49, 207, 240));
			levelFour.setColor(new Color(49, 207, 240));
			levelTwo.setColor(new Color(49, 207, 240));
			pointerLocationX = 170;
			pointerLocationY = 400;
		}
		LevelSelected = currentLevelHovered;
		// if space is pressed on menu item, change to appropriate screen based on which
		// menu item was chosen
		if (Keyboard.isKeyUp(Key.SPACE)) {
			keyLocker.unlockKey(Key.SPACE);
		}
		if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {

			// playLevelScreen.getPlayLevelScreenState();
			if (LevelSelected == 0) {
				playLevelScreen.setLevelNum(0);

				playLevelScreen.setPlayLevelScreenState(PlayLevelScreenState.RUNNING);
				playLevelScreen.initialize();
			} else if (LevelSelected == 1) {
				playLevelScreen.setLevelNum(1);
				playLevelScreen.setPlayLevelScreenState(PlayLevelScreenState.RUNNING);
				playLevelScreen.initialize();
			} else if (LevelSelected == 2) {
				playLevelScreen.setLevelNum(2);
				playLevelScreen.setPlayLevelScreenState(PlayLevelScreenState.RUNNING);
				playLevelScreen.initialize();
			} else if (LevelSelected == 3) {
				playLevelScreen.setLevelNum(3);
				playLevelScreen.setPlayLevelScreenState(PlayLevelScreenState.RUNNING);
				playLevelScreen.initialize();
			} else if (LevelSelected == 4) {
				playLevelScreen.setLevelNum(4);
				playLevelScreen.setPlayLevelScreenState(PlayLevelScreenState.RUNNING);
				playLevelScreen.initialize();
			}
		}

	}

	public void draw(GraphicsHandler graphicsHandler) {
		background.draw(graphicsHandler);
		levelOne.draw(graphicsHandler);
		levelTwo.draw(graphicsHandler);
		levelThree.draw(graphicsHandler);
		levelFour.draw(graphicsHandler);
		levelFive.draw(graphicsHandler);

		graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20,
				new Color(49, 207, 240), Color.black, 2);
	}

	public int getLevelSelected() {
		return LevelSelected;
	}
}
