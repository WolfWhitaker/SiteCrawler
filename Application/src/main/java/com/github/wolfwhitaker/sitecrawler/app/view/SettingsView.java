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

package com.github.wolfwhitaker.sitecrawler.app.view;

import com.github.wolfwhitaker.sitecrawler.app.I18N;
import com.github.wolfwhitaker.sitecrawler.app.Manageable;
import com.github.wolfwhitaker.sitecrawler.app.SiteCrawlerManager;
import com.github.wolfwhitaker.sitecrawler.app.controller.SettingsController;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;
import com.github.wolfwhitaker.sitecrawler.mvc.View;
import com.wolfwhitaker.sitecrawler.crawler.CrawlerSettings;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is a part of the SiteCrawler GUI.
 * Represents a view for the settings frame.
 *
 * @author WolfWhitaker
 */
public class SettingsView extends View<Model<List<WebPage>>, List<WebPage>>
        implements Manageable {

    /* Private constants */

    private static final int FRAME_WIDTH  = 410;
    private static final int FRAME_HEIGHT = 520;

    private static final int MAX_PAGES_TO_CRAWL = 10000;
    private static final int MIN_PAGES_TO_CRAWL = 1;

    private final JTextField domainName  = new JTextField(20);
    private final JTextField maxPages    = new JTextField(20);
    private final JTextField placedIn    = new JTextField(20);
    private final JTextField elementHTML = new JTextField(20);

    private final JButton download = new JButton(I18N.SETTINGS_BUTTON_DOWNLOAD);
    private final JButton proceed  = new JButton(I18N.SETTINGS_BUTTON_PROCEED);

    private final JTextArea info = new JTextArea();

    private final SiteCrawlerManager manager;

    private final JFrame settingsFrame;

    private final SettingsController controller = new SettingsController();

    /* Constructors */

    /**
     * Constructs a SettingsView object with this manager
     * @param manager The manager class which runs this view.
     */
    public SettingsView(SiteCrawlerManager manager) {
        this.manager = manager;
        settingsFrame = new JFrame(I18N.SETTINGS_FRAME_NAME);
        settingsFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        settingsFrame.setResizable(false);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final JPanel content = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        // Domain name row
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.NONE;
        content.add(new JLabel(I18N.SETTINGS_LABEL_DOMAIN), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(domainName, constraints);

        // Max pages row
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        content.add(new JLabel(I18N.SETTINGS_LABEL_MAX_PAGES), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(maxPages, constraints);

        // Pages placed in row
        constraints.weighty = 0.01;
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        content.add(new JLabel(I18N.SETTINGS_LABEL_PLACED_IN), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(placedIn, constraints);

        // HTML element row
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        content.add(new JLabel(I18N.SETTINGS_LABEL_ELEMENT), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(elementHTML, constraints);

        // Information text
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        constraints.gridheight = 4;
        constraints.ipady = 20;
        constraints.fill = GridBagConstraints.BOTH;
        info.setPreferredSize(new Dimension(150, 260));
        info.setEditable(false);
        content.add(info, constraints);

        // Buttons row
        constraints.ipady = 0;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.gridx = 3;
        constraints.gridy = 10;
        content.add(proceed, constraints);
        proceed.addActionListener(e -> proceed());

        constraints.gridx = 2;
        constraints.weightx = 1;
        content.add(download, constraints);
        download.addActionListener(e -> {
            if (validateData()) {
                startCrawling();
            } else {
                info.setText(I18N.SETTINGS_INFO_INVALID_DATA1 + MIN_PAGES_TO_CRAWL +
                             I18N.SETTINGS_INFO_INVALID_DATA2 + MAX_PAGES_TO_CRAWL +
                             I18N.SETTINGS_INFO_INVALID_DATA3);
            }
        });

        settingsFrame.getContentPane().add(content);
    }

    /* Actions */

    @Override
    public void modelChanged(Model<List<WebPage>> model) {
        if (domainName.getText().equals("")) {
            provideStartingInfo();
        } else {
            int pagesNumber = getModel().getProperty().size();
            info.setText(I18N.SETTINGS_INFO_DOWNLOADING_FINISHED1 +
                    (pagesNumber == 1 ? I18N.SETTINGS_INFO_DOWNLOADING_FINISHED2 :
                            I18N.SETTINGS_INFO_DOWNLOADING_FINISHED3) +
                    pagesNumber + (pagesNumber == 1 ? I18N.SETTINGS_INFO_DOWNLOADING_FINISHED4 :
                    I18N.SETTINGS_INFO_DOWNLOADING_FINISHED5) + I18N.SETTINGS_INFO_DOWNLOADING_FINISHED6);
        }
    }

    @Override
    public void disable() {
        settingsFrame.setVisible(false);
    }

    @Override
    public void activate() {
        settingsFrame.setVisible(true);
    }

    /*
     * Prompts manager to change the GUI from current view to BrowserView
     */
    private void proceed() {
        info.setText("");
        settingsFrame.validate();
        manager.switchActive(this, SiteCrawlerManager.ManageableObjects.BROWSER);
    }

    /*
     * Sets the frame to the downloading mode and prompts controller to start crawling.
     */
    private void startCrawling() {
        info.setText(I18N.SETTINGS_INFO_DOWNLOADING_STARTED);
        setDownloadingState(true);
        CrawlerSettings crawlerSettings = prepareCrawlerSettings();

        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() {
                controller.download(crawlerSettings, getModel());
                return null;
            }

            @Override
            protected void done() {
                setDownloadingState(false);
            }

        }.execute();
    }

    /*
     * Prepares CrawlingSettings object with the settings provided by the user.
     */
    private CrawlerSettings prepareCrawlerSettings() {
        return new CrawlerSettings(domainName.getText(),
                                   placedIn.getText(),
                                   elementHTML.getText(),
                                   Integer.parseInt(this.maxPages.getText()));
    }

    /*
     * Sets current state of the frame
     */
    private void setDownloadingState(boolean isDownloading) {
        download.setEnabled(!isDownloading);
        proceed.setEnabled(!isDownloading);
        domainName.setEnabled(!isDownloading);
        maxPages.setEnabled(!isDownloading);
        placedIn.setEnabled(!isDownloading);
        elementHTML.setEnabled(!isDownloading);
    }

    /*
     * Adds starting information to the info TextArea.
     */
    private void provideStartingInfo() {
        info.setText(I18N.SETTINGS_INFO_HOW_TO_USE1 +
                MIN_PAGES_TO_CRAWL + I18N.SETTINGS_INFO_HOW_TO_USE2 +
                MAX_PAGES_TO_CRAWL + I18N.SETTINGS_INFO_HOW_TO_USE3);
    }

    /*
     * Checks weather the entered data are appropriate.
     */
    private boolean validateData() {
        if (domainName.getText().equals("")) return false;
        if (maxPages.getText().length() <= 0) return false;
        try {
            int tmp = Integer.parseInt(maxPages.getText());
            if (tmp <= 0 || tmp > 1000) return false;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}