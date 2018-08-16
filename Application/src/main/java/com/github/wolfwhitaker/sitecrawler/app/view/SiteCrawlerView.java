package com.github.wolfwhitaker.sitecrawler.app.view;

import com.github.wolfwhitaker.sitecrawler.app.Manageable;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;
import com.github.wolfwhitaker.sitecrawler.mvc.View;

import java.util.List;

/**
 * This is abstract SiteCrawler view representation.
 * Each SiteCrawler view must extend this class. It implements {@link Manageable}
 * interface. So, all it's instances can be managed by
 * {@link com.github.wolfwhitaker.sitecrawler.app.SiteCrawlerManager}
 *
 * @author WolfWhitaker
 */
public abstract class SiteCrawlerView extends View<Model<List<WebPage>>, List<WebPage>>
        implements Manageable {}
