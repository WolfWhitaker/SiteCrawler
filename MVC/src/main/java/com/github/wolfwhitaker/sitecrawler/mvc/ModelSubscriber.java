package com.github.wolfwhitaker.sitecrawler.mvc;

/**
 * Utilizes the "Subscriber" pattern to provide event notifications of the publisher.
 * All model subscribers have to implement this interface.
 * @param <P> The model property.
 */
public interface ModelSubscriber<P> {

    /**
     * Processes model changing event.
     * @param model The model has been changed.
     */
    void modelChanged(Model<P> model);

}