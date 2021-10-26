package Engine;

import java.awt.event.MouseEvent;

/*
 * Default Screen that does nothing
 * Its existence is really just to prevent null pointers from occurring if no Screen is set somewhere
 * Think of it as the equivalent as setting a String to "" instead of just leaving it as null
 */
public class DefaultScreen extends Screen {
    public DefaultScreen() { }

    @Override
    public void initialize() { }

    @Override
    public void update() { }

    @Override
    public void draw(GraphicsHandler graphicsHandler) { }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

}
