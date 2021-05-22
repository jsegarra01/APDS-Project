package model.trees.binary;

public interface BinaryTreeInterface<T extends Comparable<T>> {

    void insert(BinaryNode<T> node);

    void delete(BinaryNode<T> node);

    BinaryNode<T> search(T key);

    BinaryNode<T> min();

    BinaryNode<T> max();

    BinaryNode<T> predecessor(BinaryNode<T> node);

    BinaryNode<T> successor(BinaryNode<T> node);
}
