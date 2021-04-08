package trees.binary;

import static java.lang.StrictMath.abs;

public class BinaryTree {

    private BinaryNode root;

    public void addNode(BinaryNode newNode) {
        if (root == null) {
            root = newNode;
        } else {
            add(root, newNode);
        }
    }

    private void add(BinaryNode node, BinaryNode newNode) {
        // Left node
        if (newNode.getValue() < node.getValue()) {
            // Leaf node
            if (node.getLeftChild() == null) {
                newNode.setParent(node);
                node.setLeftChild(newNode);
            } else {
                add(node.getLeftChild(), newNode);
            }
        }
        // Right node
        else if (newNode.getValue() > node.getValue()) {
            // Leaf node
            if (node.getRightChild() == null) {
                newNode.setParent(node);
                node.setRightChild(newNode);
            }
            else {
                add(node.getRightChild(), newNode);
            }
        }
    }

    public void removeNode(String nodeName){
            //we will have to find the node we want to delete
        BinaryNode node = null; //Temporary representation of our node
        BinaryNode auxNode; //Node that allows us to only affect the parent when it is not the root
        
        //Adapt the find closest value without affecting the parent
        if(node.getParent() == null){
            root = childConcatenance(node);
            root.setParent(null);
        }
        else{
            auxNode = childConcatenance(node);
            setChild(node, auxNode);
        }
        
    }

    private BinaryNode childConcatenance(BinaryNode node){
        BinaryNode auxNode;

        if (node.getLeftChild() == null && node.getRightChild() == null) {
            return null;
        }
        if (node.getRightChild() == null) {
            auxNode = findClosestValueLeft(node.getLeftChild());
            setChild(auxNode, auxNode.getLeftChild());
        }
        else{
            auxNode = findClosestValueRight(node.getRightChild());
            setChild(auxNode, auxNode.getRightChild());
        }
        auxNode.setRightChild(node.getRightChild());
        auxNode.setLeftChild(node.getLeftChild());
        
        return auxNode;
    }

    private BinaryNode findClosestValueRight(BinaryNode root) {
        return root.getLeftChild() == null ? root : findClosestValueRight(root.getLeftChild());
    }
    private BinaryNode findClosestValueLeft(BinaryNode root) {
        return root.getRightChild() == null ? root : findClosestValueLeft(root.getRightChild());
    }

    private void setChild(BinaryNode root, BinaryNode node){
        if(root.getParent().getRightChild() == root){
            root.getParent().setRightChild(node);
        }
        else{
            root.getParent().setLeftChild(node);
        }
        node.setParent(root.getParent());
    }

    private void balance(BinaryNode newNode){
        int bf = getBalanceFactor(newNode.getParent());
        int bfParent = getBalanceFactor(newNode.getParent().getParent());

        if(bf == 1 && bfParent == -2){
            leftRotate(newNode.getParent().getParent());
        }
        if(bf == -1 && bfParent == 2){
            rightRotate(newNode.getParent().getParent());
        }
        if(bfParent == 2){
            leftRotate(newNode.getParent().getParent());
        }
        if(bfParent == -2){
            rightRotate(newNode.getParent().getParent());
        }

    }

    private void rightRotate(BinaryNode node){
        BinaryNode B = node.getLeftChild();
        BinaryNode P = node.getParent();

        node.setLeftChild(B.getRightChild());
        if(B.getRightChild() != null){
            B.getRightChild().setParent(node);
        }
        B.setRightChild(node);
        node.setParent(B);
        B.setParent(P);
        if(P != null){
            if(P.getLeftChild() == node){
                P.setLeftChild(B);
            }
            else{
                P.setRightChild(B);
            }
        }
    }

    private void leftRotate(BinaryNode node){
        BinaryNode B = node.getRightChild();
        BinaryNode P = node.getParent();

        node.setRightChild(B.getLeftChild());
        if(B.getLeftChild() != null){
            B.getLeftChild().setParent(node);
        }
        B.setLeftChild(node);
        node.setParent(B);
        B.setParent(P);
        if(P != null){
            if(P.getRightChild() == node){
                P.setRightChild(B);
            }
            else{
                P.setLeftChild(B);
            }
        }
    }

    private int getBalanceFactor(BinaryNode node){
        return getNodeHeight(node.getRightChild()) - getNodeHeight(node.getLeftChild());
    }

    private int getNodeHeight(BinaryNode node){
        int leftHeight, rightHeight;

        if(node.getRightChild() == null && node.getLeftChild() == null){
            return 0;
        }
        leftHeight = getNodeHeight(node.getLeftChild());
        rightHeight = getNodeHeight(node.getRightChild());

        return Math.max(leftHeight, rightHeight) + 1;
    }

}
