package Engine;

import javax.swing.*;
import Game.Game;
import Level.MusicData;
import Screens.PlayLevelScreen;


/*
 * The JFrame that holds the GamePanel
 * Just does some setup and exposes the gamePanel's screenManager to allow an external class to setup their own content and attach it to this engine.
 */
public class GameWindow {
	public static  JFrame gameWindow;
	public static Game game;
	public static MusicData mD;
	private static GamePanel gamePanel;
	private Config config;
	private PlayLevelScreen pLS;

	public GameWindow() {
		gameWindow = new JFrame("Game");
		gamePanel = new GamePanel();
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gameWindow.setContentPane(gamePanel);
		gameWindow.setResizable(false);
		
		gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
		//gameWindow.setSize(Config.WIDTHM, Config.HEIGHTM);
		//gameWindow.setSize(Config.WIDTHL, Config.HEIGHTL);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
		gamePanel.setupGame();
	}
	// triggers the game loop to start as defined in the GamePanel class
	public void startGame() {
		gamePanel.startGame();
		
	}
	public void paintWindow( ) 
	{
		gameWindow.repaint();
		gamePanel.repaint();
		gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
		//gamePanel.setFocusable(true);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//gameWindow.setVisible(true);
	}
	public void update()
	{

	}

	public ScreenManager getScreenManager() {
		return gamePanel.getScreenManager();
	}
}
