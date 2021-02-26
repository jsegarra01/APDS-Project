/**
 * This exception will be thrown by the UIManager when entering an option which is not
 * valid. This exception allows us to limit the inputs from the user so only valid options can be
 * entered and thus eliminate incorrect values.
 *
 * @author  Lucas Azcue, Marc Escote, Eric Eugenio and Josep Segarra
 * @version 1.0
 * @since   2020-12-08
 *
 * @see java.lang.Exception
 * @see UIManager
 */
public class InvalidInputException extends Exception {

    private final String userInput;

    /**
     * Constructor method to creat a new InvalidArgumentException, with the input
     * introduced by the user.
     *
     * @param userInput input introduce by the user
     */
    public InvalidInputException(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Returns the input introduced by the user considered to be invalid.
     *
     * @return the user input.
     */
    public String getUserInput() {
        return userInput;
    }
}