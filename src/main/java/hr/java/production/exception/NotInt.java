package hr.java.production.exception;

/**
 * Used to create an exception for input values that are not of int type
 */
public class NotInt extends RuntimeException{
    /**
     * Constructor that takes no arguments
     */
    public NotInt() {
    }

    /**
     * Constructor that takes argument String message
     *
     * @param message Error message that an exception outputs
     */
    public NotInt(String message) {
        super(message);
    }

    /**
     * Constructor that takes arguments String message and Throwable cause
     *
     * @param message Error message that an exception outputs
     * @param cause Specifies the cause for the exception
     */
    public NotInt(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that takes argument Throwable cause
     *
     * @param cause Specifies the cause for the exception
     */
    public NotInt(Throwable cause) {
        super(cause);
    }
}
