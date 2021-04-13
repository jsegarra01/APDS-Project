package model.trees.binary;

public class RedBlackNode<T> extends BinaryNode<T> {

    public static final int BLACK = 0;
    public static final int RED = 1;

    private int color;
    private final boolean isNil;

    public RedBlackNode(T key, String name) {
        super(key, name);
        this.color = BLACK;
        this.isNil = false;
    }

    public RedBlackNode() {
        super();
        this.color = BLACK;
        this.isNil = true;
    }

    @Override
    public RedBlackNode<T> getParent() {
        return (RedBlackNode<T>) super.getParent();
    }

    @Override
    public RedBlackNode<T> getLeft() {
        return (RedBlackNode<T>) super.getLeft();
    }

    @Override
    public RedBlackNode<T> getRight() {
        return (RedBlackNode<T>) super.getRight();
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColorBlack() {
        this.color = BLACK;
    }

    public void setColorRed() {
        this.color = RED;
    }

    public boolean isRed() {
        return color == RED;
    }

    public boolean isBlack() {
        return color == BLACK;
    }

    @Override
    public String toString() {
        String color = (this.isRed()) ? "RED" : "BLACK";

        return (isNil) ?
                "nil" + " (" + color + ")" :
                super.toString() + " (" + color + ")";
    }

}
