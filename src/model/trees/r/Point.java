package model.trees.r;

public class Point {

    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this(point.x, point.y);
    }

    public float getDistance(Point point) {
        double xDif = point.x - this.x;
        double yDif = point.y - this.y;

        xDif = Math.pow(xDif, 2);
        yDif = Math.pow(yDif, 2);

        return (float) Math.sqrt(xDif + yDif);
    }
}
