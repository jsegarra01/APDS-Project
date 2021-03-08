package graph;

import java.io.IOException;

public class GraphManager {

    private final Graph graph;
    private final GraphTraversal graphTraversal;

    public GraphManager() throws IOException {
        graph = new Graph(GraphDAO.parseEdge(), GraphDAO.parseVertex());
        graphTraversal = new GraphTraversal(graph.getNumVertices(), graph.getMapping());

        graph.initGraph();
    }

    public boolean validateVertex(int vertexID) {
        return graph.isVertex(vertexID);
    }

    public void showPointsOfInterest(int vertexID) {
        graphTraversal.resetVisited();
        graphTraversal.DFS(graph, graph.getVertex(vertexID));
    }

    public void showDangerousPlaces(int vertexID) {
        graphTraversal.resetVisited();
        graphTraversal.BFS(graph, graph.getVertex(vertexID));
    }

    public void showUniversalNauticalChart(int vertexID) {
        graphTraversal.resetVisited();
        Graph MST = graphTraversal.MST(graph, graph.getVertex(vertexID));
    }

    public String graphToString() {
        return graph.toString();
    }
}
