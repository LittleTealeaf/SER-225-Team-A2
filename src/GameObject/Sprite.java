package GameObject;

import Engine.GraphicsHandler;
import Engine.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Thomas Kwashnak (re-wrote)
 * @author Alex Thimineur (original)
 */
public class Sprite extends Rectangle {

    protected BufferedImage image;
    protected ImageEffect imageEffect;
    protected Rectangle bounds, scaledBounds;

    public Sprite(BufferedImage image, float scale) {
        this(image, scale, ImageEffect.NONE);
    }

    public Sprite(BufferedImage image, float scale, ImageEffect imageEffect) {
        this(image, 0, 0, scale, imageEffect);
    }

    public Sprite(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        this(image, new Vector(x, y), scale, imageEffect);
    }

    public Sprite(BufferedImage image, Vector location, float scale, ImageEffect imageEffect) {
        super(location, Vector.convertImageDimensions(image), scale);
        this.image = image;
        this.imageEffect = imageEffect;
        bounds = new Rectangle(new Vector(0,0),dimension.clone());
        scaledBounds = scaleBounds(bounds);
    }

    public BufferedImage getImage() {
        return image;
    }

    public ImageEffect getImageEffect() {
        return imageEffect;
    }

    /*
    Overrides the Rectangle to use bounds if a custom bounds was specified
     */
    @Override
    public Vector getMinLocation() {
        return bounds.location;
    }

    /*
    Overrides the Rectangle to use bounds if a custom bounds was specified
     */
    @Override
    public Vector getMaxLocation() {
        return bounds.location.getAdd(dimension);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(
                image, Math.round(getX1()), Math.round(getY1()), Math.round(getScaledWidth()), Math.round(getScaledHeight()), imageEffect);
    }

    public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        Rectangle scaledBounds = getScaledBounds();
        scaledBounds.setColor(color);
        scaledBounds.draw(graphicsHandler);
    }

    public Rectangle getBounds() {
        return new Rectangle(location.getAdd(bounds.location),bounds.dimension,bounds.scale);
    }

    public Rectangle getScaledBounds() {
        return new Rectangle(location.getAdd(scaledBounds.location),scaledBounds.dimension);
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
        scaledBounds = scaleBounds(bounds);
    }

    public boolean intersects(Intersectable other) {
        return getScaledBounds().intersects(other);
    }

    /**
     * Returns the scaled bounds of the given bounds
     * @param bounds Bounds to scale to scale = 1
     * @return Scaled bounds where both the location and dimension are scaled
     */
    private Rectangle scaleBounds(Rectangle bounds) {
        return new Rectangle(bounds.location.getMultiplied(bounds.scale), bounds.dimension.getMultiplied(bounds.scale));
    }

}
