package model.trees.binary;

public class BinaryNode<T extends Comparable<T>> {

    protected final T key;
    private final String name;

    private BinaryNode<T> left;
    private BinaryNode<T> right;
    private BinaryNode<T> parent;

    public BinaryNode(T key, String name) {
        this.key = key;
        this.name = name;
    }

    public BinaryNode() {
        this.key = null;
        this.name = null;
    }

    public T getKey() {
        return key;
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
