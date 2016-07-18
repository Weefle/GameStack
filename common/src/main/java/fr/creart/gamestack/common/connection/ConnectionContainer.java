package fr.creart.gamestack.common.connection;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.lang.AtomicWrapper;
import fr.creart.gamestack.common.lang.Wrapper;
import fr.creart.gamestack.common.misc.Initialisable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents an object which contains a connection.
 * It is thread-safe.
 *
 * @author Creart
 */
public abstract class ConnectionContainer<T>
        implements AutoCloseable, Initialisable {

    private static final byte MAX_THREADS = 10;

    protected final Wrapper<ConnectionState> connectionState = new AtomicWrapper<>(ConnectionState.CLOSED);
    private final TaskHandler taskHandler;

    public ConnectionContainer(int threads)
    {
        ExecutorService service = threads <= 1 ? Executors.newSingleThreadExecutor() : Executors.newFixedThreadPool(Math.min(threads, MAX_THREADS));
        taskHandler = new TaskHandler(service, this);
    }

    /**
     * Initializes the connection if it is closed.
     */
    @Override
    public final void initialize()
    {
        if (connectionState.get() == ConnectionState.CLOSING
                || connectionState.get() == ConnectionState.OPENING
                || connectionState.get().isUsable()) // cannot open the connection
            return;

        connectionState.set(ConnectionState.OPENING);

        synchronized (this) {
            connect();
        }

        connectionState.set(ConnectionState.OPENED);
    }

    /**
     * Closes the connection if it has been established.
     */
    @Override
    public final void close()
    {
        if (!isEstablished())
            return;

        connectionState.set(ConnectionState.CLOSING);

        synchronized (this) {
            end();
        }

        connectionState.set(ConnectionState.CLOSED);
    }

    /**
     * Returns {@code true} if the connection has been established
     *
     * @return {@code true} if the connection has been established
     */
    public final boolean isEstablished()
    {
        return connectionState.get() == ConnectionState.OPENED;
    }

    /**
     * Establishes the contained connection
     */
    protected abstract void connect();

    /**
     * Closes everything.
     */
    protected abstract void end();

    private class TaskHandler {

        private ConnectionContainer<T> container;
        private ExecutorService service;

        public TaskHandler(ExecutorService service, ConnectionContainer<T> container)
        {
            Preconditions.checkNotNull(service, "service can't be null");
            Preconditions.checkNotNull(container, "container can't be null");
            Preconditions.checkArgument(!service.isShutdown(), "service should not be shutdown");

            this.service = service;
            this.container = container;
        }

        public void shutdown()
        {
            service.shutdown();
        }

    }

}
