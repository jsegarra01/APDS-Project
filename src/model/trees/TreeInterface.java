package model.trees;

import model.Node;

public interface TreeInterface<T extends Comparable<T>> {

    void insert(Node<T> node);

    void delete(Node<T> node);

    Node<T> search(T key);

    Node<T> min();

    Node<T> max();

    Node<T> predecessor(Node<T> node);

    Node<T> successor(Node<T> node);
}
