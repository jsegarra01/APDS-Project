package model.graph;

public class GraphEdge {

    private final int vertexA;
    private final int vertexB;
    private final float weight;

    public GraphEdge(int vertexA, int vertexB, float weight) {
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

    public static String toString(GraphEdge graphEdge) {
        return "(" + graphEdge.vertexA + ") --[" + graphEdge.weight + "]--> (" + graphEdge.vertexB + ")";
    }
}
