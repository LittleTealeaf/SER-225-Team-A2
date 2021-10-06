package Menu;

import Engine.GraphicsHandler;

import java.awt.*;

public class MenuItem {

    private static final Font MENU_FONT = new Font("Comic sans",Font.PLAIN,30);
    private static final Color DEFAULT_COLOR = new Color(49, 207, 240);
    private static final Color SELECTED_COLOR = new Color(255, 215, 0);
    private static final Color OUTLINE_COLOR = new Color(0, 0, 0);
    private static final int OUTLINE_THICKNESS = 3;

    private MenuItem[] neighbors;

    private MenuItemListener listener;

    private String text;

    private boolean selected;
    private int x,y;

    public MenuItem(String text, int x, int y) {
        neighbors = new MenuItem[4];
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public void setNeighborItem(MenuItem item, Direction direction) {
        neighbors[direction.getIndex()] = item;
    }

    /**
     * Selects the specified neighbor
     * @param direction Direction of neighbor
     * @return
     */
    public MenuItem selectNeighbor(Direction direction) {
        MenuItem newSelection = neighbors[direction.getIndex()];
        if(newSelection == null) {
            return this;
        } else {
            setSelected(false);
            newSelection.setSelected(true);
            return newSelection;
        }
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private Color getColor() {
        return selected ? SELECTED_COLOR : DEFAULT_COLOR;
    }

    public MenuItemListener getListener() {
        return listener;
    }

    public void setListener(MenuItemListener listener) {
        this.listener = listener;
    }

    public void select() {
        if(listener != null) {
            listener.event();
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (!OUTLINE_COLOR.equals(getColor())) {
            graphicsHandler.drawStringWithOutline(text, Math.round(x), Math.round(y), MENU_FONT, getColor(), OUTLINE_COLOR, OUTLINE_THICKNESS);
        } else {
            graphicsHandler.drawString(text, Math.round(x), Math.round(y), MENU_FONT, getColor());
        }

        //Draws the pointer
        if(selected) {
            graphicsHandler.drawFilledRectangleWithBorder(x - 30, y - 20, 20, 20, new Color(49, 207, 240), Color.black, 2);
        }
    }

    // this can be called instead of regular draw to have the text drop to the next line in graphics space on a new line character
    public void drawWithParsedNewLines(GraphicsHandler graphicsHandler) {
        int drawLocationY = Math.round(this.y);
        for (String line: text.split("\n")) {
            if (!OUTLINE_COLOR.equals(getColor())) {
                graphicsHandler.drawStringWithOutline(line, Math.round(x), drawLocationY, MENU_FONT, getColor(), OUTLINE_COLOR, OUTLINE_THICKNESS);
            } else {
                graphicsHandler.drawString(line, Math.round(x), drawLocationY, MENU_FONT, getColor());
            }
            drawLocationY += MENU_FONT.getSize();
        }
    }
}
