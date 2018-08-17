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

package com.github.wolfwhitaker.sitecrawler.dao.properties;

import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class statically loads the DAO properties from the "dao.properties" file. It's constructor
 * takes the name of a database. The database name have to be used as a property name prefix in the
 * "dao.properties" file. Use the getter with a property name to obtain a necessary property. So,
 * if you need get url property for PostgreSQL database you should call constructor with the name
 * "posgresql" without brackets. Then, use getter with "url" parameter. It returns the value of the
 * entry "postgresql.url" from the "dao.properties" file. Use isNecessary parameter of the getter to
 * to indicate whether the property is necessary or not. This class is final with "hardcoded" logic
 * and is not supposed to be extended.
 *
 * @author WolfWhitaker
 */
public final class DAOProperties {

    /* Private constants */

    private static final String PROPERTIES_FILE = "dao.properties";
    private static final Properties PROPERTIES = new Properties();    // Changes it's state!

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new DAOConfigurationException(
                    "Properties file \"" + PROPERTIES_FILE + "\" is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException ex) {
            throw new DAOConfigurationException(
                    "Cannot load properties file \"" + PROPERTIES_FILE + "\": ", ex);
        }
    }

    /* Private variables */

    private String databaseName;

    /* Constructors */

    /**
     * Constructs a DAOProperties object with the given database name which is used as a
     * property prefix inside the "dao.properties" file.
     * @param databaseName The name of a database which is used as a property prefix inside
     *                     "dao.properties" file.
     * @throws DAOConfigurationException If the DAO properties file is missing in the classpath
     * or cannot be loaded.
     */
    public DAOProperties(String databaseName) throws DAOConfigurationException {
        this.databaseName = databaseName;
    }

    /* Actions */

    /**
     * Returns a String object with a specific property value associated with the given property
     * name inside the file "dao.properties". Use "mandatory" option to indicate whether the
     * property is mandatory or not. If the value of the property inside the "dao.properties"
     * file is empty or null and the property is not mandatory, it returns null. Otherwise it
     * throws {@link DAOConfigurationException}.
     * @param propertyName The property name which is associated with the property inside the
     *                     "dao.properties" file.
     * @param isNecessary Sets whether the returned property value should not be null nor empty.
     * @return A String with a specific property value associated with the given property name.
     * @throws DAOConfigurationException If the returned property value is null or empty and is
     * mandatory.
     */
    public String getProperty(String propertyName, boolean isNecessary)
            throws DAOConfigurationException {
        String fullKey = databaseName + "." + propertyName;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0) {
            if (isNecessary) {
                throw new DAOConfigurationException("Retrieving property \"" + fullKey + "\""
                        + " from the properties file \"" + PROPERTIES_FILE + "\" failed.");
            } else {
                property = null;
            }
        }

        return property;
    }

}
