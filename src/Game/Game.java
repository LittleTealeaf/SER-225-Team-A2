package Game;

import javax.sound.sampled.AudioInputStream;

import Screens.PlayLevelScreen;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Engine.Config;
import Engine.GameWindow;
import Level.MusicData;
import Engine.ScreenManager;
import Screens.PlayLevelScreen;

/*
 * The game starts here
 * This class just starts up a GameWindow and attaches the ScreenCoordinator to the ScreenManager instance in the GameWindow
 * From this point on the ScreenCoordinator class will dictate what the game does
 */
public class Game {

	public static double vol = 0.5;
	public MusicData mD;
	public static PlayLevelScreen pLS;
	public static String equalizer;
	
    public static void main(String[] args) 
    {
        new Game();
        pLS.playTheMusic();
    }

    public Game() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
        screenManager.setCurrentScreen(new ScreenCoordinator(gameWindow));
    }
	
}
