package model.trees.r;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class RTree {

    // TODO: place R in BR

    public static final int ORDER = 3;

    private RContainer root;

    public RTree() {
        root = new RContainer();
    }

    public RContainer getRoot() {
        return root;
    }

    public void insert(RNode node) {
        insertRecursive(root, node);
    }

    private void insertRecursive(RContainer container, RNode newNode) {
        if (container.isLeaf()) {
            container.insertNode(newNode);

            if (container.isOverload()) split(container);
        }
        else {
            float newArea;
            float minArea = Float.MAX_VALUE;
            RRectangle successor = null;

            // If it is not the leaf, node only contain rectangles
            for (RNode curNode : container.getNodes()) {
                RRectangle curRectangle = (RRectangle) curNode;

                // The rectangle contains the point/rectangle
                if (newNode.isContained(curRectangle)) {
                    successor = curRectangle;
                    break;
                }
                else {
                    // Select the rectangle that grows the least when inserting new point/rectangle
                    newArea = newNode.getArea(curRectangle);
                    if (newArea < minArea) {
                        minArea = newArea;
                        successor = curRectangle;
                    }
                }
            }

            if (successor != null) insertRecursive(successor.getChild(), newNode);
        }
    }

    private void split(RContainer container) {
        RNode node1 = null, node2 = null;
        float maxDist = Float.MIN_VALUE;
        float dist;

        // Pick two most distant nodes
        for (RNode nodeA : container.getNodes()) {
            for (RNode nodeB : container.getNodes()) {
                dist = nodeA.getDistance(nodeB.getPoint());

                if (dist > maxDist) {
                    maxDist = dist;
                    node1 = nodeA;
                    node2 = nodeB;
                }
            }
        }

        // Initialize the new rectangles
        // Parent node that holds the two new rectangles
        RContainer parentContainer;

        if (container.getParent() != null) {
            parentContainer = container.getParentContainer();
            // If parent was a rectangle, remove it
            parentContainer.removeNode(container.getParent());
        }
        else {
            parentContainer = new RContainer();
        }
        // Rectangles
        RRectangle rectangle1 = initParent(parentContainer, node1);
        RRectangle rectangle2 = initParent(parentContainer, node2);

        // Add new rectangle to container
        parentContainer.insertNode(rectangle1);
        parentContainer.insertNode(rectangle2);
        parentContainer.setLeaf();

        if (parentContainer.getParent() == null) root = parentContainer;

        // Reinsert the rest of the children
        for (RNode node : container.getNodes()) {
            if (!node.equals(node1) && !node.equals(node2)) {
                insertRecursive(root, node);
            }
        }

        // If parent container overflows redo process
        if (parentContainer.isOverload()) split(parentContainer);
    }

    private RRectangle initParent(RContainer newContainer, RNode node) {
        RRectangle rectangle = new RRectangle(new Point(node.getPoint()));
        RContainer childContainer = new RContainer();
        // Parent relation
        childContainer.setParent(rectangle);
        childContainer.setParentContainer(newContainer);
        childContainer.insertNode(node);
        childContainer.setLeaf();
        // Children relation
        rectangle.setChild(childContainer);

        return rectangle;
    }

    public void searchByArea(Point point1, Point point2) {
        RRectangle rectangle = new RRectangle(point1, point2);
        searchRecursive(root, rectangle);
    }

    private void searchRecursive(RContainer container, RRectangle rectangle) {
        for (RNode node : container.getNodes()){
            if (node instanceof RRectangle) {
                RRectangle rRectangle = (RRectangle) node;

                if (rRectangle.isIntersecting(rectangle.getPoint(), rectangle.getBottomRight())) {
                    searchRecursive(rRectangle.getChild(), rectangle);
                }
            }
            else {
                if (node.isContained(rectangle)) {
                    System.out.println(((RPoint) node).getName());
                }
            }
        }
    }
}
