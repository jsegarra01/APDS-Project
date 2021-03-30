package graph;

import java.io.IOException;
import java.util.List;

public class GraphManager {

    private final Graph graph;
    private final GraphTraversal graphTraversal;

    public GraphManager() throws IOException {
        graph = new Graph(GraphDAO.parseEdge(), GraphDAO.parseNodes());
        graphTraversal = new GraphTraversal(graph.getNumNodes(), graph.getMapping());

        graph.initGraph();
    }

    public boolean validateVertex(int nodeID) {
        return graph.isVertex(nodeID) && !graph.isVertexDisconnected(nodeID);
    }

    public boolean validatePointOfInterest(int vertexID) {
        return graph.isPointOfInterest(vertexID);
    }

    public boolean validateDangerousPlace(int vertexID) {
        return graph.isDangerousPlace(vertexID);
    }

    public void getPointsOfInterest(int nodeID) {
        graphTraversal.clearVisited();

        System.out.println("\t" + graph.getVertex(nodeID).getName());
        graphTraversal.DFS(graph, graph.getVertex(nodeID));
    }

    public void showDangerousPlaces(int nodeID) {
        graphTraversal.clearVisited();
        graphTraversal.BFS(graph, graph.getVertex(nodeID));
    }

    public void showUniversalNauticalChart(int nodeID) {
        graphTraversal.clearVisited();
        graphTraversal.clearMinWeight();
        Graph MST = graphTraversal.MST(graph, graph.getVertex(nodeID));

        for (Edge edge : MST.getEdgeList()) {
            System.out.println("\t(" + edge.getVertexA() + ") --[" + edge.getWeight() + "]--> (" + edge.getVertexB() + ")");
        }
        System.out.println("\nThe minimum SPT cost is: " + graphTraversal.getMinWeight());
    }

    public void searchShortestPath(int startNodeID, int endNodeID) {
        graphTraversal.clearVisited();
        List<Integer> path = graphTraversal.Dijkstra(graph, graph.getVertex(startNodeID), graph.getVertex(endNodeID));

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
