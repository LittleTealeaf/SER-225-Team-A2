package Engine;

import GameObject.Rectangle;




import SpriteFont.SpriteFont;
import Utils.Colors;
import Utils.Stopwatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//import sun.audio.AudioData;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

import Game.GameState;
import Game.ScreenCoordinator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * This is where the game loop starts
 * The JPanel uses a timer to continually call cycles of update and draw
 */
public class GamePanel extends JPanel {
	// loads Screens on to the JPanel
	// each screen has its own update and draw methods defined to handle a "section"
	// of the game.
	protected KeyLocker keyLocker = new KeyLocker();
	private ScreenManager screenManager;
	// used to create the game loop and cycle between update and draw calls
	private Timer timer;
	// used to draw graphics to the panel
	private GraphicsHandler graphicsHandler;
	private boolean doPaint = false;
	protected int pointerLocationX, pointerLocationY;	
	protected Stopwatch keyTimer = new Stopwatch();
	protected static GameWindow gameWindow;
	private static ScreenCoordinator coordinator;
	public static Clip clip;

	
	/*
	 * The JPanel and various important class instances are setup here
	 */
	public GamePanel(ScreenCoordinator c1,GameWindow gameWindow) {
		super();
		this.gameWindow = gameWindow;
		this.setDoubleBuffered(true);

		this.setSize(Config.WIDTH, Config.HEIGHT);
		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();
		coordinator = c1;

	
		

		timer = new Timer(1000 / Config.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		timer.setRepeats(true);
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
	
	public static void music(String filepath, double gain) {
	
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filepath));
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			setVolume(gain);
			clip.start();
	
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			

		} catch (Exception ex) {
			System.out.println("No audio found!");
			ex.printStackTrace();

		}
		
	}
	public static void setVolume(double gain) {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
		
	}

	public static void setVolumeLow() {
		
		setVolume(.5);
	}

	public static void setVolumeMed() {
		setVolume(1);
		
	}

	public static void setVolumeHigh() {
		setVolume(2);
		
	}

	// this starts the timer (the game loop is started here
	public void startGame() {
		timer.start();

		music("src/Blossoming Inspiration Loop (online-audio-converter.com).wav",1);
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void update() {
			screenManager.update();
	}

	public void draw() {
		screenManager.draw(graphicsHandler);


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
	
	

}
