package graph;

import java.util.*;
import java.util.List;

public class GraphTraversal {

    private final boolean[] visited;
    private final int[] mapping;

    private float minWeight;
    private float minDistance;

    public GraphTraversal(int numVertices, int[] mapping) {
        this.visited = new boolean[numVertices];
        this.mapping = mapping.clone();
    }

    public float getMinDistance() {
        return minDistance;
    }

    public float getMinWeight() {
        return minWeight;
    }

    public void clearVisited(){
        Arrays.fill(visited, false);
    }

    public void clearMinWeight() {
        minWeight = 0;
    }

    public void DFS(Graph graph, Vertex vertex) {
        visited[mapping[vertex.getId()]] = true;
        
        for (Vertex adjacent : graph.getAdjacentVertices(vertex)) {
            if (!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("INTEREST")) {
                System.out.println("\t" + adjacent.getName());
                DFS(graph, adjacent);
            }
        }
    }

    public void BFS(Graph graph, Vertex vertex) {
        Queue<Vertex> queue = new ArrayDeque<>();

        queue.add(vertex);
        visited[mapping[vertex.getId()]] = true;

        System.out.println("\t" + vertex.getName());
        while (!queue.isEmpty()) {
            Vertex newVertex = queue.poll();

            for (Vertex adjacent: graph.getAdjacentVertices(newVertex)) {
                if (!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("DANGER")) {
                    queue.add(adjacent);
                    System.out.println("\t" + adjacent.getName());
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

            visited[mapping[nextEdge.getVertexB()]] = true;
            minWeight += nextEdge.getWeight();

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

        return minEdge;
    }

    public List<Integer> Dijkstra(Graph graph, Vertex start, Vertex end) {
        List<Integer> path = new ArrayList<>();
        float[] distances = new float[graph.getNumVertices()];
        int[] parents = new int[graph.getNumVertices()];
        float newAdjacentWeight;

        Arrays.fill(distances, Float.MAX_VALUE);
        distances[mapping[start.getId()]] = 0;
        parents[mapping[start.getId()]] = -1;

        Vertex current = start;
        while (!visited[mapping[end.getId()]]) {
            List<Vertex> adjacents = graph.getAdjacentVertices(current);
            boolean allDanger = areAllDangerous(adjacents);

            for (Vertex adjacent : adjacents) {
                // Second condition modifies Dijkstra to avoid dangerous places unless there is no other choice
                if (!visited[mapping[adjacent.getId()]] && (allDanger || !adjacent.getType().equals("DANGER"))) {
                    newAdjacentWeight = distances[mapping[current.getId()]] + graph.getEdgeWeight(current, adjacent);
                    if (distances[mapping[adjacent.getId()]] > newAdjacentWeight) {
                        distances[mapping[adjacent.getId()]] = newAdjacentWeight;
                        parents[mapping[adjacent.getId()]] = current.getId();
                    }
                }
            }
            visited[mapping[current.getId()]] = true;
            current = getNextVertex(graph, distances);
        }

        minDistance = distances[mapping[end.getId()]];
        return getPath(path, parents, end.getId(), 0);
    }

    private boolean areAllDangerous(List<Vertex> adjacents) {
        for (Vertex vertex : adjacents) {
            if (vertex.getType().equals("INTEREST")) {
                return false;
            }
        }
        return true;
    }

    private Vertex getNextVertex(Graph graph, float[] distances) {
        float minDistance = Float.MAX_VALUE;
        int vertexID = 0;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                vertexID = i;
            }
        }

        return graph.getUnmappedVertex(vertexID);
    }

    private List<Integer> getPath(List<Integer> path, int[] parent, int destination, int position){
        if (parent[mapping[destination]] == -1) {
            path.add(destination);
            return path;
        }
        path = getPath(path, parent, parent[mapping[destination]], position + 1);
        path.add(destination);
        return path;
    }
}
