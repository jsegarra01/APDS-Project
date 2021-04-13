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
        fillWithZeros();

        numNodes = graphNodeList.size();
        setMapping();
    }

    public List<GraphEdge> getEdgeList() {
        return graphEdgeList;
    }

    public int getNumNodes() {
        return numNodes;
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

    public int[] getMapping() {
        return mapping;
    }

    public GraphNode getNode(int nodeID) {
        return graphNodeList.get(mapping[nodeID]);
    }

    public GraphNode getUnmappedVertex(int nodeID) {
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

    private void fillWithZeros() {
        for (float[] row : adjacentMatrix) {
            Arrays.fill(row, 0.0f);
        }
    }

    private void setMapping() {
        mapping = new int[graphNodeList.size() * 2];

        Arrays.fill(mapping, -1);
        for (int i = 0; i < numNodes; i++) {
            mapping[graphNodeList.get(i).getId()] = i;
        }
    }

    public void initGraph() {
        for (GraphEdge graphEdge : graphEdgeList) {
            addEdge(graphEdge.getVertexA(), graphEdge.getVertexB(), graphEdge.getWeight());
            addEdge(graphEdge.getVertexB(), graphEdge.getVertexA(), graphEdge.getWeight());
        }
    }

    public void addEdge(int vertexA, int vertexB, Float weight) {
        adjacentMatrix[mapping[vertexA]][mapping[vertexB]] = weight;
    }

    public Float getEdgeWeight(GraphNode A, GraphNode B) {
        return adjacentMatrix[mapping[A.getId()]][mapping[B.getId()]];
    }

    public List<GraphNode> getAdjacentVertices(GraphNode graphNode) {
        List<GraphNode> adjacents = new ArrayList<>();

        int i = mapping[graphNode.getId()];
        for (int j = 0; j < numNodes; j++) {
            if (adjacentMatrix[i][j] != 0.0f) {
                adjacents.add(graphNodeList.get(j));
            }
        }

        return adjacents;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        for (float[] row : adjacentMatrix) {
            string.append(Arrays.toString(row)).append('\n');
        }

        return string.toString();
    }
}
