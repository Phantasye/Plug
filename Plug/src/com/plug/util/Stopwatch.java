package com.plug.util;

/**
 * A simple timing utility.
 * 
 * @author blakeman8192
 * @author lare96
 */
public class Stopwatch {

    /** The cached time. */
    private long time = System.currentTimeMillis();

    /**
     * Resets this stopwatch.
     * 
     * @return this stopwatch.
     */
    public Stopwatch reset() {
        time = System.currentTimeMillis();
        return this;
    }

    /**
     * Returns the amount of time elapsed (in milliseconds) since this object
     * was initialized, or since the last call to the "reset()" method.
     * 
     * @return the elapsed time (in milliseconds).
     */
    public long elapsed() {
        return System.currentTimeMillis() - time;
    }
}