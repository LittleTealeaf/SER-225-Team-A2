package Menu;

import Engine.*;
import Game.GameState;
import Level.Map;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.event.MouseEvent;

/*
If you are in IntelliJ (as of Intellij 2021.2.3 or newer I think, actually some older ones should work too), you can preview the rendered document
below by the left when hovering over the first / line.
 */

/**
 * An abstract class that sets up and provides methods for displaying a menu screen. This class handles the menu options, including moving around
 * using arrow keys, or selecting menu options using the mouse. This implements the {@link SelectableMenu} class, so it can be passed as a method to
 * call whenever a selection change is called for.
 *
 * <p><b>Making a Menu:</b>
 * A menu is composed of a few items: A background, the menu options, and any additional drawables (classes that implement the Drawable interface).
 * When extending the menu class, these can be configured as described below::
 * <ul>
 *     <li><b>Background:</b> As the name implies, this is the background. If this is null, then a blank background will just be rendered. When
 *     extending the Menu class, set a background using the {@link #setBackground(Map)} method. By default, many menus use the "TitleScreenMap"
 *     map. Examples of using the TitleScreenMap can be found in {@link Screens.MenuScreen}</li>
 *     <li><b>Menu Options:</b> This is probably the most complicated part to get right. Each {@link MenuOption} is set up like a node in a
 *     directed graph. Each node has a set of neighbors that it references when the user wants to move in a given direction (up, down, left,
 *     right). To set these up, you have a few options. If your nodes are in any form of "grid-like" pattern, you are free to use the
 *     {@link #setMenuItemsAsGrid(MenuOption[][])} method to add the menu options. What this will do is set each menuOption's neighbor to the
 *     closest menu option in each direction automatically. While this gets most of the work done, sometimes it can't do everything. In order to
 *     manually set the graph, you will need to go into each node individually and set their neighbors. You can use whatever for loops or tools
 *     you wish at your disposal to set these, but the majority should be completable using the {@link #setMenuItemsAsGrid(MenuOption[][])} method.
 *     A good example of using both of these methods is the {@link Screens.OptionsScreen}.<br>
 *     If you do plan on custom-setting the links completely, feel free to use the {@link #setMenuOptions(MenuOption[])}</li>
 *     <li><b>Drawables:</b> This is the final icing to the layering cake that renders each frame. This is a list of any {@link Drawable} object to
 *     render. This can be a variety of {@link SpriteFont.SpriteFont}, {@link GameObject.AnimatedSprite}, or other classes that implement the
 *     {@link Drawable} interface
 *     </li>
 * </ul>
 * </p>
 *
 * @author Thomas Kwashnak
 * @see MenuOption
 */
public abstract class Menu extends Screen implements SelectableMenu {

    private final Stopwatch keyTimer = new Stopwatch();
    private final KeyLocker keyLocker = new KeyLocker();
    private MenuOption[] menuOptions;
    private MenuOption selectedItem;
    private Drawable[] drawables;
    private Map background;

    private Point previousMouse;

    @Override
    public void initialize() {
        if (menuOptions != null) {
            selectedItem = menuOptions[0];
            selectedItem.setSelected(true);
        }
        if (background != null) {
            background.setAdjustCamera(false);
        }
        keyTimer.setWaitTime(200);
    }

    /**
     * Delegates to the {@link #updateBackground()} , {@link #updateMenuEscape()}, and {@link #updateMenu()} methods. Allows for overriding in
     * order to customize the update order, or remove specific features completely.
     */
    @Override
    public void update() {
        updateBackground();
        updateMenuEscape();
        updateMenu();
    }

    /**
     * Updates the background of the player, which allows the visual changes of map tiles.
     */
    protected void updateBackground() {
        if (background != null) {
            background.update(null);
        }
    }

    /**
     * Checks if the escape menu is closed, and then will call the {@link #backToMainMenu()} method if it is.
     */
    protected void updateMenuEscape() {
        if (KeyboardAction.MENU_ESCAPE.isDown()) {
            backToMainMenu();
        }
    }

    /**
     * Updates the actual menu, handling movement using arrow keys and selecting / executing menu options
     */
    protected void updateMenu() {
        if (menuOptions != null) {
            Point p = GamePanel.getGameWindow().getMousePoint();
            if (p != null && !p.equals(previousMouse)) {
                for (MenuOption option : menuOptions) {
                    option.mouseMoved(p);
                }
            }
            previousMouse = p;

            //If the key time is up (meaning an input is now allowed)
            if (keyTimer.isTimeUp()) {
                //Check if a directional key action is pressed
                if (KeyboardAction.MENU_DOWN.isDown()) {
                    //Reset the key timer
                    keyTimer.reset();
                    //Move in that direction
                    moveDirection(Direction.DOWN);
                } else if (KeyboardAction.MENU_UP.isDown()) {
                    keyTimer.reset();
                    moveDirection(Direction.UP);
                } else if (KeyboardAction.MENU_LEFT.isDown()) {
                    keyTimer.reset();
                    moveDirection(Direction.LEFT);
                } else if (KeyboardAction.MENU_RIGHT.isDown()) {
                    keyTimer.reset();
                    moveDirection(Direction.RIGHT);
                }
            }

            /*
            Checks if the key is already pressed down, and if not then executes the selected item if the key is then pressed
             */
            if (keyLocker.isActionUnlocked(KeyboardAction.MENU_ENTER) && KeyboardAction.MENU_ENTER.isDown()) {
                selectedItem.run();
            }
            keyLocker.updateActions(KeyboardAction.MENU_ENTER);
        }
    }

