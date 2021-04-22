package model.trees;

import model.trees.binary.*;

import java.io.IOException;
import java.util.List;

public class TreesManager {

    private final BinarySearchTree<Long> binaryTree;
    private final BinarySearchTreeTraversal<Long> binaryTraversal;

    public static final int PREORDER_TRAVERSAL_ID = 0;
    public static final int POSTORDER_TRAVERSAL_ID = 1;
    public static final int INORDER_TRAVERSAL_ID = 2;
    public static final int BY_LEVEL_TRAVERSAL_ID = 3;

    public TreesManager () throws IOException{
        binaryTree = new AVLBinarySearchTree<>();
        binaryTraversal = new BinarySearchTreeTraversal<>();

        initBinaryTree();
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
}
