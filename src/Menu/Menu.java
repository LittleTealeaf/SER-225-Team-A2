package Menu;

import Engine.*;
import Game.GameState;
import Level.Map;
import Utils.Stopwatch;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Menu extends Screen {

    private MenuOption[] menuOptions;
    private MenuOption selectedItem;
    private Drawable[] drawables;

    private Map background;
    private final Stopwatch keyTimer = new Stopwatch();

    private boolean selectionDown;

    private Point previousMouse;

    @Override
    public void initialize() {
        if(menuOptions != null) {
            selectedItem = menuOptions[0];
            selectedItem.setSelected(true);
        }
        if(background != null) {
            background.setAdjustCamera(false);
        }
        keyTimer.setWaitTime(200);
        selectionDown = true;
    }

    @Override
    public void update() {
        updateBackground();
        updateMenuEscape();
        updateMenu();
    }

    protected void updateMenuEscape() {
        if(KeyboardAdapter.MENU_ESCAPE.isDown()) {
            GamePanel.getScreenCoordinator().setGameState(GameState.MENU);
        }
    }

    protected void updateBackground() {
        if(background != null) {
            background.update(null);
        }
    }

    protected void updateMenu() {
        if(menuOptions != null) {
            Point p = GamePanel.getGameWindow().getMousePoint();
            if(p != null && !p.equals(previousMouse)) {
                for(MenuOption option : menuOptions) {
                    option.mouseMoved(p);
                }
            }
            previousMouse = p;


            //Move Direction
            if(KeyboardAdapter.MENU_DOWN.isDown() && keyTimer.isTimeUp()) {
                keyTimer.reset();
                moveDirection(Direction.DOWN);
            } else if(KeyboardAdapter.MENU_UP.isDown() && keyTimer.isTimeUp()) {
                keyTimer.reset();
                moveDirection(Direction.UP);
            } else if(KeyboardAdapter.MENU_LEFT.isDown() && keyTimer.isTimeUp()) {
                keyTimer.reset();
                moveDirection(Direction.LEFT);

            } else if(KeyboardAdapter.MENU_RIGHT.isDown() && keyTimer.isTimeUp()) {
                keyTimer.reset();
                moveDirection(Direction.RIGHT);
            }

            //Menu Enter
            if(!KeyboardAdapter.MENU_ENTER.isDown()) {
                selectionDown = false;
            } else if(!selectionDown && KeyboardAdapter.MENU_ENTER.isDown()) {
                selectedItem.execute();
            }
        }
    }

    /**
     * Automatically sets up layouts and links via grid layouts. Will attempt to link each item to the next item in
     * that direction
     * @param grid
     */
    protected void setMenuItemsAsGrid(MenuOption[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null) {
                    count++;
                    //find upper neighbor
                    for(int k = i - 1; k >= 0; k--) {
                        if(j < grid[k].length && grid[k][j] != null) {
                            grid[i][j].setNeighborItem(grid[k][j],Direction.UP);
                            break;
                        }
                    }
                    //Find lower neighbor
                    for(int k = i + 1; k < grid.length; k++) {
                        if(j < grid[k].length && grid[k][j] != null) {
                            grid[i][j].setNeighborItem(grid[k][j],Direction.DOWN);
                            break;
                        }
                    }
                    //Find left neighbor
                    for(int k = j - 1; k >= 0; k--) {
                        if(grid[i][k] != null) {
                            grid[i][j].setNeighborItem(grid[i][k],Direction.LEFT);
                            break;
                        }
                    }
                    //Find right neighbor
                    for(int k = j + 1; k < grid[i].length; k++) {
                        if(grid[i][k] != null) {
                            grid[i][j].setNeighborItem(grid[i][k],Direction.RIGHT);
                            break;
                        }
                    }
                }
            }
        }

        SelectFunction function = (newSelection) -> {
            selectedItem.setSelected(false);
            newSelection.setSelected(true);
            selectedItem = newSelection;
        };

        menuOptions = new MenuOption[count];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null) {
                    menuOptions[menuOptions.length - (count--)] = grid[i][j];
                    grid[i][j].setSelectFunction(function);
                }
            }
        }
    }


    private void moveDirection(Direction direction) {
        //Fallback if selected item is null
        if(selectedItem == null) {
            selectedItem = menuOptions[0];
        }

        selectedItem = selectedItem.selectNeighbor(direction);
    }

    protected void setBackground(Map background) {
        this.background = background;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {

        drawBackground(graphicsHandler);
        drawMenuOptions(graphicsHandler);
        drawDrawables(graphicsHandler);
    }

    protected void drawBackground(GraphicsHandler graphicsHandler) {
        if(background != null) {
            background.draw(graphicsHandler);
        }
    }

    protected void drawMenuOptions(GraphicsHandler graphicsHandler) {
        if(menuOptions != null) {
            for(MenuOption item : menuOptions) {
                item.draw(graphicsHandler);
            }
        }
    }

    protected void drawDrawables(GraphicsHandler graphicsHandler) {
        if(drawables != null) {
            for(Drawable drawable : drawables) {
                drawable.draw(graphicsHandler);
            }
        }
    }

    protected void setDrawables(Drawable[] drawables) {
        this.drawables = drawables;
    }

    public void mouseClicked(MouseEvent e) {
        for(MenuOption option : menuOptions) {
            option.mouseClicked(e);
        }
    }

    public interface SelectFunction {
        void select(MenuOption item);
    }
}
