import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static final String DIR = "res/Graphs/";

    public static List<Vertex> parseVertex(String fileName) throws IOException {
        List<Vertex> vertexList = new ArrayList<>();
        String path = DIR + fileName;

        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            vertexList.add(new Vertex(Integer.parseInt(info[0]), info[1], info[2]));
        }

        return vertexList;
    }

    public static List<Edge> parseEdge(String fileName) throws IOException {
        List<Edge> edgeList = new ArrayList<>();
        String path = DIR + fileName;

        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            bufferedReader.readLine();
        }

        counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            edgeList.add(new Edge(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Float.parseFloat(info[2])));
        }

        return edgeList;
    }
}
