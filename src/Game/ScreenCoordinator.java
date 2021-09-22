package Game;

import Engine.DefaultScreen;

import Engine.GameWindow;
import Engine.GraphicsHandler;
import Engine.Screen;
import Screens.CreditsScreen;
import Screens.IntroductionScreen;
import Screens.InstructionScreen;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;
import Level.MusicData;

/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected Screen currentScreen = new DefaultScreen();
	private GameWindow gameWindow;
	private MusicData musicData;
	private PlayLevelScreen pLS;
	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState;
	protected GameState previousGameState;
	public ScreenCoordinator(GameWindow gameWindow) 
	{
		this.gameWindow = gameWindow;
	}

	public GameState getGameState() {
		return gameState;
	}

	// Other Screens can set the gameState of this class to force it to change the currentScreen
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		// start game off with Menu Screen
		//TODO: Where to start off on your level
		gameState = GameState.MENU;
	}

	@Override
	public void update() {
		do {
			// if previousGameState does not equal gameState, it means there was a change in gameState
			// this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
			
			if (previousGameState != gameState) {
				switch(gameState) {
					case INTRO:
						currentScreen = new IntroductionScreen(this);
						break;
					case MENU:
						currentScreen = new MenuScreen(this);
						break;
					case INSTRUCTIONS:
						currentScreen = new InstructionScreen(this);
						break;
					case LEVEL:
						currentScreen = new PlayLevelScreen(this, gameWindow, musicData);
						break;
					case CREDITS:
						currentScreen = new CreditsScreen(this);
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
