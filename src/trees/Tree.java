package trees;

public interface Tree<E, T> {

    E searchNode(T t);
    void addNode(E e);
    void removeNode(E e);

    E searchMinimum();
    E searchMaximum();
    E searchSuccessor(E e);
    E searchPredecessor(E e);
}
