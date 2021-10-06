package Menu;

import Engine.GraphicsHandler;
import Engine.Screen;
import Level.Map;

public abstract class Menu extends Screen {

    protected MenuItem[] menuItems;
    private MenuItem selectedItem;

    protected Map background;

    public Menu() {
        menuItems = new MenuItem[4];
    }

    //DIRECTIONS: 0 = up, 1 = right, 2 = down, 3 = left

    @Override
    public void update() {
        
    }


    public void moveDirection(Direction direction) {
        if(selectedItem == null) {
            selectedItem = menuItems[0];
        }

        selectedItem = selectedItem.selectNeighbor(direction);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {

    }
}
