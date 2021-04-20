package model.trees.binary;

public class AVLNode<T extends Comparable<T>> extends BinaryNode<T> {

    private int height;
    private int balanceFactor;

    public AVLNode(T key, String name) {
        super(key, name);

        this.height = 0;
    }

    @Override
    public AVLNode<T> getParent() {
        return (AVLNode<T>) super.getParent();
    }

    @Override
    public AVLNode<T> getLeft() {
        return (AVLNode<T>) super.getLeft();
    }

    @Override
    public AVLNode<T> getRight() {
        return (AVLNode<T>) super.getRight();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalanceFactor() { return balanceFactor;}

    public void setBalanceFactor(int balanceFactor) { this.balanceFactor = balanceFactor;}

    @Override
    public String toString() {
        return super.toString() + " (" + height + ")";
    }


}
