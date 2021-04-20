package model.trees.binary;

public class AVLBinarySearchTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    @Override
    public void insert(BinaryNode<T> node) {
        super.insert(node);
        recursionUpdate((AVLNode<T>) node);
        printTree();
        System.out.println();
    }

    @Override
    public void delete(BinaryNode<T> node) {
        super.delete(node);
        recursionUpdate((AVLNode<T>) node);
        printTree();
        System.out.println();
    }

    private void recursionUpdate(AVLNode<T> node) {
        update(node);
        balance(node);

        if (node.getParent() != null) {
            recursionUpdate(node.getParent());
        }
    }

    private void update(AVLNode<T> node){
        int leftHeight = -1;
        int rightHeight = -1;

        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }

        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(rightHeight - leftHeight);
    }

    private void balance(AVLNode<T> node) {
        if (node.getBalanceFactor() == -2) {
            if (node.getLeft().getBalanceFactor() > 0) {
                // Left-right case
                leftRotation(node.getLeft());
            }
            rightRotation(node);
        }
        else if (node.getBalanceFactor() == 2) {
            if (node.getRight().getBalanceFactor() < 0) {
                // Right-left case
                rightRotation(node.getRight());
            }
            leftRotation(node);
        }
    }

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

        update(x);
        update(y);
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

        update(y);
        update(x);
    }
}
