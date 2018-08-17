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

package com.github.wolfwhitaker.sitecrawler.app.controller;

import com.github.wolfwhitaker.sitecrawler.dao.DAOFactory;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;
import com.wolfwhitaker.sitecrawler.crawler.CrawlerSettings;
import com.wolfwhitaker.sitecrawler.crawler.PostgreSQLCrawlerController;

import java.util.List;

/**
 * Represents a controller for the
 * {@link com.github.wolfwhitaker.sitecrawler.app.view.SettingsView} object.
 * All interactions with the model, the database, and the crawler occur through
 * the instance of this class.
 *
 * @author WolfWhitaker
 */
public class SettingsController {

    /* Actions */

    /**
     * Makes a crawl with updating of the model state afterwards.
     * @param crawlSettings Settings for this crawl.
     * @param model The model which has to be updated after the crawl.
     */
    public void download(CrawlerSettings crawlSettings, Model<List<WebPage>> model) {
        PostgreSQLCrawlerController.makeCrawl(crawlSettings);
        DAOFactory dao = DAOFactory.getInstance(DAOFactory.POSTGRESQL);
        List<WebPage> list = dao.getWebPageDAO().listById();
        model.setProperty(list);
    }

}