package trees.binary;

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

}
