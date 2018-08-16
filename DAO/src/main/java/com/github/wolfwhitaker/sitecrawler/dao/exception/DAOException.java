package com.github.wolfwhitaker.sitecrawler.dao.exception;

/**
 * This class represents a general DAO exception. It should wrap any other than
 * {@link DAOConfigurationException} in the DAO part of a program.
 *
 * @author WolfWhitaker
 */
public class DAOException extends RuntimeException {

    /* Private constants */

    private static final long serialVersionUID = 1L;

    /* Constructors */

    /**
     * Constructs DAOException with the given detail message.
     * @param message The detail message of this DAOException.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs DAOException with the given root cause.
     * @param cause The cause of this DAOException.
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs DAOException with the given detail message and cause.
     * @param message The detail message of this DAOException.
     * @param cause The cause of this DAOException.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}