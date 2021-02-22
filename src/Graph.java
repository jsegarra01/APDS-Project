import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private final float[][] adjacentMatrix;
    private int[] mapping;

    private final List<Edge> edgeList;
    private final List<Vertex> vertexList;

    public Graph(List<Edge> edgeList, List<Vertex> vertexList) {
        this.edgeList = new ArrayList<>(edgeList);
        this.vertexList = new ArrayList<>(vertexList);

        this.adjacentMatrix = new float[vertexList.size()][vertexList.size()];
        fillWithZeros();

        setMapping();
    }

    public int[] getMapping() {
        return mapping;
    }

    private void fillWithZeros() {
        for (float[] row : adjacentMatrix) {
            Arrays.fill(row, 0.0f);
        }
    }

    private void setMapping() {
        mapping = new int[vertexList.size() * 2];

        Arrays.fill(mapping, -1);
        for (int i = 0; i < vertexList.size(); i++) {
            mapping[vertexList.get(i).getId()] = i;
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

    public List<Vertex> getAdjacentVertices(Vertex vertex) {
        List<Vertex> adjacents = new ArrayList<>();
        int i = mapping[vertex.getId()];

        for (int j = 0; j < vertexList.size(); j++) {
            if (adjacentMatrix[i][j] != 0.0f) {
                adjacents.add(vertexList.get(j));
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
