package Screens;

import Engine.*;
import Game.GameState;
import Level.Map;
import Level.Player;
import Menu.Menu;
import Menu.MenuOption;

import java.awt.*;

/**
 * @author Thomas Kwashnak
 */
public class PauseScreen extends Menu {

    private static final Color COLOR_GREY_BACKGROUND;

    static {
        COLOR_GREY_BACKGROUND = new Color(0, 0, 0, 100);
    }

    private final Pausable parent;
    private boolean menuEscape;

    public PauseScreen(Map map, Player player, Pausable parent) {
        this.parent = parent;
        menuEscape = false;
        setDrawables(player, map);
        setMenuItemsAsGrid(new MenuOption[][]{
                {
                        new MenuOption("Return to Game", 100, 100, this.parent::resume)
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
            parent.resume();
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
