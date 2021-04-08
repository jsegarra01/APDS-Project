package trees;

import trees.binary.*;

import java.io.IOException;
import java.util.List;

public class TreesManager {

    private final BinaryTree binaryTree;
    private final BinaryTraversal binaryTraversal;

    public static final int PREORDER_TRAVERSAL_ID = 0;
    public static final int POSTORDER_TRAVERSAL_ID = 1;
    public static final int INORDER_TRAVERSAL_ID = 2;
    public static final int BY_LEVEL_TRAVERSAL_ID = 3;

    public TreesManager () throws IOException{
        binaryTree = new BinaryTree();
        binaryTraversal = new BinaryTraversal();

        initBinaryTree();
    }

    // ---------------------------------------------------------------------------------
    // BINARY TREES
    // ---------------------------------------------------------------------------------

    private void initBinaryTree() throws IOException {
        List<BinaryNode> nodeList = BinaryTreeDAO.parseNodes();

        for (BinaryNode node : nodeList) {
            binaryTree.addNode(node);
        }
    }

    public void addBinaryNode(String binaryNodeName, long binaryNodeValue) {
        binaryTree.addNode(new BinaryNode(binaryNodeName, binaryNodeValue));
    }

    public void removeBinaryNode(String binaryNodeName) {
        binaryTree.removeNode(binaryTree.searchNode(binaryNodeName));
    }

    public void listLoot(int searchID) {
        switch (searchID) {
            case PREORDER_TRAVERSAL_ID -> binaryTraversal.preOrderTraversal(binaryTree);
            case POSTORDER_TRAVERSAL_ID -> binaryTraversal.postOrderTraversal(binaryTree);
            case INORDER_TRAVERSAL_ID -> binaryTraversal.inOrderTraversal(binaryTree);
            case BY_LEVEL_TRAVERSAL_ID -> binaryTraversal.levelTraversal(binaryTree);
        }
    }

    public String searchByValue(long value) {
        BinaryNode node = binaryTree.searchNode(value);
        return (node == null) ? null : node.getName();
    }

    public void searchByRange(long lowerBound, long upperBound) {
        binaryTree.searchNodes(lowerBound, upperBound);
    }

    // ---------------------------------------------------------------------------------
    // R TREES
    // ---------------------------------------------------------------------------------
}
