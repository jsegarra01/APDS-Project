package trees.binary;

import java.util.ArrayDeque;
import java.util.Queue;

public class BinaryTraversal {

    private void printNode(BinaryNode node) {
        System.out.println("\t" + BinaryNode.toString(node));
    }

    public void preOrderTraversal(BinaryTree tree) {
        preOrder(tree.getRoot());
    }

    private void preOrder(BinaryNode node) {
        if (node != null) {
            printNode(node);
            preOrder(node.getLeftChild());
            preOrder(node.getRightChild());
        }
    }

    public void postOrderTraversal(BinaryTree tree) {
        postOrder(tree.getRoot());
    }

    private void postOrder(BinaryNode node) {
        if (node != null) {
            postOrder(node.getLeftChild());
            postOrder(node.getRightChild());
            printNode(node);
        }
    }

    public void inOrderTraversal(BinaryTree tree) {
        inOrder(tree.getRoot());
    }

    private void inOrder(BinaryNode node) {
        if (node != null) {
            inOrder(node.getLeftChild());
            printNode(node);
            inOrder(node.getRightChild());
        }
    }

    public void levelTraversal(BinaryTree tree) {
        Queue<BinaryNode> queue = new ArrayDeque<>();

        if (tree.getRoot() != null) {
            queue.add(tree.getRoot());
        }

        while (!queue.isEmpty()) {
            BinaryNode node = queue.poll();
            printNode(node);

            if (node.getLeftChild() != null) {
                queue.add(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                queue.add(node.getRightChild());
            }
        }
    }
}
