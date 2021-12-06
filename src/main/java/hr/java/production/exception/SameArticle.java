package hr.java.production.exception;

/**
 * Used to create an exception for items that have already been entered in the factories or stores arrays
 */
public class SameArticle extends Exception{
    /**
     * Constructor that takes no arguments
     */
    public SameArticle() {
    }

    /**
     * Constructor that takes argument String message
     *
     * @param message Error message that an exception outputs
     */
    public SameArticle(String message) {
        super(message);
    }

    /**
     * Constructor that takes arguments String message and Throwable cause
     *
     * @param message Error message that an exception outputs
     * @param cause Specifies the cause for the exception
     */
    public SameArticle(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that takes argument Throwable cause
     *
     * @param cause Specifies the cause for the exception
     */
    public SameArticle(Throwable cause) {
        super(cause);
    }
}
