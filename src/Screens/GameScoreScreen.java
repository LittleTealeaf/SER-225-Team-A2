package Screens;

import Engine.Drawable;
import Engine.GraphicsHandler;
import Game.TimeTracker;
import Maps.TitleScreenMap;
import Menu.Menu;
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

    private TimeTracker timeTracker;

    public GameScoreScreen(TimeTracker timeTracker) {
        this.timeTracker = timeTracker;
        setBackground(new TitleScreenMap());
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
