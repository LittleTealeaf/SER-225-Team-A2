package Engine;

public class Vector {
    private double x,y;


    public Vector() {
        this(0,0);
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void addX(double x) {
        this.x += x;
    }

    public void addY(double y) {
        this.y += y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void applyVelocity(Vector vector) {
        x += vector.x;
        y += vector.y;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

}
