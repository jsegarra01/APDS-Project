package model.graph;

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

    public void DFS(Graph graph, GraphNode node) {
        visited[mapping[node.getId()]] = true;
        
        for (GraphNode adjacent : graph.getAdjacentVertices(node)) {
            if (!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("INTEREST")) {
                System.out.println("\t" + GraphNode.toString(adjacent));
                DFS(graph, adjacent);
            }
        }
    }

    public void BFS(Graph graph, GraphNode node) {
        Queue<GraphNode> queue = new ArrayDeque<>();

        queue.add(node);
        visited[mapping[node.getId()]] = true;

        System.out.println("\t" + GraphNode.toString(node));
        while (!queue.isEmpty()) {
            GraphNode newGraphNode = queue.poll();

            for (GraphNode adjacent: graph.getAdjacentVertices(newGraphNode)) {
                if (!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("DANGER")) {
                    queue.add(adjacent);
                    System.out.println("\t" + GraphNode.toString(adjacent));
                    visited[mapping[adjacent.getId()]] = true;
                }
            }
        }
    }

    public Graph MST(Graph graph, GraphNode origin) {
        List<GraphNode> vertices = new ArrayList<>();
        List<GraphEdge> graphEdges = new ArrayList<>();
        int disconnectedVertices = graph.getNumOfDisconnectedVertices();

        vertices.add(origin);
        visited[mapping[origin.getId()]] = true;

        while (vertices.size() < graph.getNumNodes() - disconnectedVertices) {
            GraphEdge nextGraphEdge = getLeastWeightEdge(graph, vertices);

            visited[mapping[nextGraphEdge.getVertexB()]] = true;
            minWeight += nextGraphEdge.getWeight();

            vertices.add(graph.getNode(nextGraphEdge.getVertexB()));
            graphEdges.add(nextGraphEdge);
        }

        return new Graph(graphEdges, vertices);
    }

    private GraphEdge getLeastWeightEdge(Graph graph, List<GraphNode> vertices) {
        GraphEdge minGraphEdge = null;

        float minWeight = Float.MAX_VALUE;
        float adjacentWeight;

        for (GraphNode graphNode : vertices) {
            for (GraphNode adjacent : graph.getAdjacentVertices(graphNode)) {
                adjacentWeight = graph.getWeight(graphNode, adjacent);
                if (!visited[mapping[adjacent.getId()]] && adjacentWeight < minWeight) {
                    minGraphEdge = new GraphEdge(graphNode.getId(), adjacent.getId(), adjacentWeight);
                    minWeight = adjacentWeight;
                }
            }
        }

        return minGraphEdge;
    }

    public List<Integer> Dijkstra(Graph graph, GraphNode start, GraphNode end) {
        List<Integer> path = new ArrayList<>();
        float[] distances = new float[graph.getNumNodes()];
        int[] parents = new int[graph.getNumNodes()];
        float newAdjacentWeight;

        Arrays.fill(distances, Float.MAX_VALUE);
        distances[mapping[start.getId()]] = 0;
        parents[mapping[start.getId()]] = -1;

        GraphNode current = start;
        while (!visited[mapping[end.getId()]]) {
            List<GraphNode> adjacents = graph.getAdjacentVertices(current);
            boolean allDanger = areAllDangerous(adjacents);

            for (GraphNode adjacent : adjacents) {
                // Second condition modifies Dijkstra to avoid dangerous places unless there is no other choice
                if (!visited[mapping[adjacent.getId()]] && (allDanger || !adjacent.getType().equals("DANGER"))) {
                    newAdjacentWeight = distances[mapping[current.getId()]] + graph.getWeight(current, adjacent);
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

    private boolean areAllDangerous(List<GraphNode> adjacents) {
        for (GraphNode graphNode : adjacents) {
            if (graphNode.getType().equals("INTEREST")) {
                return false;
            }
        }
        return true;
    }

    private GraphNode getNextVertex(Graph graph, float[] distances) {
        float minDistance = Float.MAX_VALUE;
        int vertexID = 0;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                vertexID = i;
            }
        }

        return graph.getUnmappedNode(vertexID);
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
