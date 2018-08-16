package com.github.wolfwhitaker.sitecrawler.dao;


import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOConfigurationException;

import com.github.wolfwhitaker.sitecrawler.dao.properties.DAOProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * PostgreSQL implementation of {@link DAOFactory}. It uses tomcat connection
 * pool as a data source.
 */
public class PostgreSQLDAOFactory extends DAOFactory {

    /* Private constants */

    private static final String URL      = "url";
    private static final String DRIVER   = "driver";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    /* Private variables */

    private static DataSource source = new DataSource();

    /* Constructors */

    PostgreSQLDAOFactory() {
        PoolProperties properties = obtainProperties();
        source.setPoolProperties(properties);
    }

    /* Actions */

    /**
     * Returns a connection to the PostgreSQL database.
     * @return A connection to the PostgreSQL database.
     * @throws DAOConfigurationException if making connection failed.
     */
    public static Connection getConnection() throws DAOConfigurationException {
        Connection con;
        try {
            con = source.getConnection();
        } catch (SQLException ex) {
            throw new DAOConfigurationException(
                    "Cannot make connection: ", ex);
        }
        return con;
    }

    /**
     * @see DAOFactory#getWebPageDAO().
     */
    @Override
    public WebPageDAO getWebPageDAO() {
        return new PostgreSQLWebPageDAO();
    }

    /*
     * Returns tomcat pool properties.
     */
    private PoolProperties obtainProperties() {
        DAOProperties tmp = new DAOProperties("postgresql");
        PoolProperties properties = new PoolProperties();

        properties.setUrl(
                tmp.getProperty(URL, true));
        properties.setDriverClassName(
                tmp.getProperty(DRIVER, false));
        properties.setPassword(
                tmp.getProperty(PASSWORD, false));
        properties.setUsername(
                tmp.getProperty(USERNAME, properties.getPassword() != null));

        return properties;
    }

}