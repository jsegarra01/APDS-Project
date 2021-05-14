package model.trees.r;

public class RRectangle implements RNode {

    private RContainer child;

    // Top-left corner
    private Point point;
    private float width, height;

    public RRectangle() {
        child = null;
    }

    public RRectangle(Point point) {
        child = null;
        this.point = point;
    }

    public RRectangle(Point pointA, Point pointB) {
        child = null;
        this.point = pointA;
        this.width = pointB.x - pointA.x;
        this.height = pointA.y - pointB.y;
    }

    public RContainer getChild() {
        return child;
    }

    public void setChild(RContainer child) {
        this.child = child;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Point getBottomRight() {
        return new Point(point.x + width, point.y - height);
    }

    public Point getCentre() {
        return new Point(point.x + (width / 2), point.y - (height / 2));
    }

    public float getArea() {
        return width * height;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public boolean isContained(RRectangle rectangle) {
        return rectangle.containsX(this.point)
                && rectangle.containsY(this.point)
                && rectangle.containsX(this.getBottomRight())
                && rectangle.containsY(this.getBottomRight());
    }

    @Override
    public float getDistance(Point point) {
        return this.getCentre().getDistance(point);
    }

    // ---------------------------------------------
    //              |                 |
    //    case 0    |     case 1      |    case 2
    //              |                 |
    // -  -  -  -  - ----------------- -  -  -  -  -
    //              |                 |
    //    case 3    |    rectangle    |    case 4
    //              |                 |
    // -  -  -  -  - ----------------- -  -  -  -  -
    //              |                 |
    //    case 5    |     case 6      |    case 7
    //              |                 |
    // ---------------------------------------------

    public float getMinDistance(Point point) {
        // Point is to the left of the rectangle (case 0, 3, 5)
        if (point.x <= this.point.x) {
            // Point is above the rectangle (case 0)
            if (point.y >= this.point.y) {
                return point.getDistance(this.point);
            }
            // Point is bellow the rectangle (case 5)
            else if (point.y <= this.getBottomRight().y) {
                return point.getDistance(new Point(this.point.x, this.getBottomRight().y));
            }
            // Point is in between the rectangle (case 3)
            else {
                return this.point.x - point.x;
            }
        }
        // Point is to the right of the rectangle (case 2, 4, 7)
        else if (point.x >= this.getBottomRight().x) {
            // Point is above the rectangle (case 2)
            if (point.y >= this.point.y) {
                return point.getDistance(new Point(this.getBottomRight().x, this.point.y));
            }
            // Point is bellow the rectangle (case 7)
            else if (point.y <= this.getBottomRight().y) {
                return point.getDistance(this.getBottomRight());
            }
            // Point is in between the rectangle (case 4)
            else {
                return point.x - this.getBottomRight().x;
            }
        }
        // Point is in between the rectangle (case 1, 6)
        else {
            // Point is above the rectangle (case 1)
            if (point.y >= this.point.y) {
                return point.y - this.point.y;
            }
            // Point is bellow the rectangle (case 6)
            else {
                return this.getBottomRight().y - point.y;
            }
        }
    }

    @Override
    public float getArea(RRectangle rectangle) {
        return newWidth(rectangle.getPoint(), rectangle.getBottomRight()) *
                newHeight(rectangle.getPoint(), rectangle.getBottomRight());
    }

    public boolean isIntersecting(RRectangle rectangle) {
        Point topLeft = rectangle.getPoint();
        Point bottomRight = rectangle.getBottomRight();

        // Check if one of the rectangles is
        if (this.point.x >= bottomRight.x || topLeft.x >= this.getBottomRight().x){
            return false;
        }
        if (this.point.y <= bottomRight.y || topLeft.y <= this.getBottomRight().y) {
            return false;
        }

        return true;
    }

    public void updateSize(RNode node) {
        if (node instanceof RPoint) {
            width = newWidth(node.getPoint(), node.getPoint());
            height = newHeight(node.getPoint(), node.getPoint());
        }
        else {
            width = newWidth(node.getPoint(), ((RRectangle) node).getBottomRight());
            height = newHeight(node.getPoint(), ((RRectangle) node).getBottomRight());
        }

        point = newTopLeftPoint(node.getPoint());
    }

    // --------------------- RECTANGLE METHODS ---------------------

    public Point newTopLeftPoint(Point tlPoint) {
        float xMin = Math.min(this.point.x, tlPoint.x);
        float yMax = Math.max(this.point.y, tlPoint.y);

        return new Point(xMin, yMax);
    }

    public float newWidth(Point tlPoint, Point brPoint) {
        float xMax = Math.max(this.getBottomRight().x, brPoint.x);
        float xMin = Math.min(this.point.x, tlPoint.x);

        return xMax - xMin;
    }

    public float newHeight(Point tlPoint, Point brPoint) {
        float yMax = Math.max(this.point.y, tlPoint.y);
        float yMin = Math.min(this.getBottomRight().y, brPoint.y);

        return yMax - yMin;
    }

    public boolean containsX(Point point) {
        return (this.point.x <= point.x && point.x <= (this.point.x + width));
    }

    public boolean containsY(Point point) {
        return (this.point.y >= point.y && point.y >= (this.point.y - height));
    }
}