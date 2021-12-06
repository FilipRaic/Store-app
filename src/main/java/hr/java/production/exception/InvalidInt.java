package hr.java.production.exception;

/**
 * Used to create an exception for int values that are out of bounds
 */
public class InvalidInt extends Exception{
    /**
     * Constructor that takes no arguments
     */
    public InvalidInt() {
    }

    /**
     * Constructor that takes argument String message
     *
     * @param message Error message that an exception outputs
     */
    public InvalidInt(String message) {
        super(message);
    }

    /**
     * Constructor that takes arguments String message and Throwable cause
     *
     * @param message Error message that an exception outputs
     * @param cause Specifies the cause for the exception
     */
    public InvalidInt(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that takes argument Throwable cause
     *
     * @param cause Specifies the cause for the exception
     */
    public InvalidInt(Throwable cause) {
        super(cause);
    }
}
