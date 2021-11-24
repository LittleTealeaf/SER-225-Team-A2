package Engine;

import Level.Map;

import java.awt.event.MouseEvent;

// Base Screen class
// This game engine runs off the idea of "screens", which are classes that contain their own update/draw methods for a particular piece of the game
// For example, there may be a "MenuScreen" or a "PlayGameScreen"
public abstract class Screen implements Updatable {
    public abstract void initialize();
    public abstract void update();
    public abstract void draw(GraphicsHandler graphicsHandler);
    public abstract void mouseClicked(MouseEvent e);
    private Map background;
    protected void setBackground(Map background) {
        this.background = background;
    }
}
