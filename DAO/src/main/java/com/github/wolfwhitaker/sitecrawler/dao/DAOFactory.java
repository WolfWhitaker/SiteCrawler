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

    public static final int MIN = 0;
    public static final int MAX = 1;
    public static final int H2 = 0;
    public static final int POSTGRESQL = 1;

    /* Actions */

    /**
     * Returns a new DAOFactory instance for the chosen DAO type.
     * @param whichFactory One of the DAO type identifiers, e.g., POSTGRESQL.
     * @throws DAOConfigurationException If the chosen type is unsupported.
     */
    public static DAOFactory getInstance(int whichFactory)
            throws DAOConfigurationException {
        switch (whichFactory) {
            case H2:
                return new H2DAOFactory();
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