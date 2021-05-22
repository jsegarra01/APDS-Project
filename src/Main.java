import model.graph.*;
import model.tables.TablesManager;
import model.trees.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            GraphManager graphManager = new GraphManager();
            TreesManager treesManager = new TreesManager();
            TablesManager tablesManager = new TablesManager();

            new Controller(graphManager, treesManager, tablesManager).run();

        } catch (IOException e) {
            System.out.println("ERROR: Unexpected problem reading the datasets");
            e.printStackTrace();
        }
    }
}
