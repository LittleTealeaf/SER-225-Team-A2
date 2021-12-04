package Menu;

import Engine.Drawable;
import Engine.GamePanel;
import Engine.GraphicsHandler;
import Game.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This depicts a single MenuOption, its functionality, and the ability to handle the selecting process. A MenuOption is described by the following
 * attributes:
 * <ul>
 *     <li><b>Text:</b> Basically, the text that will be rendered. This will be rendered at size 30 font, and rendered in the color depending on
 *     whether it has been selected or is not selected.</li>
 *     <li><b>Location: </b>The screen-space location that the text is rendered on. Remember that the top left corner is (0,0), and that the text
 *     position is described as the bottom left corner, contrary to many other objects that are depicted at the top left corner.</li>
 *     <li><b>Neighbors:</b> The core element that makes a whole menu work. This lists the directional neighbors of the MenuOption. In short, if
 *     the user wishes to go up by pressing W or the Up Arrow, the currently selected Menu Option will check it's neighbors, and if it has a
 *     neighbor in the up slot, it will mark itself as unselected, and set that neighbor as the selected neighbor. Neighbors can be set using the
 *     {@link #setNeighborItem(MenuOption, Direction)} method. The {@link Direction} enum contains the list of directions, and their proportional
 *     index of used in the array in this class.</li>
 *     <li><b>Runnable Function:</b> This is simply a {@link Runnable} anonymous class, where the {@link Runnable#run()} method is called
 *     whenever the option is executed (This class also implements {@link Runnable}, and when {@link #run()} is called, it simply redirects and
 *     calls the run method of the set function)<br>Setting a runnable function is not necessary, and will not break if one is not set. Do not
 *     set a MenuOption's runnable to itself.</li>
 * </ul>
 * Examples of MenuOptions can be found in many of the classes in the Screens module (folder). MenuOptions require the use of extending the
 * {@link Menu} class in order to properly function
 *
 * @author Thomas Kwashnak
 * @see Menu
 */
public class MenuOption implements Drawable, Runnable {

    private static final Font DEFAULT_MENU_FONT;
    private static final Color OUTLINE_COLOR, SELECTED_COLOR, DEFAULT_COLOR;
    private static final int OUTLINE_THICKNESS;

    static {
        DEFAULT_MENU_FONT = new Font("Comic sans", Font.PLAIN, 30);
        DEFAULT_COLOR = new Color(49, 207, 240);
        OUTLINE_COLOR = new Color(0, 0, 0);
        SELECTED_COLOR = new Color(255, 215, 0);
        OUTLINE_THICKNESS = 3;
    }

    private final MenuOption[] neighbors;
    private final String text;
    private final int x;
    private final int y;
    private SelectableMenu selectableMenu;
    private Runnable function;
    private Font font;
    private boolean selected;
    private int width;
    private int height;

    private CloseOnExecute actionAfterExecute = CloseOnExecute.REMAIN_OPEN;

    public MenuOption(String text, int x, int y, Runnable function) {
        this(text, x, y);
        setFunction(function);
    }

    public MenuOption(String text, int x, int y) {
        neighbors = new MenuOption[4];
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public MenuOption(String text, int x, int y, Runnable function, CloseOnExecute closeOnExecute) {
        this(text, x, y);
        setFunction(function);
        actionAfterExecute = closeOnExecute;
    }

    public void setNeighborItem(MenuOption item, Direction direction) {
        neighbors[direction.getIndex()] = item;
    }

    /**
     * Selects the specified neighbor
     *
     * @param direction Direction of neighbor
     *
     * @return MenuOption neighbor
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

    public Runnable getFunction() {
        return function;
    }

    /**
     * Do not set a MenuOptions' runnable to itself, else face the wrath of endless recursion
     *
     * @param function Function to call whenever the MenuOption is called
     */
    public void setFunction(Runnable function) {
        this.function = function;
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
            run();
        }
    }

    @Override
    public void run() {
        if (function != null) {
            function.run();
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
        REMAIN_OPEN,
        CLOSE
    }
}
