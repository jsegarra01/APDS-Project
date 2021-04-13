package model.trees.binary;

import model.trees.*;
import model.Node;

public class BinarySearchTree<T extends Comparable<T>> implements TreeInterface<T> {

    protected BinaryNode<T> root;

    private static final int ROOT_ID = 0;
    private static final int LEFT_ID = 1;
    private static final int RIGHT_ID = 2;

    public BinaryNode<T> getRoot() {
        return root;
    }

    @Override
    public void insert(Node<T> node) {
        insert((BinaryNode<T>) node);
    }

    private void insert(BinaryNode<T> node) {
        BinaryNode<T> parent = null;
        BinaryNode<T> aux = root;

        // get new node parent
        while (aux != null) {
            parent = aux;
            if (node.getKey().compareTo(aux.getKey()) < 0) {
                aux = aux.getLeft();
            }
            else {
                aux = aux.getRight();
            }
        }

        // parent-child relation
        node.setParent(parent);
        if (parent == null) {
            root = node;
        }
        else if (node.getKey().compareTo(parent.getKey()) < 0) {
            parent.setLeft(node);
        }
        else {
            parent.setRight(node);
        }
    }

    //  Remove cases:
    //  ------------------------------------------------------------------------
    //
    //  Dictionary: n - Node to be removed, l - Left child, r - Right child,
    //              s - Successor,  N - null
    //
    //  |---------------------------|    |-----------------------------|
    //  |     Only Right Child      |    |       Only Left Child       |
    //  |---------------------------|    |-----------------------------|
    //  |                           |    |                             |
    //  |     |               |     |    |       |               |     |
    //  |    (n)    n - r    (r)    |    |      (n)    l - r    (l)    |
    //  |   /   \    -->    /   \   |    |     /   \    -->    /   \   |
    //  |  N    (r)                 |    |   (l)    N                  |
    //  |      /   \                |    |  /   \                      |
    //  |                           |    |                             |
    //  |---------------------------|    |-----------------------------|
    //
    //  NOTICE: if there are no children, we can execute one of the cases above
    //
    //  |-------------------------------|
    //  |   Two children and s == n.r   |
    //  |-------------------------------|
    //  |                               |
    //  |      |                 |      |
    //  |     (n)    s - n      (s)     |
    //  |    /   \    -->      /   \    |
    //  |  (l)   (s)         (l)   (r)  |
    //  |       /   \                   |
    //  |      N    (r)                 |
    //  |                               |
    //  |-------------------------------|
    //
    //  |-------------------------------------------------------------|
    //  |                  Two children and s != n.r                  |
    //  |-------------------------------------------------------------|
    //  |                                                             |
    //  |      |                    |                                 |
    //  |     (n)                  (n)  (s)                  (s)      |
    //  |    /   \                /    /   \                /   \     |
    //  |  (l)   (r)   s - s.r  (l)   N    (r)    s - n   (l)   (r)   |
    //  |       /   \    -->              /   \    -->         /   \  |
    //  |     (s)                     (s.r)                (s.r)      |
    //  |    /   \                                                    |
    //  |   N    (s.r)                                                |
    //  |                                                             |
    //  |-------------------------------------------------------------|

    @Override
    public void delete(Node<T> node) {
        delete((BinaryNode<T>) node);
    }

    private void delete (BinaryNode<T> node) {
        if (node.getLeft() == null) {
            // Node has only right child
            transplant(node, node.getRight());
        }
        else if (node.getRight() == null) {
            // Node has only left child
            transplant(node, node.getLeft());
        }
        else {
            // Node has two children
            BinaryNode<T> successor = searchMinimum(node.getRight());

            if (successor.getParent() != node) {
                // If successor is not z right child
                transplant(successor, successor.getRight());
                successor.setRight(node.getRight());
                successor.getRight().setParent(successor);
            }
            transplant(node, successor);
            successor.setLeft(node.getLeft());
            successor.getLeft().setParent(successor);
        }
    }

    private void transplant(BinaryNode<T> nodeA, BinaryNode<T> nodeB) {
        if (nodeA.getParent() == null) {
            root = nodeB;
        }
        else if (nodeA == nodeA.getParent().getLeft()) {
            nodeA.getParent().setLeft(nodeB);
        }
        else {
            nodeA.getParent().setRight(nodeB);
        }

        if (nodeB != null) {
            nodeB.setParent(nodeA.getParent());
        }
    }

