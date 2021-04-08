package trees.binary;

import trees.Tree;

public class BinaryTree implements Tree<BinaryNode, Long> {

    private BinaryNode root;

    public BinaryNode getRoot() {
        return root;
    }

    @Override
    public BinaryNode searchMinimum() {
        return searchMinimum(root);
    }

    private BinaryNode searchMinimum(BinaryNode node) {
        return (node.getLeftChild() == null) ? node : searchMinimum(node.getLeftChild());
    }

    @Override
    public BinaryNode searchMaximum() {
        return searchMaximum(root);
    }

    private BinaryNode searchMaximum(BinaryNode node) {
        return (node.getRightChild() == null) ? node : searchMaximum(node.getRightChild());
    }

    @Override
    public BinaryNode searchSuccessor(BinaryNode node) {
        if (node.getRightChild() != null) {
            return searchMinimum(node.getRightChild());
        }

        BinaryNode successor = node.getParent();
        while (node == successor.getRightChild()) {
            node = successor;
            successor = successor.getParent();
        }

        return successor;
    }

    @Override
    public BinaryNode searchPredecessor(BinaryNode node) {
        if (node.getLeftChild() != null) {
            return searchMinimum(node.getLeftChild());
        }

        BinaryNode successor = node.getParent();
        while (node == successor.getLeftChild()) {
            node = successor;
            successor = successor.getParent();
        }

        return successor;
    }

    @Override
    public void addNode(BinaryNode newNode) {
        if (root == null) {
            root = newNode;
            return;
        }

        add(root, newNode);
    }

    private void add(BinaryNode node, BinaryNode newNode) {
        // Left node
        if (newNode.getValue() < node.getValue()) {
            if (node.getLeftChild() == null) {
                // Leaf node
                newNode.setParent(node);
                node.setLeftChild(newNode);
            } else {
                add(node.getLeftChild(), newNode);
            }
        }
        // Right node
        else if (newNode.getValue() > node.getValue()) {
            if (node.getRightChild() == null) {
                // Leaf node
                newNode.setParent(node);
                node.setRightChild(newNode);
            }
            else {
                add(node.getRightChild(), newNode);
            }
        }
    }

    @Override
    public void removeNode(BinaryNode node) {
        root = remove(root, node.getValue());
    }

    private BinaryNode remove(BinaryNode node, long value) {
        if (node == null) {
            return null;
        }

        if (value == node.getValue()) {
            return removeCases(node);
        }

        if (value < node.getValue()) {
            node.setLeftChild(remove(node.getLeftChild(), value));
        }
        else {
            node.setRightChild(remove(node.getRightChild(), value));
        }
        return node;
    }

    private BinaryNode removeCases(BinaryNode node) {
        // Leaf node (no children)
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            return null;
        }
        // Node has only one child
        else if (node.getLeftChild() == null || node.getRightChild() == null) {
            // Right child
            if (node.getLeftChild() == null) {
                node.getRightChild().setParent(node.getParent());
                return node.getRightChild();
            }
            // Left child
            node.getLeftChild().setParent(node.getParent());
            return node.getLeftChild();
        }
        // Node has both children
        else {
            // Find next inorder successor
            BinaryNode successor = searchSuccessor(node);

            // Replace it with the node to be removed
            node.setName(successor.getName());
            node.setValue(successor.getValue());

            // Remove replaced node
            node.setRightChild(remove(node.getRightChild(), successor.getValue()));
            return node;
        }
    }

    @Override
    public BinaryNode searchNode(Long value) {
        return searchByValue(root, value);
    }

    private BinaryNode searchByValue(BinaryNode node, long value) {
        if (node == null) {
            return null;
        }

        if (value == node.getValue()) {
            return node;
        }

        return (value < node.getValue()) ?
                searchByValue(node.getLeftChild(), value) : searchByValue(node.getRightChild(), value);
    }

    public BinaryNode searchNode(String name) {
        return searchByName(root, name);
    }

    private BinaryNode searchByName(BinaryNode node, String name) {
        if (node == null) {
            return null;
        }

        if (name.equals(node.getName())) {
            return node;
        }

        BinaryNode tmp = searchByName(node.getLeftChild(), name);
        if (tmp == null) {
            tmp = searchByName(node.getRightChild(), name);
        }
        return tmp;
    }

    public void searchNodes(long lowerBound, long upperBound) {
        System.out.println(countValuesInRange(root, lowerBound, upperBound) + " treasures were found in this range:\n");
        searchByRange(root, lowerBound, upperBound);
    }

    private int countValuesInRange(BinaryNode node, long lowerBound, long upperBound) {
        if (node != null) {
            int countLeft = countValuesInRange(node.getLeftChild(), lowerBound, upperBound);
            int countRight = countValuesInRange(node.getRightChild(), lowerBound, upperBound);

            if (node.getValue() >= lowerBound && node.getValue() <= upperBound) {
                return countLeft + countRight + 1;
            }
            else {
                return countLeft + countRight;
            }
        }
        return 0;
    }

    private void searchByRange(BinaryNode node, long lowerBound, long upperBound) {
        if (node != null) {
            searchByRange(node.getLeftChild(), lowerBound, upperBound);
            if (node.getValue() >= lowerBound && node.getValue() <= upperBound) {
                System.out.println("\t" + BinaryNode.toString(node));
            }
            searchByRange(node.getRightChild(), lowerBound, upperBound);
        }
    }

}
