package com.github.wolfwhitaker.sitecrawler.mvc;

import com.github.wolfwhitaker.sitecrawler.mvc.exception.ModelException;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a generic model. It is a part of the MVC pattern. It utilizes the
 * "Publisher" pattern to notify it's views, that model property was changed.
 * @param <P> The model property.
 */
public class Model<P> {

    /* Private variables */

    private P property;

    private final Collection<ModelSubscriber<P>> subscribers = new CopyOnWriteArrayList<>();

    /* Constructors*/

    /**
     * Creates a model with this property.
     * @param property The model property.
     * @throws ModelException if the property is null.
     */
    public Model(P property) throws ModelException {
        if (property == null)
            throw new ModelException("The property is null.");
        this.property = property;
    }

    /* Getters\setters */

    public P getProperty() {
        return property;
    }

    public void setProperty(P property) throws ModelException {
        if (property == null)
            throw new ModelException("The property is null.");
        this.property = property;
        notifySubscribers();
    }

    /**
     * Notifies all subscribers of model changing.
     */
    private void notifySubscribers() {
        for (final ModelSubscriber<P> subscriber : subscribers)
            notifySubscriber(subscriber);
    }

    /**
     * Notifies the subscriber of model changing.
     * @param subscriber The model subscriber to be notified.
     */
    private void notifySubscriber(ModelSubscriber<P> subscriber) {
        assert subscriber != null;
        subscriber.modelChanged(this);
    }

    /**
     * Subscribes the subscriber to model events.
     * @param subscriber The model subscriber.
     * @throws ModelException if the property is null or
     * the subscribing is repeated.
     */
    public void subscribe(ModelSubscriber<P> subscriber) throws ModelException {
        if (subscriber == null)
            throw new ModelException("The property is empty.");
        if (subscribers.contains(subscriber))
            throw new ModelException("This subscriber already exists: " +
                    subscriber);
        subscribers.add(subscriber);
        notifySubscriber(subscriber);
    }

    /**
     * Unsubscribes the subscriber from the model events.
     * @param subscriber The model subscriber.
     */
    public void unsubscribe(ModelSubscriber<P> subscriber) {
        if (subscriber == null)
            throw new NullPointerException("The property is empty");
        if (!subscribers.contains(subscriber))
            throw new IllegalArgumentException("Unknown subscriber: " +
                    subscriber);
        subscribers.remove(subscriber);
    }

    @Override
    public String toString() {
        return property.toString();
    }

}