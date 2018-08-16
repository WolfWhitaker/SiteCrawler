package com.github.wolfwhitaker.sitecrawler.mvc.exception;

 /** This class represents a general model exception. It should wrap any exception
  * which occurs in the model part of a program.
  *
  * @author WolfWhitaker
  */
public class ModelException extends RuntimeException {

    /* Private constants */

    private static final long serialVersionUID = 1L;

    /* Constructors */

    /**
     * Constructs ModelException with the given detail message.
     * @param message The detail message of this ModelException.
     */
    public ModelException(String message) {
        super(message);
    }

    /**
     * Constructs ModelException with the given cause.
     * @param cause The cause of this ModelException.
     */
    public ModelException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs ModelException with the given detail message and cause.
     * @param message The detail message of this ModelException.
     * @param cause The cause of this ModelException.
     */
    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

}