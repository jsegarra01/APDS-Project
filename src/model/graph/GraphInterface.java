package model.graph;

import java.util.List;

public interface GraphInterface {

    void addEdge(int nodeA, int nodeB, float weight);

    GraphNode getNode(int nodeID);

    float getWeight(GraphNode nodeA, GraphNode nodeB);

    List<GraphNode> getAdjacentVertices(GraphNode node);

    boolean isNode(int nodeID);
}
