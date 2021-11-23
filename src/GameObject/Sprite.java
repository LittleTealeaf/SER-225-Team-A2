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
    protected Rectangle bounds;

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
        Rectangle scaledBounds = getBounds().getScaled();
        scaledBounds.setColor(color);
        scaledBounds.draw(graphicsHandler);
    }

    public Rectangle getBounds() {
        return new Rectangle(location.getAdd(bounds.location),bounds.dimension,bounds.scale);
    }

    public Rectangle getScaledBounds() {
        return new Rectangle(location.getAdd(bounds.location.getMultiplied(bounds.scale)),bounds.dimension.getMultiplied(bounds.scale));
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
