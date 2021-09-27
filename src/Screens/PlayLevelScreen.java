package Screens;

import java.awt.Color;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Maps.TestMap2;
import Maps.TestMap3;
import Maps.TestMap4;
import Maps.TestMap5;
import Players.Cat;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
	private KeyLocker keyLocker = new KeyLocker();
	protected ScreenCoordinator screenCoordinator;
	protected Map map;
	protected Player player;
	protected PlayLevelScreenState playLevelScreenState;
	protected Stopwatch screenTimer = new Stopwatch();
	protected LevelClearedScreen levelClearedScreen;
	protected LevelLoseScreen levelLoseScreen;
	protected SpriteFont pauseLabel;
	protected LevelSelectScreen levelSelectScreen;
	protected int levelNum = 0;
	protected int catNum = 0;
	private SpriteFont instructionLabel;
	private SpriteFont instruction2Label;
	private SpriteFont instruction3Label;
	private SpriteFont instruction4Label;
	private SpriteFont returnInstructionLabel;
	protected OptionsScreen optionsScreen;

	public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
		
		this.screenCoordinator = screenCoordinator;
		this.playLevelScreenState = PlayLevelScreenState.RUNNING;
		
	}

	public PlayLevelScreen(ScreenCoordinator screenCoordinator, PlayLevelScreenState state) {
		this.screenCoordinator = screenCoordinator;
		this.playLevelScreenState = state;

	}

	public void initialize() {

		this.map = getCurrentMap();
		map.reset();
		System.out.println(levelNum);

		levelSelectScreen = new LevelSelectScreen(this);
		levelSelectScreen.initialize();
		
		optionsScreen = new OptionsScreen(this);
		optionsScreen.initialize();
		
		pauseLabel = new SpriteFont("Pause", 350, 250, "Comic Sans", 30, Color.white);
		

		instructionLabel = new SpriteFont("To JUMP: UP arrow key, or 'W', or SPACEBAR", 130, 140, "Times New Roman", 20,
				Color.white);
		instruction2Label = new SpriteFont("To MOVE LEFT: LEFT arrow key, or 'A'", 130, 170, "Times New Roman", 20,
				Color.white);
		instruction3Label = new SpriteFont("To MOVE RIGHT: RIGHT arrow key, or 'D'", 130, 220, "Times New Roman", 20,
				Color.white);
		instruction4Label = new SpriteFont("To CROUCH: DOWN arrow key, or 'S'", 130, 260, "Times New Roman", 20,
				Color.white);
		returnInstructionLabel = new SpriteFont("Press X to return", 20, 560, "Times New Roman", 20, Color.white);
		instructionLabel.setOutlineColor(Color.white);
		instructionLabel.setOutlineThickness(2.0f);
		instruction2Label.setOutlineColor(Color.white);
		instruction2Label.setOutlineThickness(2.0f);
		instruction3Label.setOutlineColor(Color.white);
		instruction3Label.setOutlineThickness(2.0f);
		instruction4Label.setOutlineColor(Color.white);
		instruction4Label.setOutlineThickness(2.0f);
		
		
		this.player = getCat();
		//this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		this.player.setMap(map);
		this.player.addListener(this);
		this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);

	}

	public void update() {
		// based on screen state, perform specific actions
		switch (playLevelScreenState) {
		// if level is "running" update player and map to keep game logic for the
		// platformer level going

		case RUNNING:
			if (Keyboard.isKeyDown(Key.P) && !keyLocker.isKeyLocked(Key.P)) {
				playLevelScreenState = PlayLevelScreenState.PAUSE;
				keyLocker.lockKey(Key.P);
			} 
			else if (Keyboard.isKeyDown(Key.X) && !keyLocker.isKeyLocked(Key.X)) {
				playLevelScreenState = PlayLevelScreenState.INSTRUCTIONS;
				keyLocker.lockKey(Key.X);
			} else {
				player.update();
				map.update(player);
			}

			if (Keyboard.isKeyUp(Key.P)) {
				keyLocker.unlockKey(Key.P);
			}
			if (Keyboard.isKeyUp(Key.X)) {
				keyLocker.unlockKey(Key.X);
			}

			break;
		// if level has been completed, bring up level cleared screen
		case LEVEL_COMPLETED:
			levelClearedScreen = new LevelClearedScreen();
			levelClearedScreen.initialize();
			screenTimer.setWaitTime(2500);
			playLevelScreenState = PlayLevelScreenState.LEVEL_WIN_MESSAGE;
			break;
		// if level cleared screen is up and the timer is up for how long it should stay
		// out, go back to main menu
		case LEVEL_WIN_MESSAGE:
			if (screenTimer.isTimeUp()) {
				nextLevel();
				playLevelScreenState = PlayLevelScreenState.RUNNING;
			}
			break;
		// if player died in level, bring up level lost screen
		case PLAYER_DEAD:
			levelLoseScreen = new LevelLoseScreen(this, screenCoordinator);
			levelLoseScreen.initialize();
			playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE_MESSAGE;
			break;
		// wait on level lose screen to make a decision (either resets level or sends
		// player back to main menu)
		case LEVEL_LOSE_MESSAGE:
			levelLoseScreen.update();
			break;
		case LEVEL_SELECT:
			levelSelectScreen.update();
			break;
		case OPTIONS:
			optionsScreen.update();
			break;
		case PAUSE:
			if (Keyboard.isKeyDown(Key.P) && !keyLocker.isKeyLocked(Key.P)) {
				playLevelScreenState = PlayLevelScreenState.RUNNING;
				keyLocker.lockKey(Key.P);

			}
			if (Keyboard.isKeyUp(Key.P)) {
				keyLocker.unlockKey(Key.P);
			}

			break;
		case INSTRUCTIONS:
			if (Keyboard.isKeyDown(Key.X) && !keyLocker.isKeyLocked(Key.X)) {
				playLevelScreenState = PlayLevelScreenState.RUNNING;
				keyLocker.lockKey(Key.X);

			}
			if (Keyboard.isKeyUp(Key.X)) {
				keyLocker.unlockKey(Key.X);
			}

			break;

		}
	}

	public void draw(GraphicsHandler graphicsHandler) {
		// based on screen state, draw appropriate graphics
		switch (playLevelScreenState) {
		case RUNNING:
		case LEVEL_COMPLETED:

		case PLAYER_DEAD:
			map.draw(graphicsHandler);
			player.draw(graphicsHandler);
			break;
		case LEVEL_WIN_MESSAGE:
			levelClearedScreen.draw(graphicsHandler);
			break;
		case LEVEL_LOSE_MESSAGE:
			levelLoseScreen.draw(graphicsHandler);
			break;
		case LEVEL_SELECT:
			levelSelectScreen.draw(graphicsHandler);
			break;
		case OPTIONS:
			optionsScreen.draw(graphicsHandler);
			break;
		case PAUSE:
					map.draw(graphicsHandler);
			player.draw(graphicsHandler);
			pauseLabel.draw(graphicsHandler);
			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
					new Color(0, 0, 0, 100));
			break;
		case INSTRUCTIONS:
			map.draw(graphicsHandler);
			player.draw(graphicsHandler);
			instructionLabel.draw(graphicsHandler);
			instruction2Label.draw(graphicsHandler);
			instruction3Label.draw(graphicsHandler);
			instruction4Label.draw(graphicsHandler);
			returnInstructionLabel.draw(graphicsHandler);

			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
					new Color(0, 0, 0, 100));
			break;
		}
	}

	public PlayLevelScreenState getPlayLevelScreenState() {
		return playLevelScreenState;
	}

	@Override
	public void onLevelCompleted() {
		playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
	}

	@Override
	public void onDeath() {
		playLevelScreenState = PlayLevelScreenState.PLAYER_DEAD;
	}

	public Map getCurrentMap() {
		if (levelNum == 0) {
			return new TestMap();
		} else if (levelNum == 1) {
			return new TestMap2();
		} else if (levelNum == 2) {
			return new TestMap3();
		} else if (levelNum == 3) {
			return new TestMap4();

		} else {
			return new TestMap5();
		}
	}

	public void resetLevel() {
		playLevelScreenState = PlayLevelScreenState.RUNNING;
		initialize();
	}

	public void goBackToMenu() {
		screenCoordinator.setGameState(GameState.MENU);
	}

	public void nextLevel() {
		levelNum++;
		initialize();
	}

	public int getLevelNum() {

		return levelNum;
	}

	public void setLevelNum(int num) {
		levelNum = num;

	}

	public void setPlayLevelScreenState(PlayLevelScreenState state) {
		playLevelScreenState = state;
	}
	public Cat getCat() {
		
		if (catNum == 1) {
			return new Cat("Cat.png",map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		} else if (catNum == 2) {
	
			return new Cat("CatBlue.png", map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		}  else {
			return new Cat("CatGreen.png",map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
		}
		
	}
	
	// This enum represents the different states this screen can be in
	public enum PlayLevelScreenState {
		RUNNING, LEVEL_COMPLETED, PLAYER_DEAD, LEVEL_WIN_MESSAGE, LEVEL_LOSE_MESSAGE, LEVEL_SELECT, PAUSE, INSTRUCTIONS, OPTIONS
	}

	public void setCatNum(int i) {
		catNum = i;
		
	}

}
