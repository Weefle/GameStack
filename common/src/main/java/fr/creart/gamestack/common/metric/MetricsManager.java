package fr.creart.gamestack.common.metric;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.misc.Configurable;
import fr.creart.gamestack.common.misc.Initialisable;
import fr.creart.gamestack.common.thread.ThreadsUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * This class manages metrics.
 * A metric is data which is propagated on the network every x millisecond.
 * You can obtain the single instance by calling the {@link #getInstance()} function.
 * Please first {@link #configure(byte, MetricOutput)} and then {@link #initialize()} the manager, and do not forget to set
 * the thread group of the soft ({@link ThreadsUtil#setCurrentGroup(String)} before even creating the current instance.
 *
 * @author Creart
 */
public class MetricsManager implements Initialisable, Configurable, AutoCloseable {

    private static final byte MAX_THREADS = 4;
    private static MetricsManager instance;

    private byte threads;
    private ScheduledExecutorService scheduler;
    private boolean initialized;
    private MetricOutput defaultOutput;

    private MetricsManager()
    {

    }

    /**
     * Configures the manager
     *
     * @param threads Max number of threads (can't be more than 4)
     */
    public void configure(byte threads, MetricOutput output)
    {
        if (initialized)
            return;
        this.threads = threads;
        Preconditions.checkNotNull(output, "output can't be null");
        this.defaultOutput = output;
    }

    public void registerProvider(MetricProvider provider)
    {
        Preconditions.checkNotNull(provider, "provider can't be null");
    }

    @Override
    public void initialize()
    {
        ThreadFactory factory = ThreadsUtil.createFactory("Metrics");
        scheduler = threads <= 1 ? Executors.newSingleThreadScheduledExecutor(factory)
                : Executors.newScheduledThreadPool(Math.min(MAX_THREADS, threads), factory);

        // finally
        initialized = true;
    }

    @Override
    public void close() throws Exception
    {
        scheduler.shutdownNow();
    }

    /**
     * Returns the current instance. Creates it if null.
     *
     * @return the current instance. Creates it if null.
     */
    public static MetricsManager getInstance()
    {
        if (instance == null)
            instance = new MetricsManager();
        return instance;
    }

}
