package graph;

import java.io.IOException;
import java.util.List;

public class GraphManager {

    private final Graph graph;
    private final GraphTraversal graphTraversal;

    public GraphManager() throws IOException {
        graph = new Graph(GraphDAO.parseEdge(), GraphDAO.parseVertex());
        graphTraversal = new GraphTraversal(graph.getNumVertices(), graph.getMapping());

        graph.initGraph();
    }

    public boolean validateVertex(int vertexID) {
        return graph.isVertex(vertexID) && !graph.isVertexDisconnected(vertexID);
    }

    public boolean validatePointOfInterest(int vertexID) {
        return graph.isPointOfInterest(vertexID);
    }

    public boolean validateDangerousPlace(int vertexID) {
        return graph.isDangerousPlace(vertexID);
    }

    public void getPointsOfInterest(int vertexID) {
        graphTraversal.clearVisited();

        System.out.println("\t" + graph.getVertex(vertexID).getName());
        graphTraversal.DFS(graph, graph.getVertex(vertexID));
    }

    public void showDangerousPlaces(int vertexID) {
        graphTraversal.clearVisited();
        graphTraversal.BFS(graph, graph.getVertex(vertexID));
    }

    public void showUniversalNauticalChart(int vertexID) {
        graphTraversal.clearVisited();
        graphTraversal.clearMinWeight();
        Graph MST = graphTraversal.MST(graph, graph.getVertex(vertexID));

        for (Edge edge : MST.getEdgeList()) {
            System.out.println("\t(" + edge.getVertexA() + ") --[" + edge.getWeight() + "]--> (" + edge.getVertexB() + ")");
        }
        System.out.println("\nThe minimum SPT cost is: " + graphTraversal.getMinWeight());
    }

    public void searchShortestPath(int startVertexID, int endVertexID) {
        graphTraversal.clearVisited();
        List<Integer> path = graphTraversal.Dijkstra(graph, graph.getVertex(startVertexID), graph.getVertex(endVertexID));

        for (int i = 0; i < path.size() - 1; i++) {
            System.out.println("\t(" + path.get(i) + ") --["
                    + graph.getEdgeWeight(graph.getVertex(path.get(i)), graph.getVertex(path.get(i + 1)))
                    + "]--> (" + path.get(i + 1) + ")");
        }
        System.out.println("\nThe shortest path cost is: " + graphTraversal.getMinDistance());
    }

    public String graphToString() {
        return graph.toString();
    }
}
