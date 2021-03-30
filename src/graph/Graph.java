package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private float[][] adjacentMatrix;
    private int[] mapping;
    private int numNodes;

    private final List<Edge> edgeList;
    private final List<GraphNode> graphNodeList;

    public Graph() {
        edgeList = new ArrayList<>();
        graphNodeList = new ArrayList<>();
    }

    public Graph(List<Edge> edgeList, List<GraphNode> graphNodeList) {
        this.edgeList = new ArrayList<>(edgeList);
        this.graphNodeList = new ArrayList<>(graphNodeList);

        this.adjacentMatrix = new float[graphNodeList.size()][graphNodeList.size()];
        fillWithZeros();

        numNodes = graphNodeList.size();
        setMapping();
    }

    public List<Edge> getEdgeList() {
        return edgeList;
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

    public GraphNode getVertex(int vertexID) {
        return graphNodeList.get(mapping[vertexID]);
    }

    public GraphNode getUnmappedVertex(int vertexID) {
        return graphNodeList.get(vertexID);
    }

    public boolean isVertex(int vertexID) {
        return mapping[vertexID] != -1;
    }

    public boolean isPointOfInterest(int vertexID) {
        return getVertex(vertexID).getType().equals("INTEREST");
    }

    public boolean isDangerousPlace(int vertexID) {
        return getVertex(vertexID).getType().equals("DANGER");
    }

    public boolean isVertexDisconnected(int vertexID) {
        return getAdjacentVertices(getVertex(vertexID)).size() == 0;
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
        for (Edge edge : edgeList) {
            addEdge(edge.getVertexA(), edge.getVertexB(), edge.getWeight());
            addEdge(edge.getVertexB(), edge.getVertexA(), edge.getWeight());
        }
    }

    public void addEdge(int vertexA, int vertexB, float weight) {
        adjacentMatrix[mapping[vertexA]][mapping[vertexB]] = weight;
    }

    public float getEdgeWeight(GraphNode A, GraphNode B) {
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
