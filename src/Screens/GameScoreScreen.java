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

    private static final Font FONT_LEVEL;
    private static final Color COLOR_LEVEL;

    static {
        String fontName = "Times New Roman";
        FONT_LEVEL = new Font(fontName, Font.PLAIN, 30);
        COLOR_LEVEL = Color.WHITE;
    }


    public GameScoreScreen(TimeTracker timeTracker) {
        setBackground(new TitleScreenMap());
        System.out.println(getLevels(timeTracker));

        SpriteFont levels = new SpriteFont(getLevels(timeTracker), 30, ScreenManager.getScreenHeight() - 125, FONT_LEVEL, COLOR_LEVEL);

        setDrawables(levels);
    }

    private String getLevels(TimeTracker timeTracker) {
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
