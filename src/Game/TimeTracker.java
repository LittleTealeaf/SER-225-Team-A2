package Game;

import Engine.Drawable;
import Engine.GraphicsHandler;
import Engine.ScreenManager;
import Maps.GameMaps;
import Utils.GameTimer;
import Utils.TimeParser;

import java.awt.*;

public class TimeTracker implements Drawable {

    private static final Font FONT_BIG, FONT_SMALL;

    static {
        String fontName = "Times New Roman";
        FONT_BIG = new Font(fontName,Font.PLAIN, 40);
        FONT_SMALL = new Font(fontName,Font.PLAIN,30);
    }


    private int currentLevel;

    private GameTimer[] levels;
    private GameTimer total;

    /**
     * Whether the level-specific time should be listed
     */
    private boolean showLevel;

    public TimeTracker() {
        total = new GameTimer();
        levels = new GameTimer[GameMaps.MAPS.length];

        for(int i = 0; i < levels.length; i++) {
            levels[i] = new GameTimer();
        }

        currentLevel = -1;
        showLevel = false;
    }

    public void setCurrentLevel(int currentLevel) {
        if(this.currentLevel != currentLevel) {
            if(this.currentLevel == -1) {
                total.start();
            } else {
                levels[this.currentLevel].stop();
                showLevel = true;
            }
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

    public TimeParser getElapsedTime() {
        TimeParser timeParser = new TimeParser();
        for(GameTimer gameTimer : levels) {
            timeParser.addTime(gameTimer.getElapsed());
        }
        return timeParser;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        //Custom rendering in order to place it correctly
        FontMetrics metrics = graphicsHandler.getGraphics2D().getFontMetrics(FONT_BIG);
        String totalString = total.toString();
        int yTotal = metrics.getHeight();
        int xTotal = ScreenManager.getScreenWidth() - metrics.stringWidth(totalString);
        graphicsHandler.drawString(totalString, Math.round(xTotal), yTotal, FONT_BIG, Color.WHITE);

        if(showLevel) {
            metrics = graphicsHandler.getGraphics2D().getFontMetrics(FONT_SMALL);
            String mapString = levels[currentLevel].toString();
            int yMap = yTotal + metrics.getHeight();
            int xMap = ScreenManager.getScreenWidth() - metrics.stringWidth(mapString);
            graphicsHandler.drawString(mapString,xMap, yMap, FONT_SMALL,Color.white);
        }
    }
}
