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

import com.github.wolfwhitaker.sitecrawler.dao.DAOFactory;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Pattern;

/**
 * This Crawler class is made on the basis of crawler4j {@link WebCrawler}. It
 * saves all appropriate pages to the PostgreSQL base. In case elementToParse is
 * not empty it uses Jsoup library to parse necessary HTML element. The field
 * domainName must be filled to start crawling.
 */
public class PostgreSQLCrawler extends WebCrawler {

    /* Private constants */
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    /* Private variables */

    private static String domainName;
    private static String restriction;
    private static String elementToParse;

    /* Accessors */

    public static void setDomainName(String domainName) {
        PostgreSQLCrawler.domainName = domainName;
    }

    public static void setRestriction(String restriction) {
        PostgreSQLCrawler.restriction = restriction;
    }

    public static void setElementToParse(String elementToParse) {
        PostgreSQLCrawler.elementToParse = elementToParse;
    }

    /* Actions */

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith(domainName);
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            String url = page.getWebURL().getURL().toLowerCase();

            System.out.println("Visited: " + url);    //TODO: replace with a logger

            if (url.startsWith(domainName + "/" + restriction)) {
                System.out.println("Downloaded: " + url);    //TODO: replace with a logger

                WebPage webPage = new WebPage();
                if (elementToParse.equals("")) {
                    webPage.setContent(html);
                } else {
                    Document doc = Jsoup.parse(html);
                    Element elm = doc.selectFirst(elementToParse);
                    String tmp = elm.html();
                    webPage.setContent(tmp);
                }
                webPage.setPlainText(text);

                DAOFactory dao = DAOFactory.getInstance(DAOFactory.POSTGRESQL);
                dao.getWebPageDAO().create(webPage);
            }
        }
    }

}
