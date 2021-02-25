import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class GraphTraversal {

    private boolean[] visited;
    private int[] mapping;


    public GraphTraversal(int numVertices, int[] mapping) {
        this.visited = new boolean[numVertices];
        this.mapping = mapping.clone();
    }

    public void resetVisited(boolean[] visited){
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
        Queue<Vertex> queue = new ArrayDeque();
        Vertex node;
        resetVisited(visited);

        queue.add(vertex);
        visited[mapping[vertex.getId()]] = true;

        while(!queue.isEmpty()) {
            node = queue.poll();
            for (Vertex adjacent: graph.getAdjacentVertices(node)) {
                //System.out.println(adjacent.getName());
                if(!visited[mapping[adjacent.getId()]] && adjacent.getType().equals("DANGER")) {
                    queue.add(adjacent);
                    visited[mapping[adjacent.getId()]] = true;
                    System.out.println(adjacent.getName());
                }
            }
        }
    }
}
