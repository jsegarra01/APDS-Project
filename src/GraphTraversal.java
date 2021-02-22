public class GraphTraversal {

    private boolean[] visited;
    private int[] mapping;

    public GraphTraversal(int numVertices, int[] mapping) {
        this.visited = new boolean[numVertices];
        this.mapping = mapping.clone();
    }

    public void DFS(Graph graph, Vertex vertex) {
        visited[mapping[vertex.getId()]] = true;

        for (Vertex adjacent : graph.getAdjacentVertices(vertex)) {
            if (adjacent.getType().equals("INTEREST") && !visited[mapping[adjacent.getId()]]) {
                DFS(graph, adjacent);
            }
        }
    }
}
