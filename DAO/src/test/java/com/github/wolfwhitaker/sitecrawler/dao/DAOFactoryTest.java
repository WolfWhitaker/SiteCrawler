package com.github.wolfwhitaker.sitecrawler.dao;

import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DAOFactoryTest {

    @Test
    void testGetInstance() {
        // H2DAOFactory obtaining test
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.H2);
        Assertions.assertNotNull(factory);
        Assertions.assertTrue(factory instanceof H2DAOFactory);
        factory = null;

        // PostgreSQLDAOFactory obtaining test
        factory = DAOFactory.getInstance(DAOFactory.POSTGRESQL);
        Assertions.assertNotNull(factory);
        Assertions.assertTrue(factory instanceof PostgreSQLDAOFactory);

        // Min value test
        Throwable ex = Assertions.assertThrows(DAOConfigurationException.class, () -> {
            DAOFactory.getInstance(DAOFactory.MIN - 1);
        });
        Assertions.assertNotNull(ex.getMessage());
        ex = null;

        // Max value test
        ex = Assertions.assertThrows(DAOConfigurationException.class, () -> {
            DAOFactory.getInstance(DAOFactory.MAX + 1);
        });
        Assertions.assertNotNull(ex.getMessage());
    }

}
