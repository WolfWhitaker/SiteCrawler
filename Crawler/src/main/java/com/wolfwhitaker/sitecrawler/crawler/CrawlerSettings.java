package com.wolfwhitaker.sitecrawler.crawler;

/**
 * Encapsulates settings parameters for a crawling. It is made immutable class
 * due to security and thread safety reasons.
 */
public final class CrawlerSettings {

    /* Private variables */

    private final String domainName;
    private final String restriction;
    private final String elementHTML;
    private final Integer maxPages;

    /* Constructors */

    public CrawlerSettings(String domainName, String restriction,
                           String elementHTML, Integer maxPages) {
        this.domainName = domainName;
        this.restriction = restriction;
        this.elementHTML = elementHTML;
        this.maxPages = maxPages;
    }

    /* Accessors */

    public String getDomainName() {
        return domainName;
    }

    public String getRestriction() {
        return restriction;
    }

    public String getElementHTML() {
        return elementHTML;
    }

    public Integer getMaxPages() {
        return maxPages;
    }

}
