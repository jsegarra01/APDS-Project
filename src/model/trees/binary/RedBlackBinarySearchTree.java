package model.trees.binary;

public class RedBlackBinarySearchTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    // RBT with n nodes has a max height of 2 * log(n + 1) --> O(2log(n+1))

    // RBT properties:
    // 1. Every node is either red or black.
    // 2. The root is black.
    // 3. Every leaf (nil) is black.
    // 4. If a node is red, then both children are black.
    // 5. For each node, all simple paths from the node to descendant leaves
    //    contain the same number of black nodes.

    // For simplicity in the insertion and deletion operations, all leaf nodes
    // are pointing to a sentinel (nil) instead of null. In this way, we avoid
    // a lot of null checks before checking color

    private final RedBlackNode<T> nil;       // Sentinel

    public RedBlackBinarySearchTree() {
        nil = new RedBlackNode<>();
        root = nil;
    }

    // Both insert and delete O(log(n)) time

    // When inserting a new node to the tree, properties 1 and 3 are certainly
    // hold. Property 5 is also satisfied as we are replacing a black sentinel
    // with a red node with sentinel children. Therefore, properties 2 and 4 are
    // the only ones that might be violated, as new nods are colored red.

    @Override
    public void insert(BinaryNode<T> node) {
        insert((RedBlackNode<T>) node);
    }

    private void insert(RedBlackNode<T> node) {
        // Insert node and color it red
        insertNode(node);

        // Balance tree by fixing possible violations of properties 2 and 4
        // Property 4: parent cannot be red as child will be red.
        while (node.getParent().isRed()) {
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                node = recolorR(node);
            }
            else {
                node = recolorL(node);
            }
        }
        // Property 2: root cannot be red
        ((RedBlackNode<T>) root).setColorBlack();
    }

    //  |-----------------------------------|
    //  |       New node relationship       |
    //  |-----------------------------------|
    //  |                                   |
    //  |               (g) -> grandparent  |
    //  |              /   \                |
    //  |  parent <- (p)   (u) -> uncle     |
    //  |              \                    |
    //  |               (x) -> new node     |
    //  |                                   |
    //  |-----------------------------------|

    public void insertNode(RedBlackNode<T> node) {
        RedBlackNode<T> parent = nil;
        RedBlackNode<T> aux = (RedBlackNode<T>) root;

        // get new node parent
        while (aux != nil) {
            parent = aux;
            if (node.getKey().compareTo(aux.getKey()) < 0) {
                aux = aux.getLeft();
            }
            else {
                aux = aux.getRight();
            }
        }

        // parent-child relation
        node.setParent(parent);
        if (parent == nil) {
            root = node;
        }
        else if (node.getKey().compareTo(parent.getKey()) < 0) {
            parent.setLeft(node);
        }
        else {
            parent.setRight(node);
        }

        node.setColorRed();
        node.setLeft(nil);
        node.setRight(nil);
    }

    //  Cases when uncle of the inserted node is black
    //
    //  |--------------------------------------|  |--------------------------------------------|
    //  |          Case 2 - Triangle           |  |               Case 3 - Line                |
    //  |--------------------------------------|  |--------------------------------------------|
    //  |       Left       |       Right       |  |     Increasing      |      Decreasing      |
    //  |                  |                   |  |                     |                      |
    //  |    /   (g)       |       (g)    \    |  |      /    (g)       |       (g)     \      |
    //  |   /   /   \      |      /   \    \   |  |     /    /   \      |      /   \     \     |
    //  |  |  (p)   (u)    |    (u)    (p)  |  |  |    /   (p)   (u)    |    (u)   (p)    \    |
    //  |   \   \          |          /    /   |  |   /   /   \         |         /   \    \   |
    //  |    \   (x)       |       (x)    /    |  |  /  (x)   (y)       |      (y)     (x)  \  |
    //  |                  |                   |  |                     |                      |
    //  |--------------------------------------|  |--------------------------------------------|
    //  NOTICE: Case 2 falls through into case 3

    private RedBlackNode<T> recolorR(RedBlackNode<T> node) {
        // Get relations
        RedBlackNode<T> parent = node.getParent();
        RedBlackNode<T> grandparent = parent.getParent();
        RedBlackNode<T> uncle = grandparent.getRight();

        if (uncle.isRed()) {
            // Case 1: Uncle is red
            parent.setColorBlack();
            uncle.setColorBlack();
            grandparent.setColorRed();
            return grandparent;
        }
        else {
            if (node == parent.getRight()) {
                // Case 2: Uncle is black (triangle-left)
                node = parent;
                leftRotation(node);
            }
            else {
                // Case 3: Uncle is black (line-increasing)
                parent.setColorBlack();
                grandparent.setColorRed();
                rightRotation(grandparent);
            }
            return node;
        }
    }

    private RedBlackNode<T> recolorL(RedBlackNode<T> node) {
        // Get relations
        RedBlackNode<T> parent = node.getParent();
        RedBlackNode<T> grandparent = parent.getParent();
        RedBlackNode<T> uncle = grandparent.getLeft();

        if (uncle.isRed()) {
            // Case 1: Uncle is red
            parent.setColorBlack();
            uncle.setColorBlack();
            grandparent.setColorRed();
            return grandparent;
        }
        else {
            if (node == parent.getLeft()) {
                // Case 2: Uncle is black (triangle-right)
                node = parent;
                rightRotation(node);
            }
            else {
                // Case 3: Uncle is black (line-decreasing)
                parent.setColorBlack();
                grandparent.setColorRed();
                leftRotation(grandparent);
            }
            return node;
        }
    }

    @Override
    public void delete(BinaryNode<T> node) {
        deleteNode((RedBlackNode<T>) node);
    }

    private void deleteNode(RedBlackNode<T> z) {
        RedBlackNode<T> x;
        RedBlackNode<T> y = z;
        int originalColor = y.getColor();

        if (z.getLeft() == nil) {
            // Node has only right child
            x = z.getRight();
            transplant(z, z.getRight());
        }
        else if (z.getRight() == nil) {
            // Node has only left child
            x = z.getLeft();
            transplant(z, z.getLeft());
        }
        else {
            // Node has two child
            y = (RedBlackNode<T>) searchMinimum(z.getRight());
            originalColor = y.getColor();

            x = y.getRight();
            if (y.getParent() == z) {
                x.setParent(y);
            }
            else {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }

        if (originalColor == RedBlackNode.BLACK) {
            // TODO: balance tree
        }
    }

    private void transplant(RedBlackNode<T> node, RedBlackNode<T> child) {
        if (node.getParent() == nil) {
            root = child;
        }
        else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(child);
        }
        else {
            node.getParent().setRight(child);
        }

        child.setParent(node.getParent());
    }

    //  |----------------------------------------------|
    //  |       ROTATIONS - time complexity O(1)       |
    //  |----------------------------------------------|
    //  |                                              |
    //  |        |                            |        |
    //  |       (y)      Left Rotation       (x)       |
    //  |      /   \     <-------------     /   \      |
    //  |    (x)    c    Right Rotation    a    (y)    |
    //  |   /   \        ------------->        /   \   |
    //  |  a     b                            b     c  |
    //  |                                              |
    //  |----------------------------------------------|

    private void leftRotation(RedBlackNode<T> x) {
        // get y
        RedBlackNode<T> y = x.getRight();

        // x-b relation
        x.setRight(y.getLeft());
        if (y.getLeft() != nil) {
            y.getLeft().setParent(x);
        }

        // x.parent-y relation
        y.setParent(x.getParent());
        if (x.getParent() == nil) {
            root = y;
        }
        else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        }
        else {
            x.getParent().setRight(y);
        }

        // y-x relation
        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotation(RedBlackNode<T> y) {
        // get x
        RedBlackNode<T> x = y.getLeft();

        // y-b relation
        y.setLeft(x.getRight());
        if (x.getRight() != nil) {
            x.getRight().setParent(y);
        }

        // y.parent-x relation
        x.setParent(y.getParent());
        if (y.getParent() == nil) {
            root = x;
        }
        else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
        }
        else {
            y.getParent().setLeft(x);
        }

        // y-x relation
        x.setRight(y);
        y.setParent(x);
    }
}
