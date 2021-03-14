import graph.GraphManager;

import view.*;
import view.menus.*;

public class Controller {

    private final GraphManager graphManager;
    private final UIManager UI;

    private static final int GENERAL_MENU_ID = 1;
    private static final int GRAPHS_MENU_ID = 2;

    private GeneralMenuOptions generalOption;
    private GraphMenuOptions graphOption;

    public Controller(GraphManager graphManager) {
        this.graphManager = graphManager;
        UI = new UIManager();
    }

    private void getOption(int menuID) {
        try {
            switch (menuID) {
                case 1 -> generalOption = UI.displayGeneralMenu();
                case 2 -> graphOption = UI.displayGraphMenu();
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

    private int askVertexID(String vertex, boolean checkType, boolean type) {
        int vertexID;

        while (true) {
            try {
                vertexID = UI.askInteger("Enter the " + vertex + " node's identifier: ");
                if (graphManager.validateVertex(vertexID)) {
                    if (checkType) {
                        if (type) {
                            if (graphManager.validatePointOfInterest(vertexID)) {
                                break;
                            }
                            else {
                                UI.displayError("The specified ID does not correspond not a point of interest");
                            }
                        }
                        else {
                            if (graphManager.validateDangerousPlace(vertexID)) {
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
                   UI.displayError("The specified ID does not correspond to any vertex or it may be disconnected");
                }
            } catch (NumberFormatException e) {
                UI.displayError("The specified input is not an ID");
            }
        }
        return vertexID;
    }

    private void showPointsOfInterest() {
        int vertexID = askVertexID("origin", true, true);

        UI.displayMessage("\nDFS found the following points of interest:");
        UI.displayMessage("");
        graphManager.getPointsOfInterest(vertexID);
        UI.displayMessage("");
    }

    private void showDangerousPlaces() {
        int vertexID = askVertexID("origin", true, false);

        UI.displayMessage("\nBFS found the following dangerous places:");
        UI.displayMessage("");
        graphManager.showDangerousPlaces(vertexID);
        UI.displayMessage("");
    }

    private void generateUniversalNauticalChart() {
        int vertexID = askVertexID("origin", false, false);

        UI.displayMessage("\nFinding the MST...");
        UI.displayMessage("");
        graphManager.showUniversalNauticalChart(vertexID);
        UI.displayMessage("");
    }

    private void findOptimalRoute() {
        int startVertexID = askVertexID("origin", false, false);
        int endVertexID = askVertexID("destination", false, false);

        UI.displayMessage("\nFinding the optimal route...");
        UI.displayMessage("");
        graphManager.searchShortestPath(startVertexID, endVertexID);
        UI.displayMessage("");
    }

    private void runInventory() {
    }

    private void runDeck() {
    }

    private void runCrew() {
    }

}
