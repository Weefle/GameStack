/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Destroyable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @author Creart
 */
public final class ThreadsManager implements Destroyable, AutoCloseable {

    private static final ThreadGroup PARENT_GROUP = new ThreadGroup("GameStack");

    private final ThreadGroup currentGroup;
    private boolean initialized;

    /**
     * @param softName current software's name
     */
    public ThreadsManager(String softName)
    {
        currentGroup = new ThreadGroup(PARENT_GROUP, softName);
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

        currentGroup.destroy();
        PARENT_GROUP.destroy();

        initialized = false;
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
