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

    public void DFS(Graph graph, Place place) {
        visited[mapping[place.getId()]] = true;
        
        for (Place adjacent : graph.getAdjacentVertices(place)) {
            if (!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("INTEREST")) {
                System.out.println("\t" + adjacent.getName());
                DFS(graph, adjacent);
            }
        }
    }

    public void BFS(Graph graph, Place place) {
        Queue<Place> queue = new ArrayDeque<>();

        queue.add(place);
        visited[mapping[place.getId()]] = true;

        System.out.println("\t" + place.getName());
        while (!queue.isEmpty()) {
            Place newPlace = queue.poll();

            for (Place adjacent: graph.getAdjacentVertices(newPlace)) {
                if (!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("DANGER")) {
                    queue.add(adjacent);
                    System.out.println("\t" + adjacent.getName());
                    visited[mapping[adjacent.getId()]] = true;
                }
            }
        }
    }

    public Graph MST(Graph graph, Place origin) {
        List<Place> vertices = new ArrayList<>();
        List<Distance> distances = new ArrayList<>();
        int disconnectedVertices = graph.getNumOfDisconnectedVertices();

        vertices.add(origin);
        visited[mapping[origin.getId()]] = true;

        while (vertices.size() < graph.getNumVertices() - disconnectedVertices) {
            Distance nextDistance = getLeastWeightEdge(graph, vertices);

            visited[mapping[nextDistance.getVertexB()]] = true;
            minWeight += nextDistance.getWeight();

            vertices.add(graph.getVertex(nextDistance.getVertexB()));
            distances.add(nextDistance);
        }

        return new Graph(distances, vertices);
    }

    private Distance getLeastWeightEdge(Graph graph, List<Place> vertices) {
        Distance minDistance = null;

        float minWeight = Float.MAX_VALUE;
        float adjacentWeight;

        for (Place place : vertices) {
            for (Place adjacent : graph.getAdjacentVertices(place)) {
                adjacentWeight = graph.getEdgeWeight(place, adjacent);
                if (!visited[mapping[adjacent.getId()]] && adjacentWeight < minWeight) {
                    minDistance = new Distance(place.getId(), adjacent.getId(), adjacentWeight);
                    minWeight = adjacentWeight;
                }
            }
        }

        return minDistance;
    }

    public List<Integer> Dijkstra(Graph graph, Place start, Place end) {
        List<Integer> path = new ArrayList<>();
        float[] distances = new float[graph.getNumVertices()];
        int[] parents = new int[graph.getNumVertices()];
        float newAdjacentWeight;

        Arrays.fill(distances, Float.MAX_VALUE);
        distances[mapping[start.getId()]] = 0;
        parents[mapping[start.getId()]] = -1;

        Place current = start;
        while (!visited[mapping[end.getId()]]) {
            List<Place> adjacents = graph.getAdjacentVertices(current);
            boolean allDanger = areAllDangerous(adjacents);

            for (Place adjacent : adjacents) {
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

    private boolean areAllDangerous(List<Place> adjacents) {
        for (Place place : adjacents) {
            if (place.getType().equals("INTEREST")) {
                return false;
            }
        }
        return true;
    }

    private Place getNextVertex(Graph graph, float[] distances) {
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
