package Engine;

import Game.GameState;
import Game.GameThread;
import Game.ScreenCoordinator;
import GameObject.RectangleOld;
import Level.Player;
import Utils.Colors;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/*
 * This is where the game loop starts
 * The JPanel uses a timer to continually call cycles of update and draw
 */
public class GamePanel extends JPanel {

    public static Clip clip;
    protected static GameWindow gameWindow;
    private static ScreenCoordinator coordinator;
    private final ScreenManager screenManager;
    // used to draw graphics to the panel
    private final GraphicsHandler graphicsHandler;
    private final JLabel health;
    // used to create the game loop and cycle between update and draw calls
    private Timer timer;
    private boolean doPaint = false;
    private GameThread gameThread;
    //	private ThreadManager gameThread;
    //	private ThreadManager renderThread;


    /*
     * The JPanel and various important class instances are setup here
     */
    public GamePanel(ScreenCoordinator c1, GameWindow gameWindow) {
        super();
		GamePanel.gameWindow = gameWindow;
        this.setDoubleBuffered(true);

        health = new JLabel();
        add(health);

        this.setSize(Config.WIDTH, Config.HEIGHT);
        // attaches Keyboard class's keyListener to this JPanel
        this.addKeyListener(Keyboard.getKeyListener());

        graphicsHandler = new GraphicsHandler();

        screenManager = new ScreenManager();
        coordinator = c1;

        gameThread = new GameThread(this::repaint, this::update);

        //		gameThread = new GameThreadDeprecated(() -> {
        //			update();
        //			repaint();
        //		});
        //		renderThread = new RenderThread(() -> {
        //
        //		});
        //WORKS
		/*
		However, current plan should be as follows:
		 - First get the system to work on a single thread (with the dynamic speed and such)
		 - THEN get the system to work on multiple threads for painting / etc
		 */

        //		timer = new Timer(1000 / Config.FPS, new ActionListener() {
        //			public void actionPerformed(ActionEvent e) {
        //				update();
        //				changeHealth();
        //				if(coordinator.getGameState() == GameState.LEVEL) {
        //					health.show();
        //				}
        //				else {
        //					health.hide();
        //				}
        //				repaint();
        //			}
        //		});
        //		timer.setRepeats(true);
        //		tickGame = new GameTick(new Runnable() {
        //			@Override
        //			public void run() {
        //				update();
        //			}
        //		});
        //		tickRender = new GameTick(new Runnable() {
        //			@Override
        //			public void run() {
        //				repaint();
        //			}
        //		});
    }

    public void update() {

        screenManager.update();
        changeHealth();
    }

    // Checks the players health and accordingly changes to the image with the corresponding number of hearts
    public void changeHealth() {
        if (coordinator.getGameState() == GameState.LEVEL) {
            if (Player.PLAYER_HEALTH == 3) {
                health.setIcon(new ImageIcon(ImageLoader.load("3 Hearts.png")));
            } else if (Player.PLAYER_HEALTH == 2) {
                health.setIcon(new ImageIcon(ImageLoader.load("2 Hearts.png")));
            } else if (Player.PLAYER_HEALTH == 1) {
                health.setIcon(new ImageIcon(ImageLoader.load("1 Heart.png")));
            } else {
                health.setIcon(new ImageIcon(ImageLoader.load("0 Hearts.png")));
            }
        }

        if (coordinator.getGameState() == GameState.MENU) {
            Player.PLAYER_HEALTH = 3;
        }
    }

    public static ScreenCoordinator getScreenCoordinator() {
        return coordinator;
    }

    public static GameWindow getGameWindow() {
        return gameWindow;
    }

    public static void setVolumeOff() {

        setVolume(0);
    }

    public static void setVolume(double gain) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void mouseClicked(MouseEvent e) {
        //		System.out.println("Click: " + e.getPoint());
        coordinator.mouseClicked(e);
    }

    // this is called later after instantiation, and will initialize screenManager
    // this had to be done outside of the constructor because it needed to know the
    // JPanel's width and height, which aren't available in the constructor
    public void setupGame() {
        setBackground(Colors.CORNFLOWER_BLUE);
        screenManager.initialize(new RectangleOld(getX(), getY(), getWidth(), getHeight()));
        doPaint = true;
    }

    // this starts the timer (the game loop is started here
    public void startGame() {
        //		timer.start();
        gameThread.start();
        //		renderThread.start();

        try {
            music("Resources/Music/music.wav", .05);
        } catch (Exception e) {
            try {
                music("Resources/Music/music.mp3", .05);
            } catch (Exception f) {

            }
        }
    }

    public static void music(String filepath, double gain) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filepath));
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        setVolume(gain);
        clip.start();

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public ScreenManager getScreenManager() {
        return screenManager;
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

    public void draw() {
        screenManager.draw(graphicsHandler);
    }
}
