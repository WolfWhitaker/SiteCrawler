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

package com.wolfwhitaker.sitecrawler.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgreSQLCrawlerController {

    /* Private constants */

    private static final Logger logger = LoggerFactory.getLogger(PostgreSQLCrawlerController.class);

    private static final int NUMBER_OF_CRAWLERS = 1;
    private static final int POLITENESS_DELAY   = 300; // Interruption time between crawlings.

    private static final String STORAGE_FOLDER = "/data/crawl/root"; // Crawler storage folder.

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 " +
                    "(KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    /**
     * Makes a crawl with the given settings.
     * @param settings The setting for this crawl.
     */
    public static void makeCrawl(CrawlerSettings settings) {
        try {
            PostgreSQLCrawler.setDomainName(settings.getDomainName());
            PostgreSQLCrawler.setRestriction(settings.getRestriction());
            PostgreSQLCrawler.setElementToParse(settings.getElementHTML());
            crawl(settings.getDomainName(), settings.getMaxPages());
        } catch (Exception ex) {
            logger.error("Crawling failed" + ex);
        }

    }

    /*
     * Blocking method! Starts crawling.
     */
    private static void crawl(String url, int maxPagesToCrawl) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(STORAGE_FOLDER);
        config.setUserAgentString(USER_AGENT);
        config.setMaxPagesToFetch(maxPagesToCrawl);
        config.setPolitenessDelay(POLITENESS_DELAY);
        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(url);

        controller.start(PostgreSQLCrawler.class, NUMBER_OF_CRAWLERS);
    }

}
