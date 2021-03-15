package graph;

public class Distance {

    private final int vertexA;
    private final int vertexB;
    private final float weight;

    public Distance(int vertexA, int vertexB, float weight) {
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
