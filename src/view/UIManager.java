package view;

import java.util.Locale;
import java.util.Scanner;

import view.menus.*;

/**
 * The UI class provides the interface to interact with a user
 * by showing messages on the screen and asking inputs through the keyboard.
 *
 * @author  Lucas Azcue, Marc Escote, Eric Eugenio and Josep Segarra
 * @version 1.0
 * @since   2020-02-25
 */
public class UIManager {

    private final Scanner scanner;

    /**
     * Constructor method to create a new UI. Every behaviour related to user
     * input/output will be delegated in here.
     */
    public UIManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows the general menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration view.menus.GeneralMenuOptions.
     * @throws InvalidInputException if the user input is invalid.
     * @see GeneralMenuOptions
     */
    public GeneralMenuOptions displayGeneralMenu() throws InvalidInputException {

        System.out.println("-= Pirates of the Mediterranean =-\n");
        System.out.println("1. Routes (Graphs)");
        System.out.println("2. Inventory (Binary Trees)");
        System.out.println("3. Deck (R Trees)");
        System.out.println("4. Crew (Tables)\n");
        System.out.println("5. Exit\n");

        String option = askString("Choose an option: ");

        return switch (option) {
            case "1" -> GeneralMenuOptions.GRAPHS;
            case "2" -> GeneralMenuOptions.BINARY_TREES;
            case "3" -> GeneralMenuOptions.R_TREES;
            case "4" -> GeneralMenuOptions.TABLES;
            case "5" -> GeneralMenuOptions.EXIT;
            default -> throw new InvalidInputException(option);
        };
    }

    /**
     * Shows the graphs menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration view.menus.GraphMenuOptions.
     * @throws InvalidInputException if the user input is invalid.
     * @see GraphMenuOptions
     */
    public GraphMenuOptions displayGraphMenu() throws InvalidInputException {
        System.out.println("\tA. Find points of interest (DFS)");
        System.out.println("\tB. Find dangerous places (BFS)");
        System.out.println("\tC. Show the Universal Nautical Chart (MST)");
        System.out.println("\tD. Find the optimal route (Dijkstra)\n");
        System.out.println("\tE. Go back\n");

        String option = askString("What functionality do you want to run? ").toUpperCase(Locale.ROOT);

        return switch (option) {
            case "A" -> GraphMenuOptions.DFS;
            case "B" -> GraphMenuOptions.BFS;
            case "C" -> GraphMenuOptions.MST;
            case "D" -> GraphMenuOptions.DIJKSTRA;
            case "E" -> GraphMenuOptions.BACK;
            default -> throw new InvalidInputException(option);
        };
    }

    /**
     * Shows the BinaryTree menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration view.menus.BTreeMenuOptions.
     * @throws InvalidInputException if the user input is invalid.
     * @see BTreeMenuOptions
     */
    public BTreeMenuOptions displayBinaryTreeMenu() throws InvalidInputException {
        System.out.println("\tA. Add treasure");
        System.out.println("\tB. Remove treasure");
        System.out.println("\tC. List loot");
        System.out.println("\tD. Search by value (exact)");
        System.out.println("\tE. Search by value (range)\n");
        System.out.println("\tF. Go back\n");

        String option = askString("What functionality do you want to run? ").toUpperCase(Locale.ROOT);

        return switch (option) {
            case "A" -> BTreeMenuOptions.ADD;
            case "B" -> BTreeMenuOptions.REMOVE;
            case "C" -> BTreeMenuOptions.LIST;
            case "D" -> BTreeMenuOptions.EXACT;
            case "E" -> BTreeMenuOptions.RANGE;
            case "F" -> BTreeMenuOptions.BACK;
            default -> throw new InvalidInputException(option);
        };
    }

