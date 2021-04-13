import model.graph.*;
import model.trees.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GraphManager graphManager = null;
        TreesManager treesManager = null;

        try {
            graphManager = new GraphManager();
            treesManager = new TreesManager();
        } catch (IOException e) {
            System.out.println("ERROR: Unexpected problem reading the datasets");
        }

        new Controller(graphManager, treesManager).run();
    }
}
