package fr.creart.gamestack.common.connection;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.lang.AtomicWrapper;
import fr.creart.gamestack.common.lang.Wrapper;
import fr.creart.gamestack.common.misc.ConnectionData;
import fr.creart.gamestack.common.misc.Destroyable;
import fr.creart.gamestack.common.misc.Initialisable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents an object which contains a connection.
 * It is thread-safe.
 * <p>
 * /!\ Set the {@link ConnectionData} before you connect /!\
 *
 * @author Creart
 */
public abstract class ConnectionContainer<T>
        implements AutoCloseable, Initialisable, Destroyable {

    private static final byte MAX_THREADS = 10;

    protected ConnectionData connectionData;
    protected T connection; // contained connection
    protected final Wrapper<ConnectionState> connectionState = new AtomicWrapper<>(ConnectionState.CLOSED);
    private final TaskHandler taskHandler;

    public ConnectionContainer(int threads)
    {
        ExecutorService service = threads <= 1 ? Executors.newSingleThreadExecutor() : Executors.newFixedThreadPool(Math.min(threads, MAX_THREADS));
        taskHandler = new TaskHandler(service, this);
    }

    /**
     * Sets the connection data
     *
     * @param connectionData New connection data
     */
    public final void setConnectionData(ConnectionData connectionData)
    {
        if (this.connectionData != null)
            return;

        this.connectionData = connectionData;
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
            connect(connectionData);
        }

        connectionState.set(ConnectionState.OPENED);
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
        return connectionState.get().isUsable();
    }

    /**
     * Establishes the contained connection
     */
    protected abstract void connect(ConnectionData connectionData);

    /**
     * Closes everything.
     */
    protected abstract void end();

    private class TaskHandler {

        private ConnectionContainer<T> container;
        private ExecutorService service;

        TaskHandler(ExecutorService service, ConnectionContainer<T> container)
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
