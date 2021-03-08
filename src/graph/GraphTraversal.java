package graph;

import java.util.*;
import java.util.List;

public class GraphTraversal {

    private final boolean[] visited;
    private final int[] mapping;

    private float MSTWeight;

    public GraphTraversal(int numVertices, int[] mapping) {
        this.visited = new boolean[numVertices];
        this.mapping = mapping.clone();
    }

    public void resetVisited(){
        Arrays.fill(visited, false);
    }

    public void DFS(Graph graph, Vertex vertex) {
        visited[mapping[vertex.getId()]] = true;

        for (Vertex adjacent : graph.getAdjacentVertices(vertex)) {
            if (adjacent.getType().equals("INTEREST") && !visited[mapping[adjacent.getId()]]) {
                System.out.println("\t" + adjacent.getName());
                DFS(graph, adjacent);
            }
        }
    }

    public void BFS(Graph graph, Vertex vertex) {
        Queue<Vertex> queue = new ArrayDeque<>();

        queue.add(vertex);
        visited[mapping[vertex.getId()]] = true;

        while (!queue.isEmpty()) {
            Vertex newVertex = queue.poll();

            for (Vertex adjacent: graph.getAdjacentVertices(newVertex)) {
                if (adjacent.getType().equals("DANGER") && !visited[mapping[adjacent.getId()]]) {
                    System.out.println("\t" + adjacent.getName());
                    queue.add(adjacent);
                    visited[mapping[adjacent.getId()]] = true;
                }
            }
        }
    }

    public Graph MST(Graph graph, Vertex origin) {
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        int disconnectedVertices = graph.getNumOfDisconnectedVertices();

        vertices.add(origin);
        visited[mapping[origin.getId()]] = true;

        while (vertices.size() < graph.getNumVertices() - disconnectedVertices) {
            Edge nextEdge = getLeastWeightEdge(graph, vertices);

            vertices.add(graph.getVertex(nextEdge.getVertexB()));
            edges.add(nextEdge);
        }

        return new Graph(edges, vertices);
    }

    private Edge getLeastWeightEdge(Graph graph, List<Vertex> vertices) {
        Edge minEdge = null;

        float minWeight = Float.MAX_VALUE;
        float adjacentWeight;

        for (Vertex vertex : vertices) {
            for (Vertex adjacent : graph.getAdjacentVertices(vertex)) {
                adjacentWeight = graph.getEdgeWeight(vertex, adjacent);
                if (!visited[mapping[adjacent.getId()]] && adjacentWeight < minWeight) {
                    minEdge = new Edge(vertex.getId(), adjacent.getId(), adjacentWeight);
                    minWeight = adjacentWeight;
                }
            }
        }

        visited[mapping[minEdge.getVertexB()]] = true;
        MSTWeight += minEdge.getWeight();
        return minEdge;
    }
}
