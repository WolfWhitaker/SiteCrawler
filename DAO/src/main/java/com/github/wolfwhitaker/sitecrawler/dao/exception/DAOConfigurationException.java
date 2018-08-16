package com.github.wolfwhitaker.sitecrawler.dao.exception;

/**
 * This class represents an exception in the DAO configuration, such as missing
 * resources, giving wrong configuration data, etc.
 *
 * @author WolfWhitaker
 */
public class DAOConfigurationException extends DAOException {

    /* Private constants */

    private static final long serialVersionUID = 1L;

    /* Constructors */

    /**
     * Constructs DAOConfigurationException with the given detail message.
     * @param message The detail message of this DAOConfigurationException.
     */
    public DAOConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs DAOConfigurationException with the given cause.
     * @param cause The cause of the DAOConfigurationException.
     */
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs DAOConfigurationException with the given detail message and cause.
     * @param message The detail message of this DAOConfigurationException.
     * @param cause The root cause of this DAOConfigurationException.
     */
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}