package trees.binary;

public class BinaryNode {

    private String name;
    private long value;

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

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
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

    public static String toString(BinaryNode node) {
        return  node.name
                + " - "
                + String.format("%,d", node.value).replace(',', '.')
                + " doubloons";
    }
}
