package Engine;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Game.ScreenCoordinator;

/*
 * The JFrame that holds the GamePanel
 * Just does some setup and exposes the gamePanel's screenManager to allow an external class to setup their own content and attach it to this engine.
 */
public class GameWindow {
	private JFrame gameWindow;
	private GamePanel gamePanel;
	
	
	

	public GameWindow(ScreenCoordinator c1) {
		gameWindow = new JFrame("Game");
		gameWindow.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				GamePanel.mouseClicked(e);
			}
		});

		gamePanel = new GamePanel(c1,this);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gameWindow.setContentPane(gamePanel);
		gameWindow.setResizable(false);
		gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
		gamePanel.setupGame();
		
		
	}

	// triggers the game loop to start as defined in the GamePanel class
	public void startGame() {
		gamePanel.startGame();
		
	}

	public ScreenManager getScreenManager() {
		return gamePanel.getScreenManager();
		
	}

	public void paintWindow() {
		gameWindow.repaint();
		gamePanel.repaint();
		gameWindow.setSize(Config.WIDTH, Config.HEIGHT);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	public Point getMousePoint() {
		return gameWindow.getMousePosition();
	}
	

	
	
}
