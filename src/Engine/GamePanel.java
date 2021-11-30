package Engine;

import Game.GameState;
import Game.GameThread;
import Game.ScreenCoordinator;
import GameObject.Rectangle;
import Level.Player;
import Utils.Colors;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * This is where the game loop starts
 * The JPanel uses a timer to continually call cycles of update and draw
 */
public class GamePanel extends JPanel implements Updatable {
	private final ScreenManager screenManager;
	private final GraphicsHandler graphicsHandler;
	private boolean doPaint = false;
	protected static GameWindow gameWindow;
	private static ScreenCoordinator coordinator;
	public static Clip clip;
	
	// these difficulty values are not only just used for the the logic of the game but
	// to determine how much health the user gets at each difficulty;
	private final static int NORMAL = 3, HARD = 2, HARDCORE = 1;
	private static int difficulty;
	private final JLabel health;
	private final GameThread gameThread;

	
	/**
	 * The JPanel and various important class instances are setup here
	 */
	public GamePanel(ScreenCoordinator c1,GameWindow gameWindow) {
		super();
		this.gameWindow = gameWindow;
		this.setDoubleBuffered(true);
		
		health = new JLabel();
		add(health);

		this.setSize(Config.WIDTH, Config.HEIGHT);
		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();
		coordinator = c1;

		difficulty = NORMAL;
		
		gameThread = new GameThread(this::repaint, this::update);
	}
	
	public static void setDifficulty(int newDifficulty)
	{
		difficulty = newDifficulty;
	}
	public static int getDifficulty()
	{
		return difficulty;
	}
	
	public static ScreenCoordinator getScreenCoordinator() {
		return coordinator;
	}
	// this is called later after instantiation, and will initialize screenManager
	// this had to be done outside of the constructor because it needed to know the
	// JPanel's width and height, which aren't available in the constructor
	public void setupGame() {
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		doPaint = true;
	}

	public static GameWindow getGameWindow() {
		return gameWindow;
	}
	
	public static void music(String filepath, double gain) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

		AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filepath));
		clip = AudioSystem.getClip();
		clip.open(audioInput);
		setVolume(gain);
		clip.start();

		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	public static void setVolume(double gain) {
		try {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void setVolumeOff() {

		setVolume(0);
	}
	public static void setVolumeLow() {
		
		setVolume(.05);
	}

	public static void setVolumeMed() {
		setVolume(.1);
		
	}

	public static void setVolumeHigh() {
		setVolume(.3);
		
	}

	// this starts the timer (the game loop is started here
	public void startGame() {
		gameThread.start();

		try {
			music("Resources/Music/music.wav",.05);
		} catch(Exception e) {
			try {
				music("Resources/Music/music.mp3",.05);
			} catch(Exception f) {

			}
		}
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void update() {
		screenManager.update();
		changeHealth();
	}

	public void draw() {
		screenManager.draw(graphicsHandler);
	}
	
	// Checks the players health and accordingly changes to the image with the corresponding number of hearts
	public void changeHealth() {
		if(coordinator.getGameState() == GameState.LEVEL) {
			if(Player.PLAYER_HEALTH == 3) {
				health.setIcon(new ImageIcon(ImageLoader.load("3 Hearts.png")));
			}
			
			else if(Player.PLAYER_HEALTH == 2) {
				health.setIcon(new ImageIcon(ImageLoader.load("2 Hearts.png")));
			}
			
			else if(Player.PLAYER_HEALTH == 1) {
				health.setIcon(new ImageIcon(ImageLoader.load("1 Heart.png")));
			}
			
			else { 
				health.setIcon(new ImageIcon(ImageLoader.load("0 Hearts.png")));
			}
		}
		
		// Each difficulty is represented as an integer while also representing the amount of health the user has
		// normal is 3 hard is 2 and hardcore is 1
		// hide the health whenever in a menu
		if(coordinator.getGameState() == GameState.MENU) {
			Player.PLAYER_HEALTH = difficulty;
			health.hide();
		}
		// show the health when in a level
		else if (coordinator.getGameState() == GameState.LEVEL)
		{
			health.show();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// every repaint call will schedule this method to be called
		// when called, it will setup the graphics handler and then call this class's
		// draw method
		graphicsHandler.setGraphics((Graphics2D) g);
		if (doPaint) {
			draw();
		}
	}

	public static void mouseClicked(MouseEvent e) {
		coordinator.mouseClicked(e);
	}

}
