package com.github.wolfwhitaker.sitecrawler.app;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provides strings according to the chosen internationalization policy.
 * This class is final with "hardcoded" logic and is not supposed to be extended.
 */
public final class I18N {

    /* Public constants */

    public static final String SETTINGS_FRAME_NAME;
    public static final String SETTINGS_BUTTON_DOWNLOAD;
    public static final String SETTINGS_BUTTON_PROCEED;
    public static final String SETTINGS_LABEL_DOMAIN;
    public static final String SETTINGS_LABEL_MAX_PAGES;
    public static final String SETTINGS_LABEL_PLACED_IN;
    public static final String SETTINGS_LABEL_ELEMENT;
    public static final String SETTINGS_INFO_INVALID_DATA;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED;
    public static final String SETTINGS_INFO_DOWNLOADING_STARTED;
    public static final String SETTINGS_INFO_HOW_TO_USE;

    public static final String BROWSER_FRAME_NAME;
    public static final String BROWSER_BUTTON_SETTINGS;
    public static final String BROWSER_BUTTON_PREVIOUS;
    public static final String BROWSER_BUTTON_NEXT;
    public static final String BROWSER_BUTTON_CLEAR_DATA;
    public static final String BROWSER_BUTTON_SEARCH;
    public static final String BROWSER_INFO_EMPTY_RESULT;

    static {
        Locale currentLocale = Locale.ENGLISH;
        ResourceBundle bundle = ResourceBundle.getBundle(
                "I18N", currentLocale);

        // SettingsView section
        SETTINGS_FRAME_NAME = bundle.getString("settings.frame.name");

        SETTINGS_BUTTON_DOWNLOAD = bundle.getString("settings.button.download");
        SETTINGS_BUTTON_PROCEED = bundle.getString("settings.button.proceed");

        SETTINGS_LABEL_DOMAIN = bundle.getString("settings.label.domain");
        SETTINGS_LABEL_MAX_PAGES = bundle.getString("settings.label.maxpages");
        SETTINGS_LABEL_PLACED_IN = bundle.getString("settings.label.placedin");
        SETTINGS_LABEL_ELEMENT = bundle.getString("settings.label.element");

        SETTINGS_INFO_INVALID_DATA = bundle.getString("settings.info.invalid");
        SETTINGS_INFO_DOWNLOADING_FINISHED = bundle.getString("settings.info.finished");
        SETTINGS_INFO_DOWNLOADING_STARTED = bundle.getString("settings.info.started");
        SETTINGS_INFO_HOW_TO_USE = bundle.getString("settings.info.howtouse");

        // BrowserView section
        BROWSER_FRAME_NAME = bundle.getString("browser.frame.name");

        BROWSER_BUTTON_SETTINGS = bundle.getString("browser.button.settings");
        BROWSER_BUTTON_PREVIOUS = bundle.getString("browser.button.previous");
        BROWSER_BUTTON_NEXT = bundle.getString("browser.button.next");
        BROWSER_BUTTON_CLEAR_DATA = bundle.getString("browser.button.cleardata");
        BROWSER_BUTTON_SEARCH = bundle.getString("browser.button.search");

        BROWSER_INFO_EMPTY_RESULT = bundle.getString("browser.info.emptyresult");
    }

    /* Constructors */

    private I18N() {
        // Hide constructor
    }

}
