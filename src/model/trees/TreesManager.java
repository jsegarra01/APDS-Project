package model.trees;

import model.trees.binary.*;
import model.trees.r.*;

import java.io.IOException;
import java.util.List;

public class TreesManager {

    private final BinarySearchTree<Long> binaryTree;
    private final BinarySearchTreeTraversal<Long> binaryTraversal;

    private final RTree rTree;
    private final RTreeGraphics rTreeGraphics;

    public static final int PREORDER_TRAVERSAL_ID = 0;
    public static final int POSTORDER_TRAVERSAL_ID = 1;
    public static final int INORDER_TRAVERSAL_ID = 2;
    public static final int BY_LEVEL_TRAVERSAL_ID = 3;

    public TreesManager () throws IOException{
        binaryTree = new BinarySearchTree<>();
        binaryTraversal = new BinarySearchTreeTraversal<>();

        rTree = new RTree();
        rTreeGraphics = new RTreeGraphics(rTree);

        initBinaryTree();
        initRTree();
    }

    // ---------------------------------------------------------------------------------
    // BINARY TREES
    // ---------------------------------------------------------------------------------

    private void initBinaryTree() throws IOException {
        List<BinaryNode<Long>> nodeList = BinarySearchTreeDAO.parseNodes();

        for (BinaryNode<Long> node : nodeList) {
            binaryTree.insert(node);
        }
    }

    public void addBinaryNode(String name, long key) {
        binaryTree.insert(new BinaryNode<>(key, name));
    }

    public void removeBinaryNode(String binaryNodeName) {
        BinaryNode<Long> node = binaryTree.search(binaryNodeName);
        if (node != null) {
            binaryTree.delete(node);
        }
        else {
            System.out.println("\nERROR: The specified node does not exist.");
        }
    }

    public void listLoot(int traversalID) {
        switch (traversalID) {
            case PREORDER_TRAVERSAL_ID -> binaryTraversal.preOrderTraversal(binaryTree);
            case POSTORDER_TRAVERSAL_ID -> binaryTraversal.postOrderTraversal(binaryTree);
            case INORDER_TRAVERSAL_ID -> binaryTraversal.inOrderTraversal(binaryTree);
            case BY_LEVEL_TRAVERSAL_ID -> binaryTraversal.levelTraversal(binaryTree);
        }
    }

    public String searchByValue(long key) {
        BinaryNode<Long> node = binaryTree.search(key);
        return (node == null) ? null : node.getName();
    }

    public void searchByRange(long minimum, long maximum) {
        binaryTree.rangeSearch(minimum, maximum);
    }

    public void printBinaryTree() {
        binaryTree.printTree();
    }

    // ---------------------------------------------------------------------------------
    // R TREES
    // ---------------------------------------------------------------------------------

    private void initRTree() throws IOException {
        List<RPoint> nodeList = RTreeDAO.parseNodes();

        for (RPoint node : nodeList) {
            rTree.insert(node);
        }

        /*
        rTree.insert(new RPoint("P1", new Point(2, 3)));
        rTree.insert(new RPoint("P2", new Point(3, 1)));
        rTree.insert(new RPoint("P3", new Point(6, 6)));
        rTree.insert(new RPoint("P4", new Point(5, 5)));
        rTree.insert(new RPoint("P5", new Point(5, 6)));
        rTree.insert(new RPoint("P6", new Point(7, 4)));
        rTree.insert(new RPoint("P7", new Point(8, 3)));
        rTree.insert(new RPoint("P8", new Point(6, 7)));
        */
    }

    public void addRNode(String nodeName, float x, float y) {
        rTree.insert(new RPoint(nodeName, new Point(x, y)));
    }

    public void visualizeRTree() {
        rTreeGraphics.showTree();
    }

    public void searchByArea(float x1, float y1, float x2, float y2){
        Point pointA = new Point(x1, y1);
        Point pointB = new Point(x2, y2);

        rTree.searchByArea(new RRectangle(pointA, pointB));

        if (rTree.getResultSize() != 0) {
            System.out.println("\n" + rTree.getResultSize() + " treasures were found in this area: \n");
            for (String result : rTree.getSearchResult()) {
                System.out.println("\t" + result);
            }
        }
        else {
            System.out.println("\nNo treasure was found in this areas");
        }
        System.out.println();
    }

    public void searchByProximity() {
        rTree.searchByProximity(new Point(0.8f, 1.2f), 3);

        rTree.printNear();
    }

}
