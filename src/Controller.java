import model.graph.GraphManager;
import model.tables.TablesManager;
import model.trees.TreesManager;
import view.*;
import view.menus.*;

public class Controller {

    // Managers
    private final GraphManager graphManager;
    private final TreesManager treesManager;
    private final TablesManager tablesManager;
    private final UIManager UI;

    // Menu's IDs
    private static final int GENERAL_ID = 1;
    private static final int GRAPHS_ID = 2;
    private static final int BINARY_TREE_ID = 3;
    private static final int BINARY_TREE_TRAVERSAL_ID = 4;
    private static final int R_TREE_ID = 5;
    private static final int TABLES_ID = 6;

    // Menu's options
    private GeneralMenuOptions generalOption;
    private GraphMenuOptions graphOption;
    private BTreeMenuOptions binaryTreeOption;
    private BTreeTraversalMenuOptions binaryTreeTraversalOption;
    private RTreeMenuOptions rTreeOption;
    private TablesMenuOptions tablesOption;

    public Controller(GraphManager graphManager, TreesManager treesManager, TablesManager tablesManager) {
        this.graphManager = graphManager;
        this.treesManager = treesManager;
        this.tablesManager = tablesManager;

        UI = new UIManager();
    }

    private void getOption(int menuID) {
        try {
            switch (menuID) {
                case 1 -> generalOption = UI.displayGeneralMenu();
                case 2 -> graphOption = UI.displayGraphMenu();
                case 3 -> binaryTreeOption = UI.displayBinaryTreeMenu();
                case 4 -> binaryTreeTraversalOption = UI.displayBTreeTraversalMenu();
                case 5 -> rTreeOption = UI.displayRTreeMenu();
                case 6 -> tablesOption = UI.displayTablesMenu();
            }
            UI.displayMessage("");
        }
        catch (InvalidInputException e) {
            UI.displayError(e.getUserInput() + " is not a valid option");
            getOption(menuID);
        }
    }

    // ---------------------------------------------------------------------------------
    // GENERAL MENU
    // ---------------------------------------------------------------------------------

    public void run() {
        do {
            getOption(GENERAL_ID);

            switch (generalOption) {
                case GRAPHS -> runRoute();
                case BINARY_TREES -> runInventory();
                case R_TREES -> runDeck();
                case TABLES -> runCrew();
            }
        } while (generalOption != GeneralMenuOptions.EXIT);

        UI.displayMessage("So long, comrade!");
    }

    // ---------------------------------------------------------------------------------
    // GRAPHS
    // ---------------------------------------------------------------------------------

    private void runRoute() {
        do {
            getOption(GRAPHS_ID);

            switch (graphOption) {
                case DFS -> showPointsOfInterest();
                case BFS -> showDangerousPlaces();
                case MST -> generateUniversalNauticalChart();
                case DIJKSTRA -> findOptimalRoute();
            }

        } while (graphOption != GraphMenuOptions.BACK);
    }

    private int askGraphNodeID(String node, final boolean checkType, final boolean isPointOfInterest) {
        int nodeID;

        while (true) {
            try {
                nodeID = UI.askInteger("Enter the " + node + " node's identifier: ");
                if (graphManager.validateNode(nodeID)) {
                    if (checkType) {
                        if (isPointOfInterest) {
                            if (graphManager.validatePointOfInterest(nodeID)) {
                                break;
                            }
                            else {
                                UI.displayError("The specified ID does not correspond to a point of interest");
                            }
                        }
                        else {
                            if (graphManager.validateDangerousPlace(nodeID)) {
                                break;
                            }
                            else {
                                UI.displayError("The specified ID does not correspond to a dangerous place");
                            }
                        }
                    }
                    else {
                        break;
                    }
                }
                else {
                   UI.displayError("The specified ID does not correspond to any node or it may be disconnected");
                }
            } catch (NumberFormatException e) {
                UI.displayError("The specified input is not an ID");
            }
        }
        return nodeID;
    }

    private void showPointsOfInterest() {
        int nodeID = askGraphNodeID("origin", true, true);

        UI.displayMessage("\nDFS found the following points of interest:");
        UI.displayMessage("");
        graphManager.getPointsOfInterest(nodeID);
        UI.displayMessage("");
    }

    private void showDangerousPlaces() {
        int nodeID = askGraphNodeID("origin", true, false);

        UI.displayMessage("\nBFS found the following dangerous places:");
        UI.displayMessage("");
        graphManager.showDangerousPlaces(nodeID);
        UI.displayMessage("");
    }

    private void generateUniversalNauticalChart() {
        int nodeID = askGraphNodeID("origin", false, false);

        UI.displayMessage("\nFinding the MST...");
        UI.displayMessage("");
        graphManager.showUniversalNauticalChart(nodeID);
        UI.displayMessage("");
    }

    private void findOptimalRoute() {
        int startNodeID = askGraphNodeID("origin", false, false);
        int endNodeID = askGraphNodeID("destination", false, false);

        UI.displayMessage("\nFinding the optimal route...");
        UI.displayMessage("");
        graphManager.searchShortestPath(startNodeID, endNodeID);
        UI.displayMessage("");
    }

    // ---------------------------------------------------------------------------------
    // BINARY TREES
    // ---------------------------------------------------------------------------------