    @Override
    public Node<T> search(T key) {
        return searchByValue(root, key);
    }

    private BinaryNode<T> searchByValue(BinaryNode<T> node, T key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.getKey()) == 0) {
            return node;
        }

        return (key.compareTo(node.getKey()) < 0) ?
                searchByValue(node.getLeft(), key) : searchByValue(node.getRight(), key);
    }

    public Node<T> search(String name) {
        return searchByName(root, name);
    }

    private BinaryNode<T> searchByName(BinaryNode<T> node, String name) {
        if (node == null) {
            return null;
        }

        if (name.equals(node.getName())) {
            return node;
        }

        BinaryNode<T> aux = searchByName(node.getLeft(), name);
        if (aux == null) {
            aux = searchByName(node.getRight(), name);
        }
        return aux;
    }

    public void rangeSearch(T minimum, T maximum) {
        System.out.println(countValuesInRange(root, minimum, maximum) + " treasures were found in this range: \n");
        printValuesInRange(root, minimum, maximum);
    }

    private int countValuesInRange(BinaryNode<T> node, T min, T max) {
        // Second condition is only for RBTs nil
        if (node == null || node.getKey() == null) {
            return 0;
        }

        // Node is in range
        if ((min.compareTo(node.getKey()) <= 0) && (max.compareTo(node.getKey()) >= 0)) {
            return 1 + countValuesInRange(node.getLeft(), min, max) + countValuesInRange(node.getRight(), min, max);
        }

        // Node is bellow range
        if (node.getKey().compareTo(min) < 0) {
            return countValuesInRange(node.getRight(), min, max);
        }
        else {
            return countValuesInRange(node.getLeft(), min, max);
        }
    }

    // Similar to inorder traversal
    private void printValuesInRange(BinaryNode<T> node, T min, T max) {
        // Second condition is only for RBTs nil
        if (node == null || node.getKey() == null) {
            return;
        }

        // Search left until min
        if (min.compareTo(node.getKey()) < 0) {
            printValuesInRange(node.getLeft(), min, max);
        }

        // Node is in range
        if ((min.compareTo(node.getKey()) <= 0) && (max.compareTo(node.getKey()) >= 0)) {
            System.out.println("\t" + node.toString());
        }

        // Search right until max
        if (max.compareTo(node.getKey()) > 0) {
            printValuesInRange(node.getRight(), min, max);
        }
    }

    @Override
    public Node<T> min() {
        return searchMinimum(root);
    }

    protected BinaryNode<T> searchMinimum(BinaryNode<T> node) {
        return (node.getLeft() == null) ? node : searchMinimum(node.getLeft());
    }

    @Override
    public Node<T> max() {
        return searchMaximum(root);
    }

    protected BinaryNode<T> searchMaximum(BinaryNode<T> node) {
        return (node.getRight() == null) ? node : searchMaximum(node.getRight());
    }


    @Override
    public Node<T> predecessor(Node<T> node) {
        return predecessor((BinaryNode<T>) node);
    }

    private BinaryNode<T> predecessor(BinaryNode<T> node) {
        if (node.getLeft() != null) {
            return searchMinimum(node.getLeft());
        }

        BinaryNode<T> successor = node.getParent();
        while (node == successor.getLeft()) {
            node = successor;
            successor = successor.getParent();
        }

        return successor;
    }

    @Override
    public Node<T> successor(Node<T> node) {
        return successor((BinaryNode<T>) node);
    }

    private BinaryNode<T> successor(BinaryNode<T> node) {
        if (node.getRight() != null) {
            return searchMinimum(node.getRight());
        }

        BinaryNode<T> successor = node.getParent();
        while (node == successor.getRight()) {
            node = successor;
            successor = successor.getParent();
        }

        return successor;
    }

    public void printTree() {
        printNode(root, "", ROOT_ID);
    }

    private void printNode(BinaryNode<T> node, String indent, int type) {
        if (node != null) {
            System.out.print(indent);
            switch (type) {
                case ROOT_ID -> {
                    System.out.println("ROOT ---- " + node.toString());
                    indent += "   ";
                }
                case LEFT_ID -> {
                    System.out.println("L ---- " + node.toString());
                    indent += "|  ";
                }
                case RIGHT_ID -> {
                    System.out.println("R ---- " + node.toString());
                    indent += "   ";
                }
            }

            printNode(node.getLeft(), indent, LEFT_ID);
            printNode(node.getRight(), indent, RIGHT_ID);
        }
    }
}
