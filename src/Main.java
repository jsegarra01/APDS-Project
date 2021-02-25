import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DataReader reader = new DataReader();

        try {
            reader.parseData("graphXXS.paed");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph graph = new Graph(reader.getEdgeList(), reader.getVertexList());
        graph.initGraph();

        GraphTraversal DFS = new GraphTraversal(reader.getVertexList().size(), graph.getMapping());
        DFS.DFS(graph, reader.getVertexList().get(0));

        //System.out.print(graph.toString());

        UI ui = new UI();
        try {
            //ui.displayGeneralMenu();
            ui.displayGraphMenu();
        }
        catch(Exception e) {
            System.out.println("Something went wrong.");
        }
    }
}