    private void runInventory() {
        do {
            getOption(BINARY_TREE_ID);

            switch (binaryTreeOption) {
                case ADD -> addTreasureB();
                case REMOVE -> removeTreasureB();
                case LIST -> listLoot();
                case EXACT -> searchByValue();
                case RANGE -> searchByRange();
            }

        } while (binaryTreeOption != BTreeMenuOptions.BACK);
    }

    private void addTreasureB() {
        long nodeValue;
        String nodeName;

        // TODO: validate name/value
        nodeName = UI.askString("Enter the treasure's name: ");
        nodeValue = UI.askLong("Enter the treasure's value: ");

        treesManager.addBinaryNode(nodeName, nodeValue);

        UI.displayMessage("");
        UI.displayMessage("The treasure was correctly added to the loot.");
        UI.displayMessage("");
    }

    private void removeTreasureB() {
        String nodeName;

        // TODO: validate name
        nodeName = UI.askString("Enter the treasure's name: ");

        treesManager.removeBinaryNode(nodeName);

        UI.displayMessage("");
        UI.displayMessage("The treasure was correctly removed from the loot.");
        UI.displayMessage("");
    }

    private void listLoot() {
        getOption(BINARY_TREE_TRAVERSAL_ID);

        switch (binaryTreeTraversalOption) {
            case PREORDER -> treesManager.listLoot(TreesManager.PREORDER_TRAVERSAL_ID);
            case POSTORDER -> treesManager.listLoot(TreesManager.POSTORDER_TRAVERSAL_ID);
            case INORDER -> treesManager.listLoot(TreesManager.INORDER_TRAVERSAL_ID);
            case LEVEL -> treesManager.listLoot(TreesManager.BY_LEVEL_TRAVERSAL_ID);
        }
        UI.displayMessage("");
    }

    private void searchByValue() {
        long nodeValue;
        String nodeName;

        nodeValue = UI.askLong("Enter the value to search for: ");
        nodeName = treesManager.searchByValue(nodeValue);

        UI.displayMessage("");
        if ((nodeName == null)) {
            UI.displayMessage("No treasure with this value was found!");
        } else {
            UI.displayMessage("A treasure with this value was found: " + nodeName);
        }
        UI.displayMessage("");
    }

    private void searchByRange() {
        long lowerBoundValue;
        long upperBoundValue;

        // TODO: validate lowerBound < upperBound
        lowerBoundValue = UI.askLong("Enter the minimum value to search for: ");
        upperBoundValue = UI.askLong("Enter the maximum value to search for: ");

        UI.displayMessage("");
        treesManager.searchByRange(lowerBoundValue, upperBoundValue);
        UI.displayMessage("");
    }

    // ---------------------------------------------------------------------------------
    // R TREES
    // ---------------------------------------------------------------------------------

    private void runDeck() {
        do {
            getOption(R_TREE_ID);

            switch (rTreeOption) {
                case ADD -> addTreasureR();
                case REMOVE -> removeTreasureR();
                case VISUALIZE -> visualize();
                case SEARCH_AREA -> searchByArea();
                case SEARCH_PROXIMITY -> searchByProximity();
            }

        } while (rTreeOption != RTreeMenuOptions.BACK);

        UI.displayMessage("");
    }

    private void addTreasureR() {
        String nodeName;
        float x, y;

        // TODO: validate name/position
        nodeName = UI.askString("Enter the treasure's name: ");
        x = UI.askFloat("Enter the X coordinate of the treasure's position: ");
        y = UI.askFloat("Enter the Y coordinate of the treasure's position: ");

        treesManager.addRNode(nodeName, x, y);

        UI.displayMessage("");
        UI.displayMessage("The treasure was correctly added to the loot.");
        UI.displayMessage("");
    }

    private void removeTreasureR() {
        // Not implemented
    }

    private void visualize() {
        UI.displayMessage("Drawing the tree...");
        UI.displayMessage("");
        treesManager.visualizeRTree();
    }

    private void searchByArea() {
        treesManager.searchByArea(1.2f, 1.8f, 1.8f, 0.3f);
    }

    private void searchByProximity() {

    }

    // ---------------------------------------------------------------------------------
    // TABLES
    // ---------------------------------------------------------------------------------

    private void runCrew() {
        do {
            getOption(TABLES_ID);

            switch (tablesOption) {
                case ADD -> addPirate();
                case REMOVE -> removePirate();
                case SHOW -> showPirate();
                case AGE -> ageHistogram();
            }

        } while (tablesOption != TablesMenuOptions.BACK);

        UI.displayMessage("");
    }

    private void addPirate() {
        String name, role;
        int age;

        name = UI.askString("Enter the pirates name: ");
        age = UI.askInteger("Enter the pirates age: ");
        role = UI.askString("Enter the pirates role: ");
        tablesManager.addPirate(name, age, role);
    }

    private void removePirate() {
        String name;

        name = UI.askString("Enter the pirates name: ");

        tablesManager.removePirate(name);
    }

    private void showPirate() {
        String name;

        name = UI.askString("Enter the pirates name: ");

        tablesManager.printPirateInfo(name);
    }

    private void ageHistogram() {
        tablesManager.printHistogram();
    }


}
