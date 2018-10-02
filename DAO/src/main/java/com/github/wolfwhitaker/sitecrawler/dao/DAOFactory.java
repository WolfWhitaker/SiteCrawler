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

package com.github.wolfwhitaker.sitecrawler.dao;

import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOConfigurationException;

/**
 * Represents a DAO factory. Use {@link #getInstance(int)} to obtain a new
 * instance for the given DAO type.
 *
 * @author WolfWhitaker
 */
public abstract class DAOFactory {

    /* Public constants */

    public static final int POSTGRESQL = 0;

    /* Actions */

    /**
     * Returns a new DAOFactory instance for the chosen DAO type.
     * @param whichFactory One of the DAO type identifiers, e.g., POSTGRESQL.
     * @throws DAOConfigurationException If the chosen type is unsupported.
     */
    public static DAOFactory getInstance(int whichFactory)
            throws DAOConfigurationException {
        switch (whichFactory) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory();
            default:
                throw new DAOConfigurationException("Unsupported DAO type!");
        }
    }

    /**
     * Returns the WebPage DAO associated with the current DAOFactory.
     * @return The WebPageDAO object associated with the current DAOFactory.
     */
    public abstract WebPageDAO getWebPageDAO();

}