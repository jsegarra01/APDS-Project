package model.trees.r;

import java.util.ArrayList;
import java.util.List;

public class RContainer {

    private RContainer parentContainer;
    private RRectangle parent;
    private boolean isLeaf;
    private List<RNode> nodes;

    public RContainer() {
        parentContainer = null;
        parent = null;
        isLeaf = true;

        nodes = new ArrayList<>();
    }

    public RContainer getParentContainer() {
        return parentContainer;
    }

    public void setParentContainer(RContainer parentContainer) {
        this.parentContainer = parentContainer;
    }

    public RRectangle getParent() {
        return parent;
    }

    public void setParent(RRectangle parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf() {
        isLeaf = !(nodes.get(0) instanceof RRectangle);
    }

    public List<RNode> getNodes() {
        return nodes;
    }

    public void insertNode(RNode node) {
        nodes.add(node);

        // update the point (top-left)  of the parent rectangle
        if (getParent() != null) this.getParent().updateSize(node);
    }

    public void removeNode(RNode node) {
        nodes.remove(node);
    }

    public boolean isOverload() {
        return nodes.size() > RTree.ORDER;
    }

}