    /**
     * Returns the game back to the main menu through the Screen Coordinator
     */
    protected void backToMainMenu() {
        GamePanel.getScreenCoordinator().setGameState(GameState.MENU);
    }

    /**
     * Moves the menu selection in a specified direction
     *
     * @param direction Directional movement to move the selection in
     */
    private void moveDirection(Direction direction) {
        //Fallback if selected item is null
        if (selectedItem == null) {
            selectedItem = menuOptions[0];
        }

        selectedItem = selectedItem.selectNeighbor(direction);
    }

    /**
     * Delegates to the {@link #drawBackground(GraphicsHandler)}, {@link #drawMenuOptions(GraphicsHandler)}, and
     * {@link #drawDrawables(GraphicsHandler)} methods. Overriding this method allows for customizing render order, or removing some rendering
     * completely
     *
     * @param graphicsHandler current Graphics Handler to render with
     */
    @Override
    public void draw(GraphicsHandler graphicsHandler) {

        drawBackground(graphicsHandler);
        drawMenuOptions(graphicsHandler);
        drawDrawables(graphicsHandler);
    }

    /**
     * Draws the background map onto the screen
     *
     * @param graphicsHandler Current Graphics Handler
     */
    protected void drawBackground(GraphicsHandler graphicsHandler) {
        if (background != null) {
            background.draw(graphicsHandler);
        }
    }

    /**
     * Draws the Menu Options onto the screen
     *
     * @param graphicsHandler Current Graphics Handler
     */
    protected void drawMenuOptions(GraphicsHandler graphicsHandler) {
        if (menuOptions != null) {
            for (MenuOption item : menuOptions) {
                item.draw(graphicsHandler);
            }
        }
    }

    /**
     * Draws the Drawables onto the screen
     *
     * @param graphicsHandler Current Graphics Handler
     */
    protected void drawDrawables(GraphicsHandler graphicsHandler) {
        if (drawables != null) {
            for (Drawable drawable : drawables) {
                drawable.draw(graphicsHandler);
            }
        }
    }

    /**
     * Delegates the Mouse Clicked methods to each of the menu options
     *
     * @param e Mouse Event
     */
    public void mouseClicked(MouseEvent e) {
        if (menuOptions != null) {
            for (MenuOption option : menuOptions) {
                option.mouseClicked(e);
            }
        }
    }

    /**
     * Automatically sets up layouts and links via grid layouts. Will attempt to link each item to the next item in
     * that direction
     *
     * @param grid
     */
    protected void setMenuItemsAsGrid(MenuOption[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    count++;
                    //find upper neighbor
                    for (int k = i - 1; k >= 0; k--) {
                        if (j < grid[k].length && grid[k][j] != null) {
                            grid[i][j].setNeighborItem(grid[k][j], Direction.UP);
                            break;
                        }
                    }
                    //Find lower neighbor
                    for (int k = i + 1; k < grid.length; k++) {
                        if (j < grid[k].length && grid[k][j] != null) {
                            grid[i][j].setNeighborItem(grid[k][j], Direction.DOWN);
                            break;
                        }
                    }
                    //Find left neighbor
                    for (int k = j - 1; k >= 0; k--) {
                        if (grid[i][k] != null) {
                            grid[i][j].setNeighborItem(grid[i][k], Direction.LEFT);
                            break;
                        }
                    }
                    //Find right neighbor
                    for (int k = j + 1; k < grid[i].length; k++) {
                        if (grid[i][k] != null) {
                            grid[i][j].setNeighborItem(grid[i][k], Direction.RIGHT);
                            break;
                        }
                    }
                }
            }
        }

        /*
        Converts the 2d array to a single array, and sets each menu option's callback function for selecting to this object.
         */
        menuOptions = new MenuOption[count];
        for (MenuOption[] row : grid) {
            for (MenuOption menuOption : row) {
                if (menuOption != null) {
                    menuOptions[menuOptions.length - (count--)] = menuOption;
                    menuOption.setSelectFunction(this);
                }
            }
        }
    }

    protected void setMenuOptions(MenuOption... menuOptions) {
        this.menuOptions = menuOptions;
        for (MenuOption menuOption : menuOptions) {
            menuOption.setSelectFunction(this);
        }
    }

    /**
     * Updates the currently selected option to the given MenuOption
     *
     * @param menuOption Option that should now be selected.
     */
    @Override
    public void select(MenuOption menuOption) {
        selectedItem.setSelected(false);
        menuOption.setSelected(true);
        selectedItem = menuOption;
    }

    /**
     * sets the rendered background of the menu. This background will be (by default) rendered first before any other elements have been rendered
     * for each rendered frame. The map is not updated, however visual graphics are also rendered.
     *
     * @param background Menu Background to display behind all screens
     */
    protected void setBackground(Map background) {
        this.background = background;
    }

    /**
     * Sets the list of drawables to be rendered on the menu
     *
     * @param drawables List of Drawables to render
     */
    protected void setDrawables(Drawable... drawables) {
        this.drawables = drawables;
    }
}
