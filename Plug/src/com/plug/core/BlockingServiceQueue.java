package com.plug.core;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Blocks until a set of services are carried out.
 * 
 * @author lare96
 */
public class BlockingServiceQueue implements Executor {

    /** The tasks that will be carried out by the <code>taskPool</code>. */
    private ConcurrentLinkedQueue<Runnable> serviceLoad = new ConcurrentLinkedQueue<Runnable>();

    /** The thread pool that will execute services in parallel. */
    private ExecutorService servicePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ServiceThreadProvider());

    /** The phaser for blocking until the services are complete. */
    private Phaser phaser = new Phaser(1);

    @Override
    public void execute(final Runnable command) {
        phaser.register();

        serviceLoad.add(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    phaser.arrive();
                }
            }
        });
    }

    /**
     * Execute all of the submitted services and block until they are completed.
     */
    public void startAndAwait() {
        Runnable task;

        while ((task = serviceLoad.poll()) != null) {
            servicePool.submit(task);
        }

        phaser.arriveAndAwaitAdvance();
    }

    /**
     * Clears the backing service queue and terminates the
     * <code>servicePool</code>.
     */
    public void shutdown() {
        serviceLoad.clear();
        servicePool.shutdownNow();
    }
}
