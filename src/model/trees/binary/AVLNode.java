package model.trees.binary;

public class AVLNode<T> extends BinaryNode<T> {

    // TODO: declare height member and define getter and setter
    // TODO: Override some methods

    private int height;

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
}
