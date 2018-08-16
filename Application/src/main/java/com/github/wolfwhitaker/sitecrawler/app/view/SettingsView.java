package com.github.wolfwhitaker.sitecrawler.app.view;

import com.github.wolfwhitaker.sitecrawler.app.SiteCrawlerManager;
import com.github.wolfwhitaker.sitecrawler.app.controller.SettingsController;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;
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
public class SettingsView extends SiteCrawlerView {

    /* Private constants */

    private static final int FRAME_WIDTH  = 410;
    private static final int FRAME_HEIGHT = 520;

    private static final int MAX_PAGES_TO_CRAWL = 10000;
    private static final int MIN_PAGES_TO_CRAWL = 1;

    private final JTextField domainName  = new JTextField(20);
    private final JTextField maxPages    = new JTextField(20);
    private final JTextField placedIn    = new JTextField(20);
    private final JTextField elementHTML = new JTextField(20);

    private final JButton download = new JButton("Download");
    private final JButton proceed  = new JButton("Proceed");

    private final JTextArea info = new JTextArea();

    private final SiteCrawlerManager manager;

    private final JFrame settingsFrame;

    private final SettingsController controller = new SettingsController();

    //private SwingWorker downloadingProcess;

    /* Constructors */

    /**
     * Constructs SettingsView object with this manager
     * @param manager The manager class which runs this view.
     */
    public SettingsView(SiteCrawlerManager manager) {
        this.manager = manager;
        settingsFrame = new JFrame("SiteCrawler Settings");
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
        content.add(new JLabel("Domain name: "), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(domainName, constraints);

        // Max pages row
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        content.add(new JLabel("Max pages: "), constraints);

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
        content.add(new JLabel("Pages placed in:"), constraints);

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
        content.add(new JLabel("HTML element:"), constraints);

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
                info.setText("\nEnter valid data:\n\nYou should set domain name and max pages" +
                        "\nto start downloading.\nMax pages should be between " +
                        MIN_PAGES_TO_CRAWL + " and " + MAX_PAGES_TO_CRAWL + ".");
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
            info.setText("\nDownloading finished: There " + (pagesNumber == 1 ? "is " : "are ") +
                    pagesNumber + (pagesNumber == 1 ? " page " : " pages ") + "in the \ndatabase." +
                    "\nPress \"Proceed\" to start browse downloaded data.");
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
        info.setText("\nDownloading started, please be patient..." +
                "\nIt may take several minutes.");
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
        info.setText("Start to work with SiteCrawler!\n" +
                "Example:" +
                "\n    Domain name:     https://ekaterinburg.superjob.ru" +
                "\n    Max pages:            200" +
                "\n    Pages placed in:  vakansii/" +
                "\n    HTML element:    div.VacancyView_body\n" +
                "Downloads 200 (or less) containers with" +
                "\nclass=\"VacancyView_body\" from pages which placed" +
                "\nin https://ekaterinburg.superjob.ru/vakansii/\n" +
                "\nEnter data and click \"Download\" to start crawling." +
                "\n\n-You have to enter at least domain name to" +
                "\n start crawing!" +
                "\n-\"Max pages\" field must be between " +
                MIN_PAGES_TO_CRAWL + " and " + MAX_PAGES_TO_CRAWL + "!" +
                "\n-\"Pages placed in\" must be a part of a full page" +
                "\n address right after the domain name!" +
                "\n-\"HTML element\" one of jsoup selectors!");
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