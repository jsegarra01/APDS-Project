package model;

public abstract class Node<T> {

    protected final T key;

    public Node(T key) {
        this.key = key;
    }

    public Node() {
        this.key = null;
    }

    public T getKey() {
        return this.key;
    }

    public abstract String toString();
}
