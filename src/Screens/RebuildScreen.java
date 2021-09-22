package Screens;

import javax.swing.*;
import Engine.GamePanel;
import Engine.Config;
import Screens.PlayLevelScreen;


public class RebuildScreen {

	public static JFrame gameWindow;
	public static GamePanel gamePanel;
	private Config config;
		
 public RebuildScreen() {
		RebuildGameScreen();
	}
 public void RebuildGameScreen() 
 {
	gamePanel.setFocusable(true);
	gamePanel.requestFocusInWindow();
	gameWindow.setContentPane(gamePanel);
	gameWindow.setResizable(false);
	gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
	gameWindow.setLocationRelativeTo(null);
	gameWindow.setVisible(true);
	 
 }
		// triggers the game loop to start as defined in the GamePanel class
	public void startGame() 
	{
		gamePanel.startGame();
	}
}

