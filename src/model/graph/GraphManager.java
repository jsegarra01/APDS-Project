package model.graph;

import java.io.IOException;
import java.util.List;

public class GraphManager {

    private final Graph graph;
    private final GraphTraversal graphTraversal;

    public GraphManager() throws IOException {
        List<GraphNode> graphNodes = GraphDAO.parseNodes();
        List<GraphEdge> graphEdges = GraphDAO.parseEdge();

        graph = new Graph(graphEdges, graphNodes);
        graphTraversal = new GraphTraversal(graph.getNumNodes(), graph.getMapping());

        initGraph(graphEdges);
    }

    public void initGraph(List<GraphEdge> graphEdges) {
        for (GraphEdge graphEdge : graphEdges) {
            graph.addEdge(graphEdge.getVertexA(), graphEdge.getVertexB(), graphEdge.getWeight());
            graph.addEdge(graphEdge.getVertexB(), graphEdge.getVertexA(), graphEdge.getWeight());
        }
    }

    public boolean validateNode(int nodeID) {
        return graph.isNode(nodeID) && !graph.isNodeDisconnected(nodeID);
    }

    public boolean validatePointOfInterest(int vertexID) {
        return graph.isPointOfInterest(vertexID);
    }

    public boolean validateDangerousPlace(int vertexID) {
        return graph.isDangerousPlace(vertexID);
    }

    public void getPointsOfInterest(int nodeID) {
        graphTraversal.clearVisited();
        System.out.println("\t" + GraphNode.toString(graph.getNode(nodeID)));
        graphTraversal.DFS(graph, graph.getNode(nodeID));
    }

    public void showDangerousPlaces(int nodeID) {
        graphTraversal.clearVisited();
        graphTraversal.BFS(graph, graph.getNode(nodeID));
    }

    public void showUniversalNauticalChart(int nodeID) {
        graphTraversal.clearVisited();
        graphTraversal.clearMinWeight();
        Graph MST = graphTraversal.MST(graph, graph.getNode(nodeID));

        for (GraphEdge graphEdge : MST.getEdgeList()) {
            System.out.println("\t" + GraphEdge.toString(graphEdge));
        }
        System.out.println("\nThe minimum SPT cost is: " + graphTraversal.getMinWeight());
    }

    public void searchShortestPath(int startNodeID, int endNodeID) {
        graphTraversal.clearVisited();
        List<Integer> path = graphTraversal.Dijkstra(graph, graph.getNode(startNodeID), graph.getNode(endNodeID));

        for (int i = 0; i < path.size() - 1; i++) {
            System.out.println("\t(" + path.get(i) + ") --["
                    + graph.getWeight(graph.getNode(path.get(i)), graph.getNode(path.get(i + 1)))
                    + "]--> (" + path.get(i + 1) + ")");
        }
        System.out.println("\nThe shortest path cost is: " + graphTraversal.getMinDistance());
    }

    public void printGraph() {
        System.out.println(graph.toString());
    }
}
