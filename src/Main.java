import graph.GraphManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GraphManager graphManager = null;

        try {
            graphManager = new GraphManager();
        } catch (IOException e) {
            System.out.println("ERROR: Unexpected problem reading the datasets");
        }

        new Controller(graphManager).run();
    }
}
