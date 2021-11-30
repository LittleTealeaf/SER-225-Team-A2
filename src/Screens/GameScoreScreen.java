package Screens;

import Engine.Drawable;
import Engine.GraphicsHandler;
import Engine.ScreenManager;
import Game.TimeTracker;
import Maps.GameMaps;
import Maps.TitleScreenMap;
import Menu.Menu;
import SpriteFont.SpriteFont;
import Utils.GameTimer;

import java.awt.*;

public class GameScoreScreen extends Menu {

    private static final Font FONT_LEVEL, FONT_TOTAL;
    private static final Color COLOR_LEVEL, COLOR_TOTAL;

    static {
        String fontName = "Times New Roman";
        FONT_LEVEL = new Font(fontName, Font.PLAIN, 30);
        FONT_TOTAL = new Font(fontName,Font.PLAIN,60);
        COLOR_LEVEL = Color.WHITE;
        COLOR_TOTAL = Color.WHITE;
    }


    public GameScoreScreen(TimeTracker timeTracker) {
        setBackground(new TitleScreenMap());
        System.out.println(levelsToString(timeTracker));

        SpriteFont levels = new SpriteFont(levelsToString(timeTracker), 10, ScreenManager.getScreenHeight() - 150, FONT_LEVEL, COLOR_LEVEL);
        SpriteFont total = new SpriteFont(totalToString(timeTracker),500,100,FONT_TOTAL,COLOR_TOTAL);

        setDrawables(levels,total);
    }

    private String totalToString(TimeTracker timeTracker) {
        return new StringBuilder("Total: ").append(getTotalTimes(timeTracker)).toString();
    }

    private String levelsToString(TimeTracker timeTracker) {
        StringBuilder stringBuilder = new StringBuilder();
        GameTimer[] gameTimers = timeTracker.getLevels();

        for(int i = 0; i < GameMaps.MAPS.length; i++) {
            if(gameTimers[i].getElapsed() > 0) {
                if(!stringBuilder.isEmpty()) {
                    stringBuilder.append('\n');
                }
                stringBuilder.append(GameMaps.MAPS[i].getName()).append(": ").append(gameTimers[i].toString());
            }
        }

        return stringBuilder.toString();
    }

    private GameTimer getTotalTimes(TimeTracker timeTracker) {
        GameTimer totalTime = new GameTimer();
        for(GameTimer gameTimer : timeTracker.getLevels()) {
            totalTime.addTime(gameTimer.getElapsed());
        }
        return totalTime;
    }

    @Deprecated //Maybe?
    public class LevelScores implements Drawable {

        private int x, y, height, width;


        public LevelScores(GameTimer[] scores, int x, int y, int height, int width) {

        }

        @Override
        public void draw(GraphicsHandler graphicsHandler) {

        }
    }
}
