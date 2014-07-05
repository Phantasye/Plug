package com.plug.core.event;

/**
 * A container class that holds fields for an event as well as providing
 * implementation for the event itself. This class has been modified to be the
 * wrapper and the event provider at the same time because having an interface
 * packed in this class is pointless when we can (and should) be using an
 * abstract class.
 * 
 * @author lare96
 * @author Stuart
 */
public abstract class CycleEvent {

    /** The key for this event. */
    private Object key;

    /** If this event is running. */
    private boolean isRunning;

    /** The amount of cycles per event execution */
    private int tick;

    /** The current amount of cycles passed. */
    private int cyclesPassed;

    /**
     * Create a new {@link CycleEvent}.
     * 
     * @param key
     *        the key for this event.
     * @param event
     *        the actual event that will be packed in this container.
     * @param tick
     *        the cycles between execution of the event.
     */
    public CycleEvent(Object key, int tick) {
        this.key = key;
        this.isRunning = true;
        this.cyclesPassed = 0;
        this.tick = tick;
    }

    /**
     * Stop the event from running.
     */
    public void stop() {
        isRunning = false;
        fireOnStop();
    }

    /**
     * Checks if the event needs to be ran.
     * 
     * @return true if the event needs to be ran.
     */
    public boolean needsExecution() {
        if (++this.cyclesPassed >= tick) {
            this.cyclesPassed = 0;
            return true;
        }
        return false;
    }

    /**
     * Code which should be ran when the event is executed.
     * 
     * @param container
     *        the container that this event was created for.
     */
    public abstract void execute();

    /**
     * Code which should be ran when the event stops. Overriding this method is
     * optional.
     */
    public void fireOnStop() {

    }

    /**
     * Gets the key for this event.
     * 
     * @return the key for this event.
     */
    public Object getKey() {
        return key;
    }

    /**
     * Determines if the event is running.
     * 
     * @return true if this event is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Set the amount of cycles between the execution. This can be used for
     * dynamic runtime delay changes.
     * 
     * @param tick
     *        the amount of cycles to set.
     */
    public void setTick(int tick) {
        this.tick = tick;
    }
}