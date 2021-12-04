package Engine;

/**
 * Declares any object that has a drawing method
 */
public interface Drawable {

    /**
     * Draws the object onto the screen using the graphics handler
     *
     * @param graphicsHandler the currently used graphics handler for that frame
     */
    void draw(GraphicsHandler graphicsHandler);
}
