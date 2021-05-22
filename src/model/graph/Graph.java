package model.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private float[][] adjacentMatrix;
    private int[] mapping;
    private int numNodes;

    private final List<GraphEdge> graphEdgeList;
    private final List<GraphNode> graphNodeList;

    public Graph() {
        graphEdgeList = new ArrayList<>();
        graphNodeList = new ArrayList<>();
    }

    public Graph(List<GraphEdge> graphEdgeList, List<GraphNode> graphNodeList) {
        this.graphEdgeList = new ArrayList<>(graphEdgeList);
        this.graphNodeList = new ArrayList<>(graphNodeList);

        this.adjacentMatrix = new float[graphNodeList.size()][graphNodeList.size()];
        for (float[] row : adjacentMatrix) {
            Arrays.fill(row, 0.0f);
        }

        numNodes = graphNodeList.size();
        setMapping();
    }

    public List<GraphEdge> getEdgeList() {
        return graphEdgeList;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int[] getMapping() {
        return mapping;
    }

    public GraphNode getNode(int nodeID) {
        return graphNodeList.get(mapping[nodeID]);
    }

    public float getWeight(GraphNode nodeA, GraphNode nodeB) {
        return adjacentMatrix[mapping[nodeA.getId()]][mapping[nodeB.getId()]];
    }

    public GraphNode getUnmappedNode(int nodeID) {
        return graphNodeList.get(nodeID);
    }

    public boolean isNode(int nodeID) {
        return mapping[nodeID] != -1;
    }

    public boolean isPointOfInterest(int nodeID) {
        return getNode(nodeID).getType().equals("INTEREST");
    }

    public boolean isDangerousPlace(int nodeID) {
        return getNode(nodeID).getType().equals("DANGER");
    }

    public boolean isNodeDisconnected(int nodeID) {
        return getAdjacentVertices(getNode(nodeID)).size() == 0;
    }

    public int getNumOfDisconnectedVertices() {
        int numOfDisconnectedVertices = 0;

        for (GraphNode graphNode : graphNodeList) {
            if (getAdjacentVertices(graphNode).size() == 0){
                numOfDisconnectedVertices++;
            }
        }

        return numOfDisconnectedVertices;
    }

    private void setMapping() {
        mapping = new int[2 * graphNodeList.size()];

        Arrays.fill(mapping, -1);
        for (int i = 0; i < numNodes; i++) {
            mapping[graphNodeList.get(i).getId()] = i;
        }
    }

    public void addEdge(int vertexA, int vertexB, float weight) {
        adjacentMatrix[mapping[vertexA]][mapping[vertexB]] = weight;
    }

    public List<GraphNode> getAdjacentVertices(GraphNode node) {
        List<GraphNode> adjacents = new ArrayList<>();

        int i = mapping[node.getId()];
        for (int j = 0; j < numNodes; j++) {
            if (adjacentMatrix[i][j] != 0.0f) {
                adjacents.add(graphNodeList.get(j));
            }
        }

        return adjacents;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (float[] row : adjacentMatrix) {
            string.append(Arrays.toString(row)).append('\n');
        }

        return string.toString();
    }
}
