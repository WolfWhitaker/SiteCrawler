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

package com.github.wolfwhitaker.sitecrawler.app;

import com.github.wolfwhitaker.sitecrawler.app.view.BrowserView;
import com.github.wolfwhitaker.sitecrawler.app.view.SettingsView;
import com.github.wolfwhitaker.sitecrawler.dao.DAOFactory;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;
import com.wolfwhitaker.sitecrawler.crawler.PostgreSQLCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the program. It prepares application elements for a work.
 * It's a manager class which runs {@link com.github.wolfwhitaker.sitecrawler.app.Manageable}
 * objects. It switches the views so, that the only one view can be visible for the user at
 * the same time.
 *
 * @author WolfWhitaker
 */
public class SiteCrawlerManager {

    /* Nested classes */

    /**
     * This enum class contains the names of all objects which are managed by the
     * SiteCrawlerManager class.
     */
    public enum ManageableObjects {

        SETTINGS,
        BROWSER

    }

    /**
     * This class is used as centralized unchecked exception handler. It set to log every
     * (@link java.lang.RuntimeException) it catches.
     */
    private static class CentralizedUncaughtExceptionHandler
            implements Thread.UncaughtExceptionHandler {

        private static final Logger logger =
                                         LoggerFactory.getLogger(PostgreSQLCrawler.class);

        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            logger.error("Runtime exception occurred: " + ex);
        }

    }

    /* private constants */

    private final SettingsView settings;
    private final BrowserView browser;

    /* Constructors */

    private SiteCrawlerManager() {
        DAOFactory.getInstance(DAOFactory.POSTGRESQL).getWebPageDAO().createTable();
        final Model<List<WebPage>> model = new Model<>(new ArrayList<>());
        settings = new SettingsView(this);
        browser = new BrowserView(this);
        settings.setModel(model);
        browser.setModel(model);
        settings.activate();
    }

    /* Actions */

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(
                                                new CentralizedUncaughtExceptionHandler());
        SwingUtilities.invokeLater(SiteCrawlerManager::new);
    }

    /**
     * Switches the state of the {@link Manageable} objects.
     * @param invoker The object to be disabled.
     * @param objToActivate The name of the object to be activate.
     * @throws NullPointerException if this object doesn't supported.
     */
    public void switchActive(Manageable invoker, ManageableObjects objToActivate) {
        invoker.disable();
        switch (objToActivate) {
            case SETTINGS:
                settings.activate();
                break;
            case BROWSER:
                browser.activate();
                break;
            default:
                assert false: "SiteCrawlerManager cannot find the object: " + objToActivate;
        }
    }

}