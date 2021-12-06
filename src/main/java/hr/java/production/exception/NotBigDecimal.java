package hr.java.production.exception;

/**
 * Used to create an exception for input values that are not of BigDecimal type
 */
public class NotBigDecimal extends RuntimeException{
    /**
     * Constructor that takes no arguments
     */
    public NotBigDecimal() {
    }

    /**
     * Constructor that takes argument String message
     *
     * @param message Error message that an exception outputs
     */
    public NotBigDecimal(String message) {
        super(message);
    }

    /**
     * Constructor that takes arguments String message and Throwable cause
     *
     * @param message Error message that an exception outputs
     * @param cause Specifies the cause for the exception
     */
    public NotBigDecimal(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that takes argument Throwable cause
     *
     * @param cause Specifies the cause for the exception
     */
    public NotBigDecimal(Throwable cause) {
        super(cause);
    }
}
