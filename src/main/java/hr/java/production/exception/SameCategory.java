package hr.java.production.exception;

/**
 * Used to create an exception for categories that have already been entered
 */
public class SameCategory extends RuntimeException{
    /**
     * Constructor that takes no arguments
     */
    public SameCategory() {
    }

    /**
     * Constructor that takes argument String message
     *
     * @param message Error message that an exception outputs
     */
    public SameCategory(String message) {
        super(message);
    }

    /**
     * Constructor that takes arguments String message and Throwable cause
     *
     * @param message Error message that an exception outputs
     * @param cause Specifies the cause for the exception
     */
    public SameCategory(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that takes argument Throwable cause
     *
     * @param cause Specifies the cause for the exception
     */
    public SameCategory(Throwable cause) {
        super(cause);
    }
}
