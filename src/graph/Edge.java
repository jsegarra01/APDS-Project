package graph;

public class Edge {

    private final int vertexA;
    private final int vertexB;
    private final float weight;

    public Edge(int vertexA, int vertexB, float weight) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.weight = weight;
    }

    public int getVertexA() {
        return vertexA;
    }

    public int getVertexB() {
        return vertexB;
    }

    public float getWeight() {
        return weight;
    }
}
