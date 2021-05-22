package model.graph;

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

    public static List<GraphEdge> parseEdge() throws IOException {
        List<GraphEdge> graphEdgeList = new ArrayList<>();

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
            graphEdgeList.add(new GraphEdge(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Float.parseFloat(info[2])));
        }

        bufferedReader.close();
        return graphEdgeList;
    }
}