    /**
     * Shows the BinaryTrees traversal menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration view.menus.BTreeTraversalMenuOptions.
     * @throws InvalidInputException if the user input is invalid.
     * @see BTreeTraversalMenuOptions
     */
    public BTreeTraversalMenuOptions displayBTreeTraversalMenu() throws InvalidInputException {
        System.out.println("\tI. Preorder");
        System.out.println("\tII. Postorder");
        System.out.println("\tIII. Inorder");
        System.out.println("\tIV. By level\n");

        String option = askString("Pick a traversal: ").toUpperCase(Locale.ROOT);

        return switch (option) {
            case "I" -> BTreeTraversalMenuOptions.PREORDER;
            case "II" -> BTreeTraversalMenuOptions.POSTORDER;
            case "III" -> BTreeTraversalMenuOptions.INORDER;
            case "IV" -> BTreeTraversalMenuOptions.LEVEL;
            default -> throw new InvalidInputException(option);
        };
    }

    /**
     * Shows the RTrees menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration view.menus.RtreeMenuOptions.
     * @throws InvalidInputException if the user input is invalid.
     * @see BTreeTraversalMenuOptions
     */
    public RTreeMenuOptions displayRTreeMenu() throws InvalidInputException {
        System.out.println("\tA. Add treasure");
        System.out.println("\tB. Remove treasure");
        System.out.println("\tC. Visualize");
        System.out.println("\tD. Search by area");
        System.out.println("\tE. Search by proximity\n");
        System.out.println("\tF. Go back\n");

        String option = askString("What functionality do you want to run? ").toUpperCase(Locale.ROOT);

        return switch (option) {
            case "A" -> RTreeMenuOptions.ADD;
            case "B" -> RTreeMenuOptions.REMOVE;
            case "C" -> RTreeMenuOptions.VISUALIZE;
            case "D" -> RTreeMenuOptions.SEARCH_AREA;
            case "E" -> RTreeMenuOptions.SEARCH_PROXIMITY;
            case "F" -> RTreeMenuOptions.BACK;
            default -> throw new InvalidInputException(option);
        };
    }

    /**
     * Shows the Tables menu on the console and requests an option to the user.
     *
     * @return the option entered by the user in form of a value from the enumeration view.menus.TablesMenuOptions.
     * @throws InvalidInputException if the user input is invalid.
     * @see RTreeMenuOptions
     */
    public TablesMenuOptions displayTablesMenu() throws InvalidInputException {
        System.out.println("\tA. Add pirate");
        System.out.println("\tB. Remove pirate");
        System.out.println("\tC. Show pirate");
        System.out.println("\tD. Age histogram\n");
        System.out.println("\tE. Go back\n");

        String option = askString("What functionality do you want to run? ").toUpperCase(Locale.ROOT);;

        return switch (option) {
            case "A" -> TablesMenuOptions.ADD;
            case "B" -> TablesMenuOptions.REMOVE;
            case "C" -> TablesMenuOptions.SHOW;
            case "D" -> TablesMenuOptions.AGE;
            case "E" -> TablesMenuOptions.BACK;
            default -> throw new InvalidInputException(option);
        };
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
     * Shows a message on the screen and requests the user to input an int.
     *
     * @param message the message to show on the screen.
     * @return the user's input in form of an int.
     * @throws NumberFormatException if user input is not a number.
     */
    public int askInteger(String message) throws NumberFormatException {
        return Integer.parseInt(askString(message));
    }

    /**
     * Shows a message on the screen and requests the user to input a float.
     *
     * @param message the message to show on the screen.
     * @return the user's input in form of a float.
     * @throws NumberFormatException if user input is not a number.
     */
    public float askFloat(String message) throws NumberFormatException {
        return Float.parseFloat(askString(message));
    }

    /**
     * Shows a message on the screen and requests the user to input a long.
     *
     * @param message the message to show on the screen.
     * @return the user's input in form of a long.
     * @throws NumberFormatException if user input is not a number.
     */
    public long askLong(String message) throws NumberFormatException {
        return Long.parseLong(askString(message));
    }

    /**
     * Shows a message on the screen reporting an error.
     *
     * @param message the message to show on the screen.
     */
    public void displayError(String message) {
        System.out.println("\nERROR: " + message + ".\n");
    }

    /**
     * Displays the specified message.
     * @param message the message to be displayed.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
}



