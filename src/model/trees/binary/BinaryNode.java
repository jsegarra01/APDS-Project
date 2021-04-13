package model.trees.binary;

import model.Node;

public class BinaryNode<T> extends Node<T> {

    private final String name;

    private BinaryNode<T> left;
    private BinaryNode<T> right;
    private BinaryNode<T> parent;

    public BinaryNode(T key, String name) {
        super(key);

        this.name = name;
    }

    public BinaryNode() {
        super();

        this.name = null;
    }

    public String getName() {
        return name;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public BinaryNode<T> getParent() {
        return parent;
    }

    public void setParent(BinaryNode<T> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return  name
                + " - "
                + String.format("%,d", key).replace(',', '.')
                + " doubloons";
    }
}
