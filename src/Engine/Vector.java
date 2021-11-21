package Engine;

import Utils.Point;

public class Vector {
    private float x,y;


    public Vector() {
        this(0.0f,0.0f);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void addX(float x) {
        this.x += x;
    }

    public void addY(float y) {
        this.y += y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getMagnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector set(Vector location) {
        this.x = location.x;
        this.y = location.y;
        return this;
    }

    public Vector getDivided(float divisor) {
        return new Vector(x / divisor, y / divisor);
    }

    public Vector getMultiplied(float factor) {
        return new Vector(x * factor, y * factor);
    }

    public Vector getUnit() {
        return getDivided(Math.abs(getMagnitude()));
    }

    public Vector add(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vector multiply(float factor) {
        this.x *= factor;
        this.y *= factor;
        return this;
    }

    public Vector divide(float divisor) {
        this.x /= divisor;
        this.y /= divisor;
        return this;
    }

    public Vector subtract(float magnitude) {
        return add(-magnitude);
    }

    public Vector subtract(Vector vector) {
        return add(vector.getNegative());
    }

    public Vector getSubtracted(Vector vector) {
        return clone().subtract(vector);
    }

    public Vector add(float magnitude) {
        add(getUnit().multiply(magnitude));
        return this;
    }

    public Vector clone() {
        return new Vector(x,y);
    }

    public Vector getGreatestDirection() {
        if(x > y) {
            return new Vector(x,0);
        } else if(y > x) {
            return new Vector(0,y);
        } else {
            return clone();
        }
    }

    public Vector getAbsolute() {
        return new Vector(Math.abs(x), Math.abs(y));
    }

    public Vector getNegative() {
        return clone().multiply(-1);
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
