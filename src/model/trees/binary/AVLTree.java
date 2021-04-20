package model.trees.binary;

import model.Node;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    @Override
    public void insert(Node<T> node) {
        super.insert(node);
        recursionUpdate((AVLNode<T>) node);
        printTree();
        System.out.println();

    }

    @Override
    public void delete(Node<T> node) {
        super.delete(node);
        recursionUpdate((AVLNode<T>) node);
        printTree();
        System.out.println();
    }

    private void recursionUpdate(AVLNode<T> node){
        update((AVLNode<T>) node);
        balance((AVLNode<T>) node);
        if(((AVLNode<T>) node).getParent() != null){
            recursionUpdate((AVLNode<T>) ((AVLNode<T>) node).getParent());
        }
    }


    private void balance(AVLNode<T> newNode){
        if(newNode.getBalanceFactor() == -2){
            if(newNode.getLeft().getBalanceFactor() <= 0){
                leftLeftCase(newNode);
            }
            else{
                leftRightCase(newNode);
            }
        }
        else if(newNode.getBalanceFactor() == 2){
            if(newNode.getRight().getBalanceFactor() >= 0){
                rightRightCase(newNode);
            }
            else{
                rightLeftCase(newNode);
            }
        }
    }

    private void leftLeftCase(AVLNode<T> node){
        rightRotation(node);
    }

    private void leftRightCase(AVLNode<T> node){
        leftRotation(node.getLeft());
        leftLeftCase(node);
    }

    private void rightRightCase(AVLNode<T> node){
        leftRotation(node);
    }

    private void rightLeftCase(AVLNode<T> node){
        rightRotation(node.getRight());
        rightRightCase(node);
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


    private void update(AVLNode<T> node){
        int leftHeight = -1;
        int rightHeight = -1;

        if(node.getLeft() != null){
            leftHeight = node.getLeft().getHeight();
        }
        if(node.getRight() != null){
            rightHeight = node.getRight().getHeight();
        }

        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(rightHeight - leftHeight);

    }

}
