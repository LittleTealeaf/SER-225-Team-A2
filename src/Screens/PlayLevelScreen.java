package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.LevelFour;
import Maps.LevelThree;
import Maps.LevelTwo;
import Maps.LevelOne;
import Players.Cat;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import Engine.GameWindow;
import Screens.RebuildScreen;

import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected Stopwatch screenTimer = new Stopwatch();
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;
    private GameWindow gameWindow;
    private RebuildScreen rB;
    private Config config;
    protected int currentLevel = 0;
    private MusicData mD;
//    private Game game;
//    public static Camera cam;

    private boolean isGamePaused = false;
    private SpriteFont pauseLabel;
    private KeyLocker keyLocker = new KeyLocker();
    private final Key pauseKey = Key.P;

    //Pause Screen Info and Buttons
    protected int currentMenuItemHovered = 1; // current menu item being "hovered" over
    protected int currentSettingLevelHovered = 0;
    protected int settingsMenuItemSelected = 1;
    protected int menuItemSelected = -1;
    public static double vol = 0.25;
    protected SpriteFont pauseHeader;
    protected SpriteFont settingsButton;
    protected SpriteFont mainMenuButton;
    protected SpriteFont lowVol;
    protected SpriteFont midVol;
    protected SpriteFont HighVol;
    protected SpriteFont smallScreen;
    protected SpriteFont mediumScreen;
    protected SpriteFont bigScreen;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    
    //settings menu
    protected boolean settingsActive = false;
    protected boolean volActive = false;
    protected boolean aspectActive = false;
    public boolean screenS = true;
    public boolean screenM = false;
    public boolean screenL = false;
    protected SpriteFont volumeLevel;
    
    protected SpriteFont aspectRatioLevel;


    public PlayLevelScreen(ScreenCoordinator screenCoordinator, GameWindow gameWindow, MusicData musicData) {
        this.screenCoordinator = screenCoordinator;
        this.gameWindow = gameWindow;
        //Pause Screen Initialization
        pauseHeader = new SpriteFont("PAUSED", 300, 100, "Comic Sans", 50, new Color(255, 255, 255));
        pauseHeader.setOutlineColor(Color.black);
        pauseHeader.setOutlineThickness(3);
        settingsButton = new SpriteFont("SETTINGS", 130, 250, "Comic Sans", 30, new Color (49, 207,240));
        settingsButton.setOutlineColor(Color.black);
        settingsButton.setOutlineThickness(3);
        mainMenuButton = new SpriteFont("MAIN MENU", 120, 330, "Comic Sans", 30, new Color(49, 207, 240));
        mainMenuButton.setOutlineColor(Color.black);
        mainMenuButton.setOutlineThickness(3);
        //Settings Buttons
        //Sound Settings
        volumeLevel = new SpriteFont("Volume: ", 490, 210, "Comic Sans", 30, new Color (49, 207,240));
        volumeLevel.setOutlineColor(Color.black);
        volumeLevel.setOutlineThickness(3);
        lowVol = new SpriteFont("Low ", 370, 245, "Comic Sans", 30, new Color (49, 207,240));
        lowVol.setOutlineColor(Color.black);
        lowVol.setOutlineThickness(3);
        midVol = new SpriteFont("Medium ", 500, 245, "Comic Sans", 30, new Color (49, 207,240));
        midVol.setOutlineColor(Color.black);
        midVol.setOutlineThickness(3);
        HighVol = new SpriteFont("Full ", 670, 245, "Comic Sans", 30, new Color (49, 207,240));
        HighVol.setOutlineColor(Color.black);
        HighVol.setOutlineThickness(3);
        //Aspect Ratio Settings
        aspectRatioLevel = new SpriteFont("Aspect Ratio: ", 470, 330, "Comic Sans", 30, new Color(49, 207, 240));
        aspectRatioLevel.setOutlineColor(Color.black);
        aspectRatioLevel.setOutlineThickness(3);
        smallScreen = new SpriteFont("Small ", 360, 370, "Comic Sans", 30, new Color (49, 207,240));
        smallScreen.setOutlineColor(Color.black);
        smallScreen.setOutlineThickness(3);
        mediumScreen = new SpriteFont("Medium ", 490, 370, "Comic Sans", 30, new Color (49, 207,240));
        mediumScreen.setOutlineColor(Color.black);
        mediumScreen.setOutlineThickness(3);
        bigScreen = new SpriteFont("Large ", 660, 370, "Comic Sans", 30, new Color (49, 207,240));
        bigScreen.setOutlineColor(Color.black);
        bigScreen.setOutlineThickness(3);
    }

    public void initialize() {
        // define/setup map

        switch (currentLevel) {
            case 0:
                //TODO: Change this to desired map to start on that map
                this.map = new LevelOne();
                map.reset();
                break;
            case 1:
                this.map = new LevelTwo();
                map.reset();
                break;
            case 2:
                this.map = new LevelThree();
                map.reset();
                break;
            case 3:
                this.map = new LevelFour();
                map.reset();
                break;
        }

        // setup player
        this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        if (currentLevel > 0) {
            player.unlockPowerUpOne();
        }
        this.player.setMap(map);
        this.player.addListener(this);
        this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
        keyTimer.setWaitTime(200);
    }

    public void update() {
        if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {

            isGamePaused = !isGamePaused;
            keyLocker.lockKey(pauseKey);
        }

        if (Keyboard.isKeyUp(pauseKey)) {
            keyLocker.unlockKey(pauseKey);
        }

        if (!isGamePaused) {
            // based on screen state, perform specific actions
            switch (playLevelScreenState) {
                // if level is "running" update player and map to keep game logic for the platformer level going
                case RUNNING:
                    player.update();
                    map.update(player);
                    break;
                // if level has been completed, bring up level cleared screen
                case LEVEL_COMPLETED:
                    levelClearedScreen = new LevelClearedScreen();
                    levelClearedScreen.initialize();
                    screenTimer.setWaitTime(2500);
                    playLevelScreenState = PlayLevelScreenState.LEVEL_WIN_MESSAGE;
                    currentLevel++;
                    break;
                // if level cleared screen is up and the timer is up for how long it should stay out, go back to main menu
                case LEVEL_WIN_MESSAGE:
                    if (screenTimer.isTimeUp()) {
                        levelClearedScreen = null;
                        //TODO: Update this if another level is added
                        if (currentLevel > 3) {
                            goBackToMenu();
                        } else {
                            playLevelScreenState = PlayLevelScreenState.RUNNING;
                            this.initialize();
                        }
                    }
                    break;
                // if player died in level, bring up level lost screen
                case PLAYER_DEAD:
                    levelLoseScreen = new LevelLoseScreen(this);
                    levelLoseScreen.initialize();
                    playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE_MESSAGE;
                    break;
                // wait on level lose screen to make a decision (either resets level or sends player back to main menu)
                case LEVEL_LOSE_MESSAGE:
                    levelLoseScreen.update();
                    break;
            }
        }
   
        if(isGamePaused == true)
        {
        	if(settingsActive == false) 
        	{
        		if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp())
        		{
        			keyTimer.reset();
        			currentMenuItemHovered++;
        		}
        		else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp())
        		{
        			keyTimer.reset();
        			currentMenuItemHovered--;
        		} 
        	}
        	if(settingsActive == true) 
        	{

        		if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp())
            	{
            		keyTimer.reset();
            		settingsMenuItemSelected++;
            	}
            	else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp())
            	{
            		keyTimer.reset();
            		settingsMenuItemSelected--;
            	}
        		if (Keyboard.isKeyDown(Key.RIGHT) && keyTimer.isTimeUp())
                {
                	keyTimer.reset();
                	currentSettingLevelHovered++;
                }
                else if (Keyboard.isKeyDown(Key.LEFT) && keyTimer.isTimeUp())
                {
                	keyTimer.reset();
                	currentSettingLevelHovered--;
                }	
        	}

       
        	 // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
            if (currentMenuItemHovered > 1)
            {
            	currentMenuItemHovered = 2;
            }
            else if (currentMenuItemHovered < 1)
            {
                currentMenuItemHovered = 1;
            }
            if (settingsMenuItemSelected > 1)
            {
            	settingsMenuItemSelected = 2;
            }
            else if (settingsMenuItemSelected < 1)
            {
            	settingsMenuItemSelected = 1;
            }
            if (currentSettingLevelHovered > 2)
            {
            	currentSettingLevelHovered = 0;
            }
            else if (currentSettingLevelHovered < 0)
            {
            	currentSettingLevelHovered = 2;
            }
            // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
            if (currentMenuItemHovered == 1)
            {
            	pauseHeader.setColor(new Color(255, 255, 255));
            	settingsButton.setColor(new Color(255, 215, 0));
                mainMenuButton.setColor(new Color(49, 207, 240));
                pointerLocationX = 100;
                pointerLocationY = 230;
            }
            if (currentMenuItemHovered == 2)
            {
            	pauseHeader.setColor(new Color(255, 255, 255));
            	settingsButton.setColor(new Color(49, 207, 240));
            	mainMenuButton.setColor(new Color(255, 215, 0));
                pointerLocationX = 90;
                pointerLocationY = 310;
            }
            if (settingsMenuItemSelected == 1 && settingsActive == true)
            {
            	volActive = true;
            	smallScreen.setColor(new Color(49, 207, 240));
            	mediumScreen.setColor(new Color(49, 207, 240));
            	bigScreen.setColor(new Color(49, 207, 240));
            	if(currentSettingLevelHovered == 0) 
            	{
            		lowVol.setColor(new Color(255, 215, 0));
                	midVol.setColor(new Color(49, 207, 240));
                	HighVol.setColor(new Color(49, 207, 240));
                	pointerLocationX = 330;
                    pointerLocationY = 225;
            	}
            	if(currentSettingLevelHovered == 1) 
            	{
            		lowVol.setColor(new Color(49, 207, 240));
                	midVol.setColor(new Color(255, 215, 0));
                	HighVol.setColor(new Color(49, 207, 240));
                	pointerLocationX = 470;
                    pointerLocationY = 225;
            	}
            	if(currentSettingLevelHovered == 2) 
            	{
            		lowVol.setColor(new Color(49, 207, 240));
                	midVol.setColor(new Color(49, 207, 240));
                	HighVol.setColor(new Color(255, 215, 0));
                	pointerLocationX = 640;
                    pointerLocationY = 225;
            	}
                
            }
            else if (settingsMenuItemSelected == 2 && settingsActive == true)
            {
            	lowVol.setColor(new Color(49, 207, 240));
            	midVol.setColor(new Color(49, 207, 240));
            	HighVol.setColor(new Color(49, 207, 240));
            	aspectActive = true;
            	if(currentSettingLevelHovered == 0) 
            	{
            		smallScreen.setColor(new Color(255, 215, 0));
                	mediumScreen.setColor(new Color(49, 207, 240));
                	bigScreen.setColor(new Color(49, 207, 240));
                	pointerLocationX = 330;
                    pointerLocationY = 350;
            	}
            	if(currentSettingLevelHovered == 1) 
            	{
            		smallScreen.setColor(new Color(49, 207, 240));
                	mediumScreen.setColor(new Color(255, 215, 0));
                	bigScreen.setColor(new Color(49, 207, 240));
                	pointerLocationX = 460;
                    pointerLocationY = 350;
            	}
            	if(currentSettingLevelHovered == 2) 
            	{
            		smallScreen.setColor(new Color(49, 207, 240));
                	mediumScreen.setColor(new Color(49, 207, 240));
                	bigScreen.setColor(new Color(255, 215, 0));
                	pointerLocationX = 630;
                    pointerLocationY = 350;
            	}  
            }

        
        if (Keyboard.isKeyUp(Key.SPACE))
        {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE))
        {
        	keyLocker.lockKey(Key.SPACE);
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 1)
            {
                if(settingsActive == false)
                {
                    settingsActive = true;

                }
                else if(settingsActive == true)
                {
                    //settingsActive = false;
                    if (settingsMenuItemSelected == 1)
                    {
                        if(currentSettingLevelHovered == 0)
                        {
                            setVolLow();
                            settingsActive = false;
                        }
                        if(currentSettingLevelHovered == 1)
                        {
                            setVolMid();
                            settingsActive = false;

                        }
                        if(currentSettingLevelHovered == 2)
                        {
                            setVolFull();
                            settingsActive = false;
                        }
                    }
                    else if (settingsMenuItemSelected == 2)
                    {
                        if(currentSettingLevelHovered == 0)
                        {

                            setScreenSmall();
                            settingsActive = false;

                            //gW.setBoundsSmall();
                        }
                        if(currentSettingLevelHovered == 1)
                        {
                            setScreenMid();
                            settingsActive = false;
                            //gW.setBoundsMedium();
                        }
                        if(currentSettingLevelHovered == 2)
                        {
                            setScreenLarge();
                            settingsActive = false;
                        }
                    }
                }
            }
            else if (menuItemSelected == 2)
            {
                screenCoordinator.setGameState(GameState.MENU);
            }
        }
      }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
            case LEVEL_COMPLETED:
            case PLAYER_DEAD:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_WIN_MESSAGE:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }
        if (isGamePaused)
        {
            graphicsHandler.drawFilledRectangle(0, 0, Config.WIDTH, Config.HEIGHT, new Color(0, 0, 0, 100));
            pauseHeader.draw(graphicsHandler);
            settingsButton.draw(graphicsHandler);
            mainMenuButton.draw(graphicsHandler);
            graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
            if(settingsActive == false)
        	{
            	pauseHeader.draw(graphicsHandler);
                settingsButton.draw(graphicsHandler);
                mainMenuButton.draw(graphicsHandler);
        	}
        	else if(settingsActive == true)
        	{
        		volumeLevel.draw(graphicsHandler);
        		aspectRatioLevel.draw(graphicsHandler);
        		lowVol.draw(graphicsHandler);
        		midVol.draw(graphicsHandler);
        		HighVol.draw(graphicsHandler);
        		smallScreen.draw(graphicsHandler);
        		mediumScreen.draw(graphicsHandler);
        		bigScreen.draw(graphicsHandler);
        	}
        }

    }
    public int getMenuItemSelected() {
        return menuItemSelected;
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    public void setVolLow()
    {
        System.out.println("screen vol low");
        mD.setVolCall("Low");
        System.out.println("Current Vol: " + vol);

    }
    public void setVolMid()
    {
        System.out.println("screen vol mid");
        mD.setVolCall("Mid");
        System.out.println("Current Vol: " + vol);
    }
    public void setVolFull()
    {
        System.out.println("screen vol high");
        mD.setVolCall("Full");
        System.out.println("Current Vol: " + vol);
    }

    public void setScreenSmall() 
    {
    	screenS = true;
		screenM = false;
		screenL = false;
		config.WIDTH = 800;
		config.HEIGHT = 605;
        Camera.setMultiplyInt(140);
		gameWindow.paintWindow();
		
    }
    public void setScreenMid() 
    {
    	screenS = false;
		screenM = true;
		screenL = false;
		config.WIDTH = 950;
		config.HEIGHT = 705;
        Camera.setMultiplyInt(240);
		gameWindow.paintWindow();
    }
    public void setScreenLarge() 
    {
		screenS = false;
		screenM = false;
		screenL = true;
		
		config.WIDTH = 1100;
		config.HEIGHT = 710;
		Camera.setMultiplyInt(310);
		gameWindow.paintWindow();
    }

    public static void playTheMusic()
    {

        String filePath = "Resources/MakafushigiAdventure.wav";
        MusicData musicObject = new MusicData();
        musicObject.playMusic(filePath, vol);

    }

    @Override
    public void onLevelCompleted() {
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    @Override
    public void onDeath() {
        playLevelScreenState = PlayLevelScreenState.PLAYER_DEAD;
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }


    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, PLAYER_DEAD, LEVEL_WIN_MESSAGE, LEVEL_LOSE_MESSAGE,
    }
}
