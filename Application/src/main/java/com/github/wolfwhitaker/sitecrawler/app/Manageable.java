package com.github.wolfwhitaker.sitecrawler.app;

/**
 * Each class which should be under run of a manager class must
 * implement this interface.
 */
public interface Manageable {

    /**
     * Disables the object.
     */
    void disable();

    /**
     * Makes the object active.
     */
    void activate();

}
