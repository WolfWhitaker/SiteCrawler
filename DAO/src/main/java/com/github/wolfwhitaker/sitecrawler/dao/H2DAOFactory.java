package com.github.wolfwhitaker.sitecrawler.dao;

import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOConfigurationException;
import com.github.wolfwhitaker.sitecrawler.dao.properties.DAOProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class H2DAOFactory extends DAOFactory {

    private static final String URL      = "url";
    private static final String DRIVER   = "driver";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static DataSource source = new DataSource();

    public H2DAOFactory() {
        PoolProperties properties = obtainProperties();
        source.setPoolProperties(properties);
    }

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

    @Override
    public WebPageDAO getWebPageDAO() {
        return new H2WebPageDAO();
    }

    private PoolProperties obtainProperties() {
        DAOProperties tmp = new DAOProperties("h2");
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
