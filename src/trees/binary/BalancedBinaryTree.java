package trees.binary;

public class BalancedBinaryTree extends BinaryTree {

    @Override
    public void addNode(BinaryNode newNode) {
        super.addNode(newNode);

        if (newNode.getParent() != null) {
            balanceAddition(newNode);
        }
    }

    @Override
    public void removeNode(BinaryNode node) {
        super.removeNode(node);
    }

    private void balanceAddition(BinaryNode newNode){
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

        if (node.getRightChild() == null && node.getLeftChild() == null){
            return 0;
        }
        leftHeight = getNodeHeight(node.getLeftChild());
        rightHeight = getNodeHeight(node.getRightChild());

        return Math.max(leftHeight, rightHeight) + 1;
    }

}
