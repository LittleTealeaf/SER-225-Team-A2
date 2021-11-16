package Screens;

import Engine.*;
import Game.GameState;
import Level.Map;
import Level.Player_Old;
import Menu.Menu;
import Menu.MenuOption;
import SpriteFont.SpriteFont;

import java.awt.*;

public class PauseScreen extends Menu {

    private static final Color COLOR_GREY_BACKGROUND;
    private static final SpriteFont PAUSE_INSTRUCTIONS;

    static {

        COLOR_GREY_BACKGROUND = new Color(0, 0, 0, 100);
        PAUSE_INSTRUCTIONS = new SpriteFont("SOMETHING SOMETHING ", 350, 250, "Comic Sans", 30, Color.white);
    }

    private final PlayLevelScreen playLevelScreen;
    private boolean menuEscape;

    public PauseScreen(Map map, Player_Old player, PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        menuEscape = false;
        setDrawables(new Drawable[]{
                player, map
        });
        setMenuItemsAsGrid(new MenuOption[][]{
                {
                        new MenuOption("Return to Game", 100, 100, () -> this.playLevelScreen.resume())
                }, {
                        new MenuOption("Back to Menu", 100, 200, () -> GamePanel.getScreenCoordinator().setGameState(GameState.MENU))
                }
        });
    }

    /**
     * Specifying what actions we actually want to take
     */
    public void update() {
        updateMenu();
        if (menuEscape && KeyboardAction.GAME_PAUSE.isDown()) {
            playLevelScreen.resume();
        }
        if (!KeyboardAction.GAME_PAUSE.isDown()) {
            menuEscape = true;
        }
    }

    /**
     * Overrides the draw function to specify a specific order
     *
     * @param graphicsHandler
     */
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        drawDrawables(graphicsHandler);
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth() + 50, ScreenManager.getScreenHeight() + 50, COLOR_GREY_BACKGROUND);
        drawMenuOptions(graphicsHandler);
    }
}
