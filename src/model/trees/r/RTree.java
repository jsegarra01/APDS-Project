package model.trees.r;

import java.util.ArrayList;
import java.util.List;

public class RTree {

    public static final int ORDER = 3;

    private RContainer root;

    public RTree() {
        root = new RContainer();
    }

    public RContainer getRoot() {
        return root;
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
        List<RNode> leftOvers = new ArrayList<>(container.getNodes());
        leftOvers.remove(node1);
        leftOvers.remove(node2);

        for (RNode node : leftOvers) {
            RRectangle successor = getMinGrowthRectangle(node, parentContainer);
            successor.getChild().insertNode(node);

            if (node instanceof RRectangle) {
                // Update parent container reference on child
                ((RRectangle) node).getChild().setParentContainer(successor.getChild());
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

        if (node instanceof RRectangle) {
            // Update parent container reference on child
            ((RRectangle) node).getChild().setParentContainer(childContainer);
        }

        return rectangle;
    }

    // -------------------------------- DELETE --------------------------------

    // TODO: Deletion

    // -------------------------------- SEARCH BY AREA --------------------------------

    private List<String> areaSearchResult;

    public int getResultSize() {
        return  areaSearchResult.size();
    }

    public List<String> getAreaSearchResult() {
        return areaSearchResult;
    }

    public void searchByArea(RRectangle rectangle) {
        areaSearchResult = new ArrayList<>();
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
                    areaSearchResult.add(node.toString());
                }
            }
        }
    }

    // -------------------------------- SEARCH BY PROXIMITY --------------------------------

    private List<RNode> proximitySearchResult;

    private RNode distantNode;
    private float curMaxDistance;

    public List<RNode> getProximitySearchResult() {
        return proximitySearchResult;
    }

    public void searchByProximity(Point point, int k) {
        curMaxDistance = Float.MIN_VALUE;
        proximitySearchResult = new ArrayList<>();

        searchByProximityRecursive(root, point, k);
    }

    private void searchByProximityRecursive(RContainer container, Point point, int k) {
        if (container.isLeaf()) {
            // Get nearest points
            findNearestPoints(container, point, k);
        }
        else {
            // Find the rectangle where the point would be inserted
            RRectangle successor = getMinGrowthRectangle(new RPoint("", point), container);
            if (successor != null) searchByProximityRecursive(successor.getChild(), point, k);

            // After getting the points in the rectangle where the point would be inserted
            // Visit the siblings and check if there is any node closer
            for (RNode node : container.getNodes()) {
                RRectangle rectangle = (RRectangle) node;

                // Skip the visited rectangle
                if (rectangle.equals(successor)) continue;

                // Prune those rectangles that are to far away
                if (rectangle.getMinDistance(point) < curMaxDistance){
                    searchByProximityRecursive(rectangle.getChild(), point, k);
                }
            }
        }
    }

    private void findNearestPoints(RContainer container, Point point, int k) {
        for (RNode curNode : container.getNodes()) {
            if (proximitySearchResult.size() < k) {
                proximitySearchResult.add(curNode);

                // Update max distance
                if (curMaxDistance < point.getDistance(curNode.getPoint())) {
                    curMaxDistance = point.getDistance(curNode.getPoint());
                    distantNode = curNode;
                }
            }
            // If we have already got k points check if any of the remaining is closer than the actual ones
            else {
                if (curMaxDistance > point.getDistance(curNode.getPoint())) {
                    proximitySearchResult.set(proximitySearchResult.indexOf(distantNode), curNode);
                    curMaxDistance = Float.MIN_VALUE;
                }

                // Update max distance
                for (RNode node: proximitySearchResult) {
                    if (curMaxDistance < point.getDistance(node.getPoint())) {
                        curMaxDistance = point.getDistance(node.getPoint());
                        distantNode = node;
                    }
                }
            }
        }
    }
}
