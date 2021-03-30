package trees.binary;

public class BinaryNode {

    private final String name;
    private final long value;

    private BinaryNode parent;
    private BinaryNode leftChild;
    private BinaryNode rightChild;

    public BinaryNode(String name, long value) {
        this.name = name;
        this.value = value;

        parent = null;
        leftChild = null;
        rightChild = null;
    }

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }

    public BinaryNode getParent() {
        return parent;
    }

    public void setParent(BinaryNode parent) {
        this.parent = parent;
    }

    public BinaryNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BinaryNode leftChild) {
        this.leftChild = leftChild;
    }

    public BinaryNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BinaryNode rightChild) {
        this.rightChild = rightChild;
    }


}
