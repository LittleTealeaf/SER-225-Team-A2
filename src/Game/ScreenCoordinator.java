package Game;

import Engine.DefaultScreen;
import Engine.GameWindow;
import Engine.GraphicsHandler;
import Engine.Screen;
import Screens.CreditsScreen;
import Screens.MenuScreen;

import Screens.OpeningScreen;
import Screens.OptionsScreen;
import Screens.PlayLevelScreen;
import Screens.PlayLevelScreen.PlayLevelScreenState;
import Screens.InstructionsScreen;


/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected GameWindow gameWindow;
	protected Screen currentScreen = new DefaultScreen();

	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState;
	protected GameState previousGameState;

	public  GameState getGameState() {
		return gameState;
	}

	// Other Screens can set the gameState of this class to force it to change the currentScreen
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		// start game off with Menu Screen
		gameState = GameState.MENU;
	}

	@Override
	public void update() {
		do {
			// if previousGameState does not equal gameState, it means there was a change in gameState
			// this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
			if (previousGameState != gameState) {
				switch(gameState) {
					case MENU:
						currentScreen = new MenuScreen(this);
						break;
					case LEVEL:
						currentScreen = new PlayLevelScreen(this);
						break;
					case CREDITS:
						currentScreen = new CreditsScreen(this);
						break;
					case INSTRUCTIONS:
						currentScreen = new InstructionsScreen(this);
						break;

					case LEVELSELECT:
						currentScreen = new PlayLevelScreen(this,PlayLevelScreenState.LEVEL_SELECT);
						break;

					case OPENING:
					    currentScreen = new OpeningScreen(this);
					    break;
					case OPTIONS:
						currentScreen = new PlayLevelScreen(this,PlayLevelScreenState.OPTIONS);
						break;
				}
				currentScreen.initialize();
			}
			previousGameState = gameState;

			// call the update method for the currentScreen
			currentScreen.update();
		} while (previousGameState != gameState);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// call the draw method for the currentScreen
		currentScreen.draw(graphicsHandler);
	}
}
