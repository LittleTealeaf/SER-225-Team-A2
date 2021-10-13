package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Screen;
import Screens.*;


/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {

	protected Screen currentScreen = new DefaultScreen();


	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState, previousGameState;

	private int initialMap = 0;

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
					case MENU -> currentScreen = new MenuScreen();
					case LEVEL -> {
						currentScreen = new PlayLevelScreen(initialMap);
						initialMap = 0;
					} //should we skip tutorial?
					case CREDITS -> currentScreen = new CreditsScreen(this); //TODO rebuild this
					case INSTRUCTIONS -> currentScreen = new InstructionsScreen();
					case LEVELSELECT -> currentScreen = new LevelSelectScreen();
					case OPENING -> currentScreen = new OpeningScreen(this);
					case OPTIONS -> currentScreen = new OptionsScreen();
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

	public void loadLevel(int level) {
		this.initialMap = level;
		this.gameState = GameState.LEVEL;
	}
}
