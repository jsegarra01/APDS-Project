import graph.GraphManager;

import trees.TreesManager;
import view.*;
import view.menus.*;

public class Controller {

    private final GraphManager graphManager;
    private final TreesManager treesManager;
    private final UIManager UI;

    private static final int GENERAL_MENU_ID = 1;
    private static final int GRAPHS_MENU_ID = 2;
    private static final int BINARY_TREES_MENU_ID = 3;

    private GeneralMenuOptions generalOption;
    private GraphMenuOptions graphOption;
    private BTreesMenuOptions binaryTreesOption;

    public Controller(GraphManager graphManager, TreesManager treesManager) {
        this.graphManager = graphManager;
        this.treesManager = treesManager;

        UI = new UIManager();
    }

    private void getOption(int menuID) {
        try {
            switch (menuID) {
                case 1 -> generalOption = UI.displayGeneralMenu();
                case 2 -> graphOption = UI.displayGraphMenu();
                case 3 -> binaryTreesOption = UI.displayTreesMenu();
            }
            UI.displayMessage("");
        }
        catch (InvalidInputException e) {
            UI.displayError(e.getUserInput() + " is not a valid option");
            getOption(menuID);
        }
    }

    public void run() {
        do {
            getOption(GENERAL_MENU_ID);

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
            getOption(GRAPHS_MENU_ID);

            switch (graphOption) {
                case DFS -> showPointsOfInterest();
                case BFS -> showDangerousPlaces();
                case MST -> generateUniversalNauticalChart();
                case DIJKSTRA -> findOptimalRoute();
            }

        } while (graphOption != GraphMenuOptions.BACK);
    }

    private int askGraphNodeID(String node, boolean checkType, boolean type) {
        int nodeID;

        while (true) {
            try {
                nodeID = UI.askInteger("Enter the " + node + " node's identifier: ");
                if (graphManager.validateVertex(nodeID)) {
                    if (checkType) {
                        if (type) {
                            if (graphManager.validatePointOfInterest(nodeID)) {
                                break;
                            }
                            else {
                                UI.displayError("The specified ID does not correspond not a point of interest");
                            }
                        }
                        else {
                            if (graphManager.validateDangerousPlace(nodeID)) {
                                break;
                            }
                            else {
                                UI.displayError("The specified ID does not correspond not a dangerous place");
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
            getOption(BINARY_TREES_MENU_ID);

            switch (binaryTreesOption) {
                case ADD -> addTreasure();
                case REMOVE -> removeTreasure();
                case LIST -> listLoot();
                case EXACT -> searchByExactValue();
                case RANGE -> searchByRangeValue();
            }

        } while (binaryTreesOption != BTreesMenuOptions.BACK);
    }

    private void addTreasure() {
    }

    private void removeTreasure() {

    }

    private void listLoot() {

    }

    private void searchByExactValue() {

    }

    private void searchByRangeValue() {

    }

    private void runDeck() {
    }

    private void runCrew() {
    }

}
