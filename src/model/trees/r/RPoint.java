package model.trees.r;

public class RPoint implements RNode {

    private String name;
    private Point point;

    public RPoint(String name, Point point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public boolean isContained(RRectangle rectangle) {
        return rectangle.containsX(point) && rectangle.containsY(point);
    }

    @Override
    public float getDistance(Point point) {
        return this.point.getDistance(point);
    }

    @Override
    public float getArea(RRectangle rectangle) {
        float xMax = Math.max(this.point.x, rectangle.getBottomRight().x);
        float xMin = Math.min(this.point.x, rectangle.getPoint().x);
        float yMax = Math.max(this.point.y, rectangle.getPoint().y);
        float yMin = Math.min(this.point.y, rectangle.getBottomRight().y);

        return (xMax - xMin) * (yMax - yMin) ;
    }

    /*
    @Override
    public float getArea(RRectangle rectangle) {
        float width;
        float height;

        // Get width
        if (rectangle.containsX(point)) {
            width = rectangle.getWidth();
        }
        else {
            float rectangleX = rectangle.getPoint().x;

            width = (rectangleX > point.x) ? (rectangleX - point.x) + rectangle.getWidth() : point.x - rectangleX;
        }

        // Get height
        if (rectangle.containsY(point)) {
            height = rectangle.getHeight();
        }
        else {
            float rectangleY = rectangle.getPoint().y;

            height = (rectangleY < point.y) ? (point.y - rectangleY) + rectangle.getHeight() : rectangleY - point.y;
        }

        return width * height;
    }
    */

    @Override
    public String toString() {
        return name + " " + point.toString();
    }
}
