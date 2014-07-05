package server.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread factory that will be used to prepare worker threads used for
 * {@link BlockingServiceQueue}s.
 * 
 * @author lare96
 */
public class ServiceThreadProvider implements ThreadFactory {

    /**
     * Used to keep track of the amount of worker threads prepared by this
     * factory.
     */
    private AtomicInteger threadCount = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.NORM_PRIORITY + 1);
        thread.setName("WorkerThread-" + threadCount.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    }
}
