package server.core;

import java.util.concurrent.ThreadFactory;

/**
 * A {@link ThreadFactory} that will prepare the main game thread.
 * 
 * @author lare96
 */
public class GameThreadProvider implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.setName(Rs2Engine.class.getSimpleName());
        return thread;
    }
}
