package hr.java.production.exception;

/**
 * Used to create an exception for BigDecimal values that are out of bounds
 */
public class InvalidBigDecimal extends Exception{
    /**
     * Constructor that takes no arguments
     */
    public InvalidBigDecimal() {
    }

    /**
     * Constructor that takes argument String message
     *
     * @param message Error message that an exception outputs
     */
    public InvalidBigDecimal(String message) {
        super(message);
    }

    /**
     * Constructor that takes arguments String message and Throwable cause
     *
     * @param message Error message that an exception outputs
     * @param cause Specifies the cause for the exception
     */
    public InvalidBigDecimal(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that takes argument Throwable cause
     *
     * @param cause Specifies the cause for the exception
     */
    public InvalidBigDecimal(Throwable cause) {
        super(cause);
    }
}
