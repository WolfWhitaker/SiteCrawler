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

import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOException;

import java.util.List;

/**
 * This interface represents a contract for a DAO of the {@link WebPage} DTO.
 *
 * @author WolfWhitaker
 */
public interface WebPageDAO {

    /**
     * Returns the WebPage object from the database matching the given ID, otherwise null.
     * @param id The ID of the WebPage to be returned.
     * @return WebPage from the database matching the given ID, otherwise null.
     * @throws DAOException if something fails at database level.
     */
    WebPage find(Long id) throws DAOException;

    /**
     * Returns a list of all web pages from the database ordered by ID.
     * The list is never null and is empty when the database does not contain any web page.
     * @return A list of all web pages from the database ordered by ID.
     * @throws DAOException If something fails at database level.
     */
    List<WebPage> listById() throws DAOException;

    /**
     * Returns a list which formed with WebPages which contain a text from the searching
     * query.
     * The list is never null and is empty when the database does not contain any
     * WebPage.
     * @param searchQuery The phrase to search in a web page content.
     * @return A list formed with WebPages which contain a text from the searching query.
     * @throws DAOException If something fails at database level.
     */
    List<WebPage> searchList(String searchQuery) throws DAOException;

    /**
     * Creates the given WebPage object in the database. The WebPage ID must be null,
     * otherwise it will throw illegalArgumentException.
     * @param page WebPage to be created in the database.
     * @throws IllegalArgumentException If the WebPage ID is not null.
     * @throws DAOException If something fails at database level.
     */
    void create(WebPage page) throws IllegalArgumentException, DAOException;

    /**
     * Updates the WebPage object in the database. The WebPage ID must not be null,
     * otherwise it will throw IllegalArgumentException.
     * @param page WebPage to be updated in the database.
     * @throws IllegalArgumentException If the WebPage ID is null.
     * @throws DAOException If something fails at database level.
     */
    void update(WebPage page) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given WebPage from the database.
     * @param page The WebPage to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    void delete(WebPage page) throws DAOException;

    /**
     * Clears the "web_pages" table from all entries.
     * @throws DAOException If something fails at database level.
     */
    void clearTable() throws DAOException;

    /**
     * Creates the "web_pages" table in the database if it doesn't exist.
     * @throws DAOException If something fails at database level.
     */
    void createTable() throws DAOException;

}
