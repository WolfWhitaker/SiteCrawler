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
    public static final String SETTINGS_INFO_INVALID_DATA1;
    public static final String SETTINGS_INFO_INVALID_DATA2;
    public static final String SETTINGS_INFO_INVALID_DATA3;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED1;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED2;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED3;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED4;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED5;
    public static final String SETTINGS_INFO_DOWNLOADING_FINISHED6;
    public static final String SETTINGS_INFO_DOWNLOADING_STARTED;
    public static final String SETTINGS_INFO_HOW_TO_USE1;
    public static final String SETTINGS_INFO_HOW_TO_USE2;
    public static final String SETTINGS_INFO_HOW_TO_USE3;

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

        SETTINGS_INFO_INVALID_DATA1 = bundle.getString("settings.info.invalid1");
        SETTINGS_INFO_INVALID_DATA2 = bundle.getString("settings.info.invalid2");
        SETTINGS_INFO_INVALID_DATA3 = bundle.getString("settings.info.invalid3");
        SETTINGS_INFO_DOWNLOADING_FINISHED1 = bundle.getString("settings.info.finished1");
        SETTINGS_INFO_DOWNLOADING_FINISHED2 = bundle.getString("settings.info.finished2");
        SETTINGS_INFO_DOWNLOADING_FINISHED3 = bundle.getString("settings.info.finished3");
        SETTINGS_INFO_DOWNLOADING_FINISHED4 = bundle.getString("settings.info.finished4");
        SETTINGS_INFO_DOWNLOADING_FINISHED5 = bundle.getString("settings.info.finished5");
        SETTINGS_INFO_DOWNLOADING_FINISHED6 = bundle.getString("settings.info.finished6");
        SETTINGS_INFO_DOWNLOADING_STARTED = bundle.getString("settings.info.started");

        SETTINGS_INFO_HOW_TO_USE1 = bundle.getString("settings.info.howtouse1");
        SETTINGS_INFO_HOW_TO_USE2 = bundle.getString("settings.info.howtouse2");
        SETTINGS_INFO_HOW_TO_USE3 = bundle.getString("settings.info.howtouse3");

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
