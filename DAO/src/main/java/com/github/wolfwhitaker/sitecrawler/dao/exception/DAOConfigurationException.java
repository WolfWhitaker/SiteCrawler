/*
 *
 ****************************************************************************
 *                                                                          *
 *                                SiteCrawler                               *
 *                                                                          *
 * is an open source project, which is distributed under "fair use" terms.  *
 * It means you can use any part of it's code as you wish and redistribute  *
 * it, but you should mention the author.                                   *
 *                                                                          *
 * WARNING!!! It is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR  *
 * CONDITIONS OF ANY KIND. You should also keep in mind that this project   *
 * uses the code of the third-party developers. So, if you want to use some *
 * part of it's code WHICH USES THIRD-PART LIBRARIES in your own project,   *
 * MAKE SURE that the way you use it doesn't violate THEIR TERMS OF USE.    *
 *                                                                          *
 * Copyright (C) 2018 WolfWhitaker                                          *
 *                                                                          *
 * My github page: https://github.com/WolfWhitaker                          *
 *                                                                          *
 ****************************************************************************
 *
 */

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