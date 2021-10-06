package Menu;

import Engine.*;
import Game.ScreenCoordinator;
import Level.Map;
import Utils.Stopwatch;

public abstract class Menu extends Screen {

    protected MenuItem[] menuItems;
    private MenuItem selectedItem;
    protected ScreenCoordinator screenCoordinator;

    private Map background;

    private KeyLocker keyLocker = new KeyLocker();
    private Stopwatch keyTimer = new Stopwatch();


    public Menu(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    //DIRECTIONS: 0 = up, 1 = right, 2 = down, 3 = left

    @Override
    public void initialize() {
        selectedItem = menuItems[0];
        selectedItem.setSelected(true);
        if(background != null) {
            background.setAdjustCamera(false);
        }
        keyLocker.lockKey(Key.SPACE,Key.ENTER);
        keyTimer.setWaitTime(200);

    }

    @Override
    public void update() {
        background.update(null);

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
    }

    /**
     * Automatically sets up layouts and links via grid layouts. Will attempt to link each item to the next item in
     * that direction
     * @param grid
     */
    protected void setMenuItemsAsGrid(MenuItem[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null) {
                    count++;
                    //find upper neighbor
                    for(int k = i - 1; k >= 0; k--) {
                        if(grid[k][j] != null) {
                            grid[i][j].setNeighborItem(grid[k][j],Direction.UP);
                            break;
                        }
                    }
                    //Find lower neighbor
                    for(int k = i + 1; k < grid.length; k++) {
                        if(grid[k][j] != null) {
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
        menuItems = new MenuItem[count];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null) {
                    menuItems[menuItems.length - (count--)] = grid[i][j];
                }
            }
        }
    }


    public void moveDirection(Direction direction) {
        if(selectedItem == null) {
            selectedItem = menuItems[0];
        }

        selectedItem = selectedItem.selectNeighbor(direction);
    }

    protected void setBackground(Map background) {
        this.background = background;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if(background != null) {
            background.draw(graphicsHandler);
        }
        for(MenuItem item : menuItems) {
            item.draw(graphicsHandler);
        }
    }
}
