package graph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GraphDAO {

    private static final String PATH = "res/Graphs/graphXXS.paed";

    public static List<GraphNode> parseNodes() throws IOException {
        List<GraphNode> graphNodeList = new ArrayList<>();

        File file = new File(PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            graphNodeList.add(new GraphNode(Integer.parseInt(info[0]), info[1], info[2]));
        }

        bufferedReader.close();
        return graphNodeList;
    }

    public static List<Edge> parseEdge() throws IOException {
        List<Edge> edgeList = new ArrayList<>();

        File file = new File(PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        // Skip vertices
        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            bufferedReader.readLine();
        }

        counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            edgeList.add(new Edge(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Float.parseFloat(info[2])));
        }

        bufferedReader.close();
        return edgeList;
    }
}
