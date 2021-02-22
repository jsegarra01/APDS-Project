import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DataReader reader = new DataReader();

        try {
            reader.parseData("graphXXS.paed");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph graph = new Graph(reader.getVertexList());
        graph.initGraph(reader.getEdgeList());

        System.out.print(graph.toString());
    }
}
