import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final List<Edge> edgeList;
    private final List<Vertex> vertexList;

    private static final String DIR = "res/Graphs/";

    public DataReader() {
        edgeList = new ArrayList<>();
        vertexList = new ArrayList<>();
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void parseData(String fileName) throws IOException {
        String path = DIR + fileName;

        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            vertexList.add(new Vertex(Integer.parseInt(info[0]), info[1], info[2]));
        }

        counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            edgeList.add(new Edge(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Float.parseFloat(info[2])));
        }
    }
}
