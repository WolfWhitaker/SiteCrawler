package com.github.wolfwhitaker.sitecrawler.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class H2DAOFactoryTest {

    @Test
    void testGetWebPageDAO() {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.H2);
        WebPageDAO dao = factory.getWebPageDAO();
        Assertions.assertNotNull(dao);
        Assertions.assertTrue(dao instanceof H2WebPageDAO);
    }

}
