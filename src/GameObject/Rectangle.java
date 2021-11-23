package GameObject;

import Engine.Drawable;
import Engine.GraphicsHandler;
import Engine.Vector;

import java.awt.*;

/**
 * @author Thomas Kwashnak
 */
public class Rectangle implements Drawable, Intersectable, Overlappable {

    private static final int BORDER_THICKNESS = 0;

    protected Vector location, dimension;
    protected float scale;
    protected Color color, borderColor;

    public Rectangle(float x, float y, float width, float height) {
        this(new Vector(x,y), new Vector(width,height));
    }

    public Rectangle(Vector location, Vector dimension) {
        this(location,dimension,1);
    }

    public Rectangle(float x, float y, float width, float height, float scale) {
        this(new Vector(x,y), new Vector(width,height), scale);
    }

    public Rectangle(Vector location, Vector dimension, float scale) {
        this.location = location;
        this.dimension = dimension;
        this.scale = scale;
    }

    public float getX1() {
        return location.getX();
    }

    public float getX2() {
        return location.getX() + dimension.getX();
    }

    public float getScaledX2() {
        return location.getX() + (dimension.getX() * scale);
    }

    public float getY1() {
        return location.getY();
    }

    public float getY2() {
        return location.getY() + dimension.getY();
    }

    public float getScaledY2() {
        return location.getY() + (dimension.getY() * scale);
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public void move(Vector displacement) {
        location.add(displacement);
    }

    public Vector getDimension() {
        return dimension;
    }

    public Vector getScaledDimension() {
        return dimension.getMultiplied(scale);
    }

    public Vector getLocation() {
        return location;
    }

    public float getScaledWidth() {
        return dimension.getX() * scale;
    }

    public float getScaledHeight() {
        return dimension.getY() * scale;
    }

    public Vector getMinLocation() {
        return location;
    }

    public Vector getMaxLocation() {
        return location.getAdd(dimension);
    }

    public void update() {}

    public boolean intersects(Intersectable other) {
        Vector min = getMinLocation(), max = getMaxLocation(), minOther = other.getMinLocation(), maxOther = other.getMaxLocation();
        return (min.getX() < maxOther.getX()) && (max.getX() > minOther.getX()) && (min.getY() < maxOther.getY()) && (max.getY() > minOther.getY());
    }

    public boolean overlaps(Overlappable other) {
        Vector min = getMinLocation(), max = getMaxLocation(), minOther = other.getMinLocation(), maxOther = other.getMaxLocation();
        return (min.getX() <= maxOther.getX()) && (max.getX() >= minOther.getX()) && (min.getY() <= maxOther.getY()) && (max.getY() >= minOther.getY());
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(Math.round(getX1()), Math.round(getY1()), Math.round(getScaledWidth()), Math.round(getScaledHeight()), color);
        if (borderColor != null && !borderColor.equals(color)) {
            graphicsHandler.drawRectangle(Math.round(getX1()), Math.round(getY1()), Math.round(getScaledWidth()), Math.round(getScaledHeight()), borderColor, BORDER_THICKNESS);
        }
    }

    public String toString() {
        return String.format("Rectangle: location = %s, dimensions = %s",location.toString(),dimension.toString());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public float getWidth() {
        return dimension.getX();
    }

    public float getHeight() {
        return dimension.getY();
    }

    public void setWidth(float width) {
        dimension.setX(width);
    }

    public void setHeight(float height) {
        dimension.setY(height);
    }
}
