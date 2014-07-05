package server.core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Handles all of our cycle based events. This class has been optimized to use
 * an iterator for better performance instead of instantiating multiple local
 * lists every single cycle.
 * 
 * @author lare96
 * @author Stuart
 */
public class CycleEventHandler {

    /** The singleton instance. */
    private static CycleEventHandler instance;

    /** Holds all of the events currently being ran. */
    private static List<CycleEvent> events = new ArrayList<CycleEvent>();

    /**
     * Adds an event to the list to be ticked and fired.
     * 
     * @param event
     *        the event to add to the list.
     */
    public void addEvent(CycleEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Need to submit an actual event!");
        }

        events.add(event);
    }

    /**
     * Adds events in bulk.
     * 
     * @param events
     *        the events to add.
     */
    public void addBulkEvent(CycleEvent... events) {
        if (events == null || events.length < 1) {
            throw new IllegalArgumentException("Need to submit at least one actual event!");
        }

        for (CycleEvent event : events) {
            if (event == null) {
                throw new IllegalStateException("Cannot submit a malformed event!");
            }

            addEvent(event);
        }
    }

    /**
     * Ticks and fires events that are ready to be fired. Also removes events
     * that are no longer running.
     */
    public void process() {

        // here we use an iterator instead of two lists
        for (Iterator<CycleEvent> iterator = events.iterator(); iterator.hasNext();) {
            CycleEvent nextEvent = iterator.next();

            if (nextEvent == null) {
                continue;
            }

            if (nextEvent.needsExecution() && nextEvent.isRunning()) {
                nextEvent.execute();
            }

            if (!nextEvent.isRunning()) {
                iterator.remove();
            }
        }
    }

    /**
     * Stops all events with the given key.
     * 
     * @param key
     *        the key to stop all events with.
     */
    public void stopEvents(Object key) {
        for (CycleEvent c : events) {
            if (c.getKey() == key) {
                c.stop();
            }
        }
    }

    /**
     * Gets an unmodifiable list of the events currently running.
     * 
     * @return an unmodifiable list of the events currently running.
     */
    public List<CycleEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Sets (if needed) and gets the singleton instance.
     * 
     * @return the singleton instance.
     */
    public static CycleEventHandler getSingleton() {
        if (instance == null) {
            instance = new CycleEventHandler();
        }
        return instance;
    }
}