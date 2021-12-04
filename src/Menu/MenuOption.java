package Menu;

import Engine.Drawable;
import Engine.GamePanel;
import Engine.GraphicsHandler;
import Game.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuOption implements Drawable {

    private static final Font DEFAULT_MENU_FONT = new Font("Comic sans", Font.PLAIN, 30);
    private static final Color DEFAULT_COLOR = new Color(49, 207, 240);
    private static final Color SELECTED_COLOR = new Color(255, 215, 0);
    private static final Color OUTLINE_COLOR = new Color(0, 0, 0);
    private static final int OUTLINE_THICKNESS = 3;
    private final MenuOption[] neighbors;
    private final String text;
    private final int x;
    private final int y;
    private SelectableMenu selectableMenu;
    private MenuItemListener listener;
    private Font font;
    private boolean selected;
    private int width;
    private int height;

    private CloseOnExecute actionAfterExecute = CloseOnExecute.REMAINOPEN;

    public MenuOption(String text, int x, int y, MenuItemListener listener) {
        this(text, x, y);
        setListener(listener);
    }

    public MenuOption(String text, int x, int y) {
        neighbors = new MenuOption[4];
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public MenuOption(String text, int x, int y, MenuItemListener listener, CloseOnExecute closeOnExecute) {
        this(text, x, y);
        setListener(listener);
        actionAfterExecute = closeOnExecute;
    }

    public void setNeighborItem(MenuOption item, Direction direction) {
        neighbors[direction.getIndex()] = item;
        //        item.neighbors[direction.getOppositeIndex()] = this;
    }

    /**
     * Selects the specified neighbor
     *
     * @param direction Direction of neighbor
     *
     * @return
     */
    public MenuOption selectNeighbor(Direction direction) {
        MenuOption newSelection = neighbors[direction.getIndex()];
        if (newSelection == null) {
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

    public MenuItemListener getListener() {
        return listener;
    }

    public void setListener(MenuItemListener listener) {
        this.listener = listener;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (!OUTLINE_COLOR.equals(getColor())) {
            graphicsHandler.drawStringWithOutline(text, Math.round(x), Math.round(y), getFont(), getColor(), OUTLINE_COLOR, OUTLINE_THICKNESS);
        } else {
            graphicsHandler.drawString(text, Math.round(x), Math.round(y), getFont(), getColor());
        }

        if (width == 0) {
            graphicsHandler.getGraphics2D().setFont(font);
            FontMetrics metrics = graphicsHandler.getGraphics2D().getFontMetrics();
            height = metrics.getHeight() * 3;
            width = (int) (metrics.stringWidth(text) * 2.5);
        }

        //Draws the pointer
        if (selected) {
            graphicsHandler.drawFilledRectangleWithBorder(x - 30, y - 20, 20, 20, DEFAULT_COLOR, Color.black, 2);
        }
    }

    private Color getColor() {
        return selected ? SELECTED_COLOR : DEFAULT_COLOR;
    }

    public Font getFont() {
        return font != null ? font : DEFAULT_MENU_FONT;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    // this can be called instead of regular draw to have the text drop to the next line in graphics space on a new line character
    public void drawWithParsedNewLines(GraphicsHandler graphicsHandler) {
        int drawLocationY = Math.round(this.y);
        for (String line : text.split("\n")) {
            if (!OUTLINE_COLOR.equals(getColor())) {
                graphicsHandler.drawStringWithOutline(line, Math.round(x), drawLocationY, getFont(), getColor(), OUTLINE_COLOR, OUTLINE_THICKNESS);
            } else {
                graphicsHandler.drawString(line, Math.round(x), drawLocationY, getFont(), getColor());
            }
            drawLocationY += getFont().getSize();
        }
    }

    public void mouseMoved(Point p) {
        if (selected != contains(p) && selectableMenu != null) {
            selectableMenu.select(this);
        }
    }

    public boolean contains(Point point) {
        return point != null && point.x > x && point.y > y && point.x < x + width && point.y < y + height;
    }

    public void mouseClicked(MouseEvent e) {
        if (contains(e.getPoint())) {
            execute();
        }
    }

    public void execute() {
        if (listener != null) {
            listener.event();
        }
        // allows a menu option the ability to close after selecting them
        if (actionAfterExecute == CloseOnExecute.CLOSE) {
            GamePanel.getScreenCoordinator().setGameState(GameState.MENU);
        }
    }

    public void setSelectFunction(SelectableMenu function) {
        this.selectableMenu = function;
    }

    public enum CloseOnExecute {
        REMAINOPEN,
        CLOSE
    }
}
