package graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class GraphTraversal {

    private final boolean[] visited;
    private final int[] mapping;


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
                    queue.add(adjacent);
                    visited[mapping[adjacent.getId()]] = true;
                }
            }
        }
    }
}
