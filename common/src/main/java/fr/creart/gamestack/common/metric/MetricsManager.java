package fr.creart.gamestack.common.metric;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.misc.Configurable;
import fr.creart.gamestack.common.misc.Initialisable;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * This class manages metrics.
 * A metric is data which is propagated on the network every x millisecond.
 *
 * @author Creart
 */
public class MetricsManager implements Initialisable, Configurable, AutoCloseable {

    private static final byte MAX_THREADS = 4;

    private byte threads;
    private ScheduledExecutorService scheduler;
    private volatile boolean initialized;
    private MetricOutput defaultOutput;
    private Set<MetricProvider> providers = Sets.newConcurrentHashSet();

    public MetricsManager(MetricOutput output, byte threads)
    {
        this.defaultOutput = output;
        this.threads = threads;
    }

    public void registerProvider(MetricProvider provider)
    {
        checkInitializedState();
        Preconditions.checkNotNull(provider, "provider can't be null");

        synchronized (this) {
            providers.add(provider);
        }
    }

    @Override
    public void initialize()
    {
        if (initialized)
            return;

        ThreadFactory factory = Commons.getThreadsManager().newThreadFactory("Metrics");
        scheduler = threads <= 1 ? Executors.newSingleThreadScheduledExecutor(factory) :
                Executors.newScheduledThreadPool(Math.min(MAX_THREADS, threads), factory);

        // finally
        initialized = true;
    }

    @Override
    public void close() throws Exception
    {
        scheduler.shutdownNow();
    }

    private void checkInitializedState()
    {
        Preconditions.checkState(initialized, "not initialized");
    }

}
