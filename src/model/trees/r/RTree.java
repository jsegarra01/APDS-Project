package model.trees.r;

import java.util.ArrayList;
import java.util.List;

public class RTree {

    public static final int ORDER = 3;

    private RContainer root;
    private List<String> searchResult;

    public RTree() {
        root = new RContainer();
    }

    public RContainer getRoot() {
        return root;
    }

    public int getResultSize() {
        return  searchResult.size();
    }

    public List<String> getSearchResult() {
        return searchResult;
    }

    // -------------------------------- INSERT --------------------------------

    public void insert(RNode node) {
        insertRecursive(root, node);
    }

    private void insertRecursive(RContainer container, RNode newNode) {
        if (container.isLeaf()) {
            container.insertNode(newNode);

            if (container.isOverload()) split(container);
        }
        else {
            RRectangle successor = getMinGrowthRectangle(newNode, container);
            if (successor != null) insertRecursive(successor.getChild(), newNode);
        }
    }

    private RRectangle getMinGrowthRectangle(RNode node, RContainer container) {
        float newArea;
        float minArea = Float.MAX_VALUE;
        RRectangle successor = null;

        // If it is not the leaf, node only contain rectangles
        for (RNode curNode : container.getNodes()) {
            RRectangle curRectangle = (RRectangle) curNode;

            // The rectangle contains the point/rectangle
            if (node.isContained(curRectangle)) {
                successor = curRectangle;
                break;
            }
            else {
                // Select the rectangle that grows the least when inserting new point/rectangle
                newArea = node.getArea(curRectangle);
                if (newArea < minArea) {
                    minArea = newArea;
                    successor = curRectangle;
                }
            }
        }

        return successor;
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
            root = parentContainer;
        }
        // Rectangles
        RRectangle rectangle1 = initParent(parentContainer, node1);
        RRectangle rectangle2 = initParent(parentContainer, node2);

        // Add new rectangle to container
        parentContainer.insertNode(rectangle1);
        parentContainer.insertNode(rectangle2);
        parentContainer.setLeaf();

        // Reinsert the rest of the children
        for (RNode node : container.getNodes()) {
            if (!node.equals(node1) && !node.equals(node2)) {
                if (node instanceof RPoint) {
                    // If they are points insert them again recursively
                    insertRecursive(parentContainer, node);
                }
                else {
                    // If they are rectangles insert them to the big rectangle that grows the least
                    RRectangle successor = getMinGrowthRectangle(node, parentContainer);
                    successor.getChild().insertNode(node);
                    if (successor.getChild().isOverload()) split(successor.getChild());
                }
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

    // -------------------------------- DELETE --------------------------------

    // TODO: Deleteion

    // -------------------------------- SEARCH BY AREA --------------------------------

    public void searchByArea(RRectangle rectangle) {
        searchResult = new ArrayList<>();
        searchByAreaRecursive(root, rectangle);
    }

    private void searchByAreaRecursive(RContainer container, RRectangle rectangle) {
        for (RNode node : container.getNodes()){
            if (node instanceof RRectangle) {
                RRectangle curRectangle = (RRectangle) node;

                // If the actual rectangle overlaps at any point with the specified rectangle, go deep
                if (curRectangle.isIntersecting(rectangle)) {
                    searchByAreaRecursive(curRectangle.getChild(), rectangle);
                }
            }
            else {
                // If it is a point check if it is inside the specified rectangle
                if (node.isContained(rectangle)) {
                    searchResult.add(node.toString());
                }
            }
        }
    }

    // -------------------------------- SEARCH BY PROXIMITY --------------------------------

    private float curMaxDistance;

    public void searchByProximity(Point point, int numPoints) {
        curMaxDistance = Float.MIN_VALUE;
        searchByProximityRecursive(root, point, numPoints);
    }

    private void searchByProximityRecursive(RContainer container, Point point, int numPoints) {
        if (container.isLeaf()) {
            for (RNode node : container.getNodes()) {
                // TODO: add node

                if (curMaxDistance < point.getDistance(node.getPoint())) curMaxDistance = point.getDistance(node.getPoint());
            }
        }
        else {
            RRectangle successor = getMinGrowthRectangle(new RPoint("", point), container);
            if (successor != null) searchByProximityRecursive(successor.getChild(), point, numPoints);

            // After inserting nodes from first rectangle check its siblings
            for (RNode node : container.getNodes()) {
                RRectangle rectangle = (RRectangle) node;
                if (!rectangle.equals(successor) && curMaxDistance > rectangle.getMinDistance(point)) {
                    // TODO: add node
                }
            }
        }
    }

    // TODO: after all candidate points have been inserted get the ones with the k smallest distance
}
