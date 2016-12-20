/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection;

import fr.creart.gamestack.common.lang.AtomicWrapper;
import fr.creart.gamestack.common.lang.Wrapper;
import fr.creart.gamestack.common.misc.Callback;
import fr.creart.gamestack.common.misc.Destroyable;
import fr.creart.gamestack.common.thread.ThreadsUtil;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 * This class represents an object which contains a connection.
 * It is thread-safe.
 * <p>
 * /!\ Set the {@link ConnectionData} before you connect /!\
 *
 * {@inheritDoc}
 * @author Creart
 */
public abstract class ConnectionContainer<T, D extends ConnectionData>
        implements AutoCloseable, Destroyable {

    private static final byte MAX_THREADS = 10;

    private Lock taskLock = new ReentrantLock();
    protected final Logger logger = Logger.getLogger(getServiceName());
    protected T connection; // contained connection
    protected final Wrapper<ConnectionState> connectionState = new AtomicWrapper<>(ConnectionState.CLOSED);
    private final ConnectionTasksManager<T> taskHandler;

    /**
     * @param threads number of used threads (max = 10)
     */
    public ConnectionContainer(int threads)
    {
        ExecutorService service = threads <= 1 ? Executors.newSingleThreadExecutor() : Executors.newFixedThreadPool(Math.min(threads, MAX_THREADS));
        taskHandler = new ConnectionTasksManager<>(service, this);
    }

    /**
     * Initialises the connection if it is closed.
     *
     * @param connectionData data required in order to establish the connection (host, port... + credentials)
     */
    public final void initialise(D connectionData)
    {
        if (connectionState.get() == ConnectionState.CLOSING
                || connectionState.get() == ConnectionState.OPENING
                || connectionState.get().isUsable()) // cannot open the connection
            return;

        initialiseDriver();

        connectionState.set(ConnectionState.OPENING);

        synchronized (this) {
            while (!connect(connectionData)) {
                logger.error("Could not connect to the " + getServiceName() + " server. Trying again in 5 seconds.");
                ThreadsUtil.sleep(5000L);
            }
        }

        connectionState.set(ConnectionState.OPENED);

        logger.info("Successfully connected to the " + getServiceName() + "server.");
    }

    /**
     * Closes the connection if it has been established.
     */
    @Override
    public final void close()
    {
        destroy();
    }

    @Override
    public final void destroy()
    {
        if (!isConnectionEstablished())
            return;

        connectionState.set(ConnectionState.CLOSING);

        synchronized (this) {
            end();
        }

        connectionState.set(ConnectionState.CLOSED);
        taskHandler.destroy();
    }

    /**
     * Enqueues a new task
     *
     * @param callback task
     */
    protected final void enqueueTask(Callback<T> callback)
    {
        if (!connectionState.get().isUsable())
            return;

        taskLock.lock();
        try {
            taskHandler.enqueueTask(callback);
        } finally {
            taskLock.unlock();
        }
    }

    /**
     * Returns <tt>true</tt> if the connection has been established
     *
     * @return <tt>true</tt> if the connection has been established
     */
    public boolean isConnectionEstablished()
    {
        return connectionState.get().isUsable();
    }

    /**
     * Returns container's logger
     *
     * @return container's logger
     */
    public final Logger getLogger()
    {
        return logger;
    }

    /**
     * Returns the connection
     *
     * @return the connection
     */
    T getConnection()
    {
        return connection;
    }

    /**
     * Establishes the contained connection
     */
    protected abstract boolean connect(D connectionData);

    /**
     * Closes everything.
     */
    protected abstract void end();

    /**
     * Initialises the driver required that may be required by the container.
     */
    protected void initialiseDriver()
    {
    }

    /**
     * Returns the name of the service
     *
     * @return the name of the service
     */
    protected abstract String getServiceName();

}
