import model.graph.*;
import model.tables.TablesManager;
import model.trees.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        GraphManager graphManager = null;
        TreesManager treesManager = null;
        TablesManager tablesManager = null;

        try {
            graphManager = new GraphManager();
            treesManager = new TreesManager();
            tablesManager = new TablesManager();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Unexpected problem reading the datasets");
        }

        new Controller(graphManager, treesManager, tablesManager).run();
    }
}
