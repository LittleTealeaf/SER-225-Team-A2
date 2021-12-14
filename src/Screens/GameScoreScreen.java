package Screens;

import Engine.GamePanel;
import Game.TimeTracker;
import Maps.GameMaps;
import Maps.TitleScreenMap;
import Menu.Menu;
import Menu.MenuOption;
import SpriteFont.SpriteFont;
import Utils.GameTimer;

import java.awt.*;

/**
 * Displays the game score after the player completes the game, showing the time they took to complete each level, the total time, and the
 * difficulty that the player completed on.
 *
 * @author Thomas Kwashnak
 */
public class GameScoreScreen extends Menu {

    private static final Font FONT_LEVEL, FONT_TITLE;
    private static final Color COLOR_LEVEL, COLOR_TITLE;

    static {
        String fontName = "Times New Roman";
        FONT_LEVEL = new Font(fontName, Font.PLAIN, 30);
        FONT_TITLE = new Font(fontName, Font.BOLD, 60);
        COLOR_LEVEL = COLOR_TITLE = Color.WHITE;
    }

    public GameScoreScreen(TimeTracker timeTracker) {
        setBackground(new TitleScreenMap());
        System.out.println(levelsToString(timeTracker));

        SpriteFont levels = new SpriteFont(levelsToString(timeTracker), 10, 150, FONT_LEVEL, COLOR_LEVEL);
        levels.setMultiLine(true);
        SpriteFont title = new SpriteFont("Game Complete", 200, 75, FONT_TITLE, COLOR_TITLE);
        SpriteFont difficulty = new SpriteFont(GamePanel.getDifficultyString() + " Difficulty", 500, 150, FONT_LEVEL, COLOR_LEVEL);
        setDrawables(levels, title, difficulty);

        MenuOption close = new MenuOption("Main Menu", 550, 550, this::backToMainMenu);
        setMenuItemsAsGrid(new MenuOption[][]{{close}});
    }

    /**
     * Converts the list of level-specific times to a single string, separating each level by a newline character ('\n')
     *
     * @param timeTracker Time Tracker containing the list of level-specific maps used to render
     *
     * @return Displayed list of newlines as a String
     */
    private String levelsToString(TimeTracker timeTracker) {
        StringBuilder stringBuilder = new StringBuilder();
        GameTimer[] gameTimers = timeTracker.getLevels();

        for (int i = 0; i < GameMaps.MAPS.length; i++) {
            if (gameTimers[i].getElapsed() > 0) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append('\n');
                }
                stringBuilder.append(GameMaps.MAPS[i].getName()).append(": ").append(gameTimers[i].toString());
            }
        }
        stringBuilder.append("\n\nTotal: ").append(timeTracker.getElapsedTime());

        return stringBuilder.toString();
    }
}
