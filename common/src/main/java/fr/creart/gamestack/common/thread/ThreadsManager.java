package fr.creart.gamestack.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Destroyable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Creart
 */
public final class ThreadsManager implements Destroyable, AutoCloseable {

    private static final ThreadGroup PARENT_GROUP = new ThreadGroup("GameStack");

    private final ThreadGroup currentGroup;
    private ExecutorService cachedGlobalService;
    private boolean initialized;

    public ThreadsManager(String softName)
    {
        currentGroup = new ThreadGroup(PARENT_GROUP, softName);
        cachedGlobalService = Executors.newCachedThreadPool(newThreadFactory("All"));
    }

    @Override
    public void close() throws Exception
    {
        destroy();
    }

    @Override
    public void destroy()
    {
        if (!initialized)
            return;

        cachedGlobalService.shutdown();

        currentGroup.destroy();
        PARENT_GROUP.destroy();

        initialized = false;
    }

    /**
     * Returns the global executor service
     *
     * @return the global executor service
     */
    public ExecutorService getCachedGlobalService()
    {
        return cachedGlobalService;
    }

    /**
     * Returns a new thread group whose parent is the thread group of the soft.
     *
     * @param name Thread group's name
     * @return a new thread group whose parent is the thread group of the soft.
     */
    public ThreadGroup newThreadGroup(String name)
    {
        return new ThreadGroup(currentGroup, name);
    }

    /**
     * Returns a new {@link ThreadFactory} with the precised thread group name.
     *
     * @param groupName Thread group's name
     * @return a new {@link ThreadFactory} with the precised thread group name.
     */
    public ThreadFactory newThreadFactory(String groupName)
    {
        return newThreadFactory(newThreadGroup(groupName));
    }

    /**
     * Returns a new {@link ThreadFactory} with the precised thread group.
     *
     * @param group Thread group's name
     * @return a new {@link ThreadFactory} with the precised thread group.
     */
    public ThreadFactory newThreadFactory(ThreadGroup group)
    {
        return new ThreadFactoryBuilder()
                .setThreadFactory(runnable -> new Thread(group, runnable))
                .setUncaughtExceptionHandler(new DefaultExceptionHandler())
                .build();
    }

    private class DefaultExceptionHandler implements UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e)
        {
            CommonLogger.error(String.format("An uncaught exception has been thrown (Thread=%s).", t.getName()), e);
        }
    }

}
