package fr.creart.gamestack.common.metric;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.misc.Configurable;
import fr.creart.gamestack.common.misc.Initialisable;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * This class manages metrics.
 * A metric is data which is propagated on the network every x millisecond.
 *
 * @author Creart
 */
public class MetricsManager implements Initialisable, Configurable, AutoCloseable {

    private static final byte MAX_THREADS = 4;

    private ThreadGroup metricsGroup;
    private byte threads;
    private ScheduledExecutorService scheduler;
    private volatile boolean initialized;
    private MetricOutput defaultOutput;
    private Set<MetricProvider> providers = Sets.newConcurrentHashSet();
    private ScheduledFuture<?> metricTask;

    public MetricsManager(MetricOutput output, byte threads)
    {
        this.defaultOutput = output;
        this.threads = threads;
    }

    synchronized Set<MetricProvider> getProviders()
    {
        return providers;
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

        metricsGroup = Commons.getThreadsManager().newThreadGroup("Metrics");

        ThreadFactory factory = Commons.getThreadsManager().newThreadFactory(metricsGroup);
        scheduler = threads <= 1 ? Executors.newSingleThreadScheduledExecutor(factory) :
                Executors.newScheduledThreadPool(Math.min(MAX_THREADS, threads), factory);

        metricTask = scheduler.scheduleAtFixedRate(new MetricTask(this), 2000L, 500L, TimeUnit.MILLISECONDS);

        // finally
        initialized = true;
    }

    @Override
    public void close() throws Exception
    {
        metricTask.cancel(false);
        scheduler.shutdownNow();
    }

    MetricOutput getDefaultOutput()
    {
        return defaultOutput;
    }

    ThreadGroup getMetricsGroup()
    {
        return metricsGroup;
    }

    private void checkInitializedState()
    {
        Preconditions.checkState(initialized, "not initialized");
    }

}
