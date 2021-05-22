package model.trees.r;

public interface RNode {

    Point getPoint();

    boolean isContained(RRectangle rectangle);

    float getDistance(Point point);

    float getArea(RRectangle rectangle);
}
