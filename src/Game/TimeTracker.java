package Game;

import Engine.Drawable;
import Engine.GraphicsHandler;
import Engine.ScreenManager;
import Maps.GameMaps;
import Utils.GameTimer;
import Utils.TimeParser;

import java.awt.*;

/**
 * Tracks the current time that the user has spent in a given run. Displays that time in the top left corner of the screen.
 *
 * @author Thomas Kwashnak
 */
public class TimeTracker implements Drawable {

    private static final Font FONT_BIG, FONT_SMALL;

    static {
        String fontName = "Times New Roman";
        FONT_BIG = new Font(fontName, Font.PLAIN, 40);
        FONT_SMALL = new Font(fontName, Font.PLAIN, 30);
    }

    private final GameTimer[] levels;
    private final GameTimer total;
    private int currentLevel;
    /*
    These values are updated (conservatively) whenever the charsTotal or charsMap changes. Indicates the starting position x and y of the text on
    the screen
     */
    private int xMap, yMap, xTotal, yTotal, charsTotal, charsMap;

    /**
     * Whether the level-specific time should be listed
     */
    private boolean showLevel;

    public TimeTracker() {
        //Instantiate the timers
        total = new GameTimer();
        levels = new GameTimer[GameMaps.MAPS.length];

        for (int i = 0; i < levels.length; i++) {
            levels[i] = new GameTimer();
        }

        //Sets the current level to -1 so it does not display anything initially
        currentLevel = -1;
        showLevel = false;
    }

    /**
     * Sets the current running level. If the total is not running, it starts the timer. All level-specific timers are paused except for the level
     * that is now the current level, which is started.
     *
     * @param currentLevel The given level to start
     */
    public void setCurrentLevel(int currentLevel) {
        //Only does anything if it actually changes the level
        if (this.currentLevel != currentLevel) {
            //Starts the total
            total.start();
            /*
            If it is "changing" the current level (meaning that it is not the first level to start), then it will stop the last level's timer and
            set the showLevel value to true, which indicates if the level specific timer should be shown along with the total timer
             */
            if (this.currentLevel > -1) {
                levels[this.currentLevel].stop();
                showLevel = true;
            }
            //Updates the current level and starts the timer
            this.currentLevel = currentLevel;
            levels[currentLevel].start();
        }
    }

    /**
     * Usable when dealing with pausing
     */
    public void start() {
        total.start();
        levels[currentLevel].start();
    }

    public void stop() {
        total.stop();
        levels[currentLevel].stop();
    }

    /**
     * @return A TimeParser class that contains the total elapsed time of all levels (more accurate than totalTime)
     */
    public TimeParser getElapsedTime() {
        TimeParser timeParser = new TimeParser();
        for (GameTimer gameTimer : levels) {
            timeParser.addTime(gameTimer.getElapsed());
        }
        return timeParser;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        //Get the totalString (efficiently), and then performs the check on the position
        String totalString = total.toString();
        if (totalString.length() != charsTotal) {
            /*
            If the length of the total string has changed, then use font metrics to get the location, width, and height using the FONT_BIG.
             */
            FontMetrics metrics = graphicsHandler.getGraphics2D().getFontMetrics(FONT_BIG);
            yTotal = metrics.getHeight();
            xTotal = ScreenManager.getScreenWidth() - metrics.stringWidth(totalString.replace(' ', '0')) - 5;
            charsTotal = totalString.length();
        }
        //Draw the total string
        graphicsHandler.drawString(totalString, xTotal, yTotal, FONT_BIG, Color.white);

        /*
        Only if it is the second level that has been started (meaning the player has completed at least one level), then show the level specific timer
         */
        if (showLevel) {
            String mapString = levels[currentLevel].toString();
            if (mapString.length() != charsMap) {
                /*
                    If the length of the map time string has changed, then use font metrics to get the location, width, and height using the FONT_BIG.
                */
                FontMetrics metrics = graphicsHandler.getGraphics2D().getFontMetrics(FONT_SMALL);
                yMap = metrics.getHeight() + yTotal;
                xMap = ScreenManager.getScreenWidth() - metrics.stringWidth(mapString.replace(' ', '0')) - 5;
                charsMap = mapString.length();
            }
            graphicsHandler.drawString(mapString, xMap, yMap, FONT_SMALL, Color.white);
        }
    }

    public GameTimer[] getLevels() {
        return levels;
    }
}
