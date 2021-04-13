package model.trees.binary;
import java.util.ArrayDeque;
import java.util.Queue;

public class BinarySearchTreeTraversal<T extends Comparable<T>> {

    private void printNode(BinaryNode<T> node) {
        System.out.println("\t" + node.toString());
    }

    public void preOrderTraversal(BinarySearchTree<T> tree) {
        preOrder(tree.getRoot());
    }

    private void preOrder(BinaryNode<T> node) {
        if (node != null) {
            printNode(node);
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    public void postOrderTraversal(BinarySearchTree<T> tree) {
        postOrder(tree.getRoot());
    }

    private void postOrder(BinaryNode<T> node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            printNode(node);
        }
    }

    public void inOrderTraversal(BinarySearchTree<T> tree) {
        inOrder(tree.getRoot());
    }

    private void inOrder(BinaryNode<T> node) {
        if (node != null) {
            inOrder(node.getLeft());
            printNode(node);
            inOrder(node.getRight());
        }
    }

    public void levelTraversal(BinarySearchTree<T> tree) {
        Queue<BinaryNode<T>> queue = new ArrayDeque<>();

        if (tree.getRoot() != null) {
            queue.add(tree.getRoot());
        }

        while (!queue.isEmpty()) {
            BinaryNode<T> node = queue.poll();
            printNode(node);

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
    }
}
