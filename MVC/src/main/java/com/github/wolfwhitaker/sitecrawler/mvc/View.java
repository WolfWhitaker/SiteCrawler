package com.github.wolfwhitaker.sitecrawler.mvc;

/**
 * Represents an abstract view. It is a part of the MVC pattern. It implements
 * {@link ModelSubscriber} to be aware of model changing.
 * @param <M> The model.
 * @param <P> The model property.
 */
public abstract class View<M extends Model<P>, P> implements ModelSubscriber<P> {

    private M model;

    protected M getModel() {
        return model;
    }

    public void setModel(M model) {
        unsubscribe();
        this.model = model;
        subscribe();
    }

    /**
     *Subscribes to the model events.
     */
    private void subscribe() {
        if (model != null)
            model.subscribe(this);
    }

    /**
     * Unsubscribes from the model events.
     */
    private void unsubscribe() {
        if (model != null)
            model.unsubscribe(this);
    }

    /**
     * Disposes this view.
     */
    public void dispose() {
        unsubscribe();
    }

}