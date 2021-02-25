import java.io.IOException;
import java.util.Scanner;

/**
 * The UI class provides the interface to interact with a user
 * by showing messages on the screen and asking inputs through the keyboard.
 *
 * @author  Lucas Azcue, Marc Escote, Eric Eugenio and Josep Segarra
 * @version 1.0
 * @since   2020-02-25
 */
public class UI {

    private final Scanner scanner;

    /**
     * Constructor method to create a new UI. Every behaviour related to user
     * input/output will be delegated in here.
     */
    public UI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows a message on the screen and requests the user to input a String.
     *
     * @param message the message to show on the screen.
     * @return the user's input in form of a String.
     */
    public String askString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Shows the menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration MenuOptions.
     * @throws Exception if the user input is invalid.
     * @see GraphMenuOptions
     */
    public GeneralMenuOptions displayGeneralMenu() throws Exception {

        System.out.println("-= Pirates of the Mediterranean -=\n");
        System.out.println("1. Routes (Graphs)");
        System.out.println("2. Inventory (Binary trees)");
        System.out.println("3. Deck (R trees)");
        System.out.println("4. Crew (Tables)");
        System.out.println("\n5. Exit");

        String option = askString("\nChoose an option: ");

        switch (option) {
            case "1":
                return GeneralMenuOptions.ROUTE;
            case "2":
                return GeneralMenuOptions.INVENTORY;
            case "3":
                return GeneralMenuOptions.DECK;
            case "4":
                return GeneralMenuOptions.CREW;
            case "5":
                return GeneralMenuOptions.EXIT;
            default:
                throw new Exception();
        }
    }
    public GraphMenuOptions displayGraphMenu() throws Exception {
        System.out.println("\tA. Find points of interest (DFS)");
        System.out.println("\tB. Find dangerous places (BFS)");
        System.out.println("\tC. Show the Universal Nautical Chart (MST)");
        System.out.println("\tD. Find the optimal route (Dijkstra)");
        System.out.println("\n\tE. Go back");

        String option = askString("\nWhat functionality do you want to run? ");

        switch (option) {
            case "1":
                return GraphMenuOptions.DFS;
            case "2":
                return GraphMenuOptions.BFS;
            case "3":
                return GraphMenuOptions.MST;
            case "4":
                return GraphMenuOptions.DIJKSTRA;
            case "5":
                return GraphMenuOptions.GOBACK;
            default:
                throw new Exception();
        }


    }

}



