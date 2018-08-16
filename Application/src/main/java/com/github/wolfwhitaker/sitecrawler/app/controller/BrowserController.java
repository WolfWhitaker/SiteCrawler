package com.github.wolfwhitaker.sitecrawler.app.controller;

import com.github.wolfwhitaker.sitecrawler.dao.DAOFactory;
import com.github.wolfwhitaker.sitecrawler.dao.WebPageDAO;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;

import java.util.List;

/**
 * Represents a controller for the
 * {@link com.github.wolfwhitaker.sitecrawler.app.view.SettingsView} object.
 * All interaction with the database or the crawler occurs through the instance
 * of this class.
 *
 * @author WolfWhitaker
 */
public class BrowserController {

    private final WebPageDAO dao = DAOFactory.getInstance(DAOFactory.POSTGRESQL).getWebPageDAO();

    /**
     * Clears database web_pages table and updates the model state.
     * @param model The model to be updated after the table clearing.
     */
    public void clearData(Model<List<WebPage>> model) {
        dao.clearTable();
        List<WebPage> property = dao.listById();
        model.setProperty(property);
    }

    /**
     * Fills the model with a result of searching inside the database by this string.
     * @param model The model to be filled with a result of searching.
     * @param str The string to search.
     */
    public void search(Model<List<WebPage>> model, String str) {
        if (str.equals("")) {
            updateModel(model);
        } else {
            List<WebPage> property = dao.searchList(str);
            model.setProperty(property);
        }
    }

    /**
     * Updates current model with the current data.
     * @param model The model to be updated.
     */
    public void updateModel(Model<List<WebPage>> model) {
        List<WebPage> property = dao.listById();
        model.setProperty(property);
    }

}