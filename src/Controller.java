import graph.GraphManager;

import java.io.IOException;

public class Controller {

    private final GraphManager graphManager;
    private final UIManager UI;

    private static final int GENERAL_MENU_ID = 1;
    private static final int GRAPHS_MENU_ID = 2;

    private GeneralMenuOptions generalOption;
    private GraphMenuOptions graphOption;

    // TODO: handle exception
    public Controller() throws IOException {
        graphManager = new GraphManager();
        UI = new UIManager();
    }

    private void getOption(int menuID) {
        try {
            switch (menuID) {
                case 1 -> generalOption = UI.displayGeneralMenu();
                case 2 -> graphOption = UI.displayGraphMenu();
            }
            // New line
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

        } while (graphOption != GraphMenuOptions.GOBACK);
    }

    private int askVertexID() {
        int vertexID;

        while (true) {
            try {
                 vertexID = UI.askInteger("Enter the origin node's identifier: ");
                if (graphManager.validateVertex(vertexID)) {
                    break;
                }
                else {
                    UI.displayError("The specified ID does not correspond to any vertex");
                }
            } catch (NumberFormatException e) {
                UI.displayError("The specified input is not an ID");
            }
        }

        return vertexID;
    }

    private void showPointsOfInterest() {
        int vertexID = askVertexID();
        graphManager.showPointsOfInterest(vertexID);
    }

    private void showDangerousPlaces() {
        int vertexID = askVertexID();
        graphManager.showDangerousPlaces(vertexID);
    }

    private void generateUniversalNauticalChart() {
    }

    private void findOptimalRoute() {
    }

    private void runInventory() {
    }

    private void runDeck() {
    }

    private void runCrew() {
    }

}
