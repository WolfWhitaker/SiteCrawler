package com.github.wolfwhitaker.sitecrawler.app.view;

import com.github.wolfwhitaker.sitecrawler.app.Manageable;
import com.github.wolfwhitaker.sitecrawler.app.SiteCrawlerManager;
import com.github.wolfwhitaker.sitecrawler.app.controller.BrowserController;
import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.mvc.Model;
import com.github.wolfwhitaker.sitecrawler.mvc.View;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.util.List;

/**
 * This class is a part of the SiteCrawler GUI.
 * Represents a view for the settings frame.
 *
 * @author WolfWhitaker
 */
public class BrowserView extends View<Model<List<WebPage>>, List<WebPage>>
        implements Manageable {

    /* Private constants */

    private static final int FRAME_WIDTH  = 1024;
    private static final int FRAME_HEIGHT = 700;

    private final BrowserController controller = new BrowserController();

    private final SiteCrawlerManager manager;

    private final JEditorPane editorPane = new JEditorPane();

    private final JTextField searchField = new JTextField(30);

    private final JFrame browserFrame = new JFrame("SiteCrawler Browser");

    /* Private variables */

    private int currentPageNumber;

    /**
     * Constructs BrowserView object with this manager
     * @param manager The manager class which runs this view.
     */
    public BrowserView(SiteCrawlerManager manager) {
        this.manager = manager;
        browserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browserFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        editorPane.setEditable(false);
        HTMLEditorKit kit = new HTMLEditorKit();
        editorPane.setEditorKit(kit);
        editorPane.setContentType("text/html");
        javax.swing.text.Document doc = kit.createDefaultDocument();
        editorPane.setDocument(doc);

        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(editorPane);
        scrollPane.revalidate();

        JPanel bottomPanel = createBottomPanel();

        browserFrame.getContentPane().setLayout(new BorderLayout());
        browserFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        browserFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        browserFrame.setLocationRelativeTo(null);
    }

    @Override
    public void modelChanged(Model<List<WebPage>> model) {
        currentPageNumber = 0;
        printPage();
    }

    /*
     * Creates a panel on the south side of the frame.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();

        JButton settings = new JButton("Settings");
        panel.add(settings);
        settings.addActionListener(e ->
                manager.switchActive(this,
                        SiteCrawlerManager.ManageableObjects.SETTINGS));

        JButton previousButton = new JButton("Previous");
        panel.add(previousButton);
        previousButton.addActionListener(e -> {
            if (currentPageNumber > 0) {
                currentPageNumber--;
                printPage();
            }
        });

        JButton nextButton = new JButton("  Next  ");
        panel.add(nextButton);
        nextButton.addActionListener(e -> {
            if (currentPageNumber < getModel().getProperty().size() - 1) {
                currentPageNumber++;
                printPage();
            }
        });

        JButton clearData = new JButton("Clear data");
        panel.add(clearData);
        clearData.addActionListener(e ->
                new Thread(() -> controller.clearData(getModel())).start());

        JButton searchButton = new JButton("Search:");
        panel.add(searchButton);
        searchButton.addActionListener(e ->
                new Thread(() -> controller.search(getModel(),
                        searchField.getText())).start());

        panel.add(searchField);
        return panel;
    }

    /*
     * Sets a page with the index equal to currentPageNumber as the
     * editor pane's content.
     */
    private void printPage() {
        List<WebPage> list = getModel().getProperty();
        if (list != null && !list.isEmpty()) {
            WebPage page = list.get(currentPageNumber);
            editorPane.setText("<html><body>" + page.getContent() + "</html></body>");
        } else {
            editorPane.setText("There is no element matching. Try to search some other " +
                    "phrase or return to the settings and download necessary data!");
        }
    }

    @Override
    public void disable() {
        browserFrame.setVisible(false);
    }

    @Override
    public void activate() {
        new Thread(() -> {
            controller.updateModel(getModel());
            browserFrame.setVisible(true);
        }).start();
    }

}
