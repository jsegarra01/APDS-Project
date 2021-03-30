package trees;

import trees.binary.*;

import java.io.IOException;
import java.util.List;

public class TreesManager {

    private final BinaryTree binaryTree;

    public TreesManager () throws IOException{
        binaryTree = new BinaryTree();
        initBinaryTree();
    }

    private void initBinaryTree() throws IOException {
        List<BinaryNode> nodeList = BinaryTreeDAO.parseNodes();

        for (BinaryNode node : nodeList) {
            binaryTree.addNode(node);
        }
    }
}
