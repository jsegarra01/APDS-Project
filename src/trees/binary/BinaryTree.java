package trees.binary;

public class BinaryTree {

    private BinaryNode root;

    public void addNode(BinaryNode newNode) {
        if (root == null) {
            root = newNode;
        } else {
            add(root, newNode);
        }
    }

    private void add(BinaryNode node, BinaryNode newNode) {
        // Left node
        if (newNode.getValue() < node.getValue()) {
            // Leaf node
            if (node.getLeftChild() == null) {
                newNode.setParent(node);
                node.setLeftChild(newNode);
            } else {
                add(node.getLeftChild(), newNode);
            }
        }
        // Right node
        else if (newNode.getValue() > node.getValue()) {
            // Leaf node
            if (node.getRightChild() == null) {
                newNode.setParent(node);
                node.setRightChild(newNode);
            }
            else {
                add(node.getRightChild(), newNode);
            }
        }
    }

}
