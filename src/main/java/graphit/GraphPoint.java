package graphit;

public class GraphPoint {

    public double x;
    public double y;

    GraphPoint(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public GraphPoint modify(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public java.awt.Point toPoint() {
        return new java.awt.Point((int) x, (int) y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + " ]";
    }

}