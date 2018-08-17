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