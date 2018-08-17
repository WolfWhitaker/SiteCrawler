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
