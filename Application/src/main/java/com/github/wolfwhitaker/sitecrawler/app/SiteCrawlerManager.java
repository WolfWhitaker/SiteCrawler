package com.github.wolfwhitaker.sitecrawler.app;

import com.github.wolfwhitaker.sitecrawler.app.view.BrowserView;
import com.github.wolfwhitaker.sitecrawler.app.view.SettingsView;
import com.github.wolfwhitaker.sitecrawler.app.view.SiteCrawlerView;
import com.github.wolfwhitaker.sitecrawler.dao.DAOFactory;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;

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

    /* private constants */

    private final SiteCrawlerView settings;
    private final SiteCrawlerView browser;

    /**
     * This enum class contains the names of all objects which are managed by the
     * SiteCrawlerManager class.
     */
    public enum ManageableObjects {

        SETTINGS,
        BROWSER

    }

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
        }
        assert false: "SiteCrawlerManager cannot find such object!";
    }

}