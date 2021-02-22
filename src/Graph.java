import java.util.Arrays;
import java.util.List;

public class Graph {

    private final int numVertices;

    private final float[][] adjacentMatrix;
    private int[] mapping;

    public Graph(List<Vertex> vertexList) {
        numVertices = vertexList.size();
        adjacentMatrix = new float[numVertices][numVertices];
        fillWithZeros();

        setMapping(vertexList);
        System.out.println(Arrays.toString(mapping));
    }

    private void fillWithZeros() {
        for (float[] row : adjacentMatrix) {
            Arrays.fill(row, 0.0f);
        }
    }

    private void setMapping(List<Vertex> vertexList) {
        mapping = new int[numVertices * 2];

        Arrays.fill(mapping, -1);
        for (int i = 0; i < numVertices; i++) {
            mapping[vertexList.get(i).getId()] = i;
        }
    }

    public void initGraph(List<Edge> edgeList) {
        for (Edge edge : edgeList) {
            addEdge(edge.getVertexA(), edge.getVertexB(), edge.getWeight());
            addEdge(edge.getVertexB(), edge.getVertexA(), edge.getWeight());
        }
    }

    public void addEdge(int vertexA, int vertexB, float weight) {
        adjacentMatrix[mapping[vertexA]][mapping[vertexB]] = weight;
    }

    public void removeEdge(int vertexA, int vertexB) {
        adjacentMatrix[mapping[vertexA]][mapping[vertexB]] = 0.0f;
    }

    public int[] getAdjacentVertices(int vertex) {
        int[] adjacents = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (adjacentMatrix[vertex][i] == 0.0f) {
                adjacents[i] = -1;
            }
            else {
                adjacents[i] = i;
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
