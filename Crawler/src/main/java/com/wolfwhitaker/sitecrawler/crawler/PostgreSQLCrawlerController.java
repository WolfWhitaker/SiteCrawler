package com.wolfwhitaker.sitecrawler.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class PostgreSQLCrawlerController {

    /* Private constants */

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
            System.out.println("Crawling failed: " + ex); //TODO: replace with a logger
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
