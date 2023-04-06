package srg.exceptions;

/**
 * An exception raised when there is insufficient capacity for a resource.
 */
public class InsufficientCapcaityException extends Exception {
    /**
     * Constructs an InsufficientCapcaityException.
     */
    public InsufficientCapcaityException() {
        super();
    }

    /**
     * Constructs an InsufficientCapcaityException.
     * @param s The exception message.
     */
    public InsufficientCapcaityException(String s) {
        super(s);
    }
}
