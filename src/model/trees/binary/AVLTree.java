package model.trees.binary;

import model.Node;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    @Override
    public void insert(Node<T> node) {
        super.insert(node);
    }

    @Override
    public void delete(Node<T> node) {
        super.delete(node);
    }

    // TODO: Balancing

    // Rotations follow the same logic as in red-black trees

    private void leftRotation(AVLNode<T> x) {
        // get y
        AVLNode<T> y = x.getRight();

        // x-b relation
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }

        // x.parent-y relation
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        }
        else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        }
        else {
            x.getParent().setRight(y);
        }

        // y-x relation
        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotation(AVLNode<T> y) {
        // get x
        AVLNode<T> x = y.getLeft();

        // y-b relation
        y.setLeft(x.getRight());
        if (x.getRight() != null) {
            x.getRight().setParent(y);
        }

        // y.parent-x relation
        x.setParent(y.getParent());
        if (y.getParent() == null) {
            root = x;
        }
        else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
        }
        else {
            y.getParent().setLeft(x);
        }

        // y-x relation
        x.setRight(y);
        y.setParent(x);
    }
}
