/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
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
import org.apache.log4j.Logger;

/**
 * This class manages metrics.
 * A metric is data which is propagated on the network every x millisecond.
 *
 * @author Creart
 */
public class MetricsManager implements Initialisable, Configurable, AutoCloseable {

    static final Logger LOGGER = Logger.getLogger("Metrics");

    private static final byte MAX_THREADS = 4;

    private ThreadGroup metricsGroup;
    private byte threads;
    private ScheduledExecutorService scheduler;
    private volatile boolean initialised;
    private MetricOutput defaultOutput;
    private Set<MetricProvider> providers = Sets.newConcurrentHashSet();
    private ScheduledFuture<?> metricTask;
    private BiMap<String, Class<? extends Metric>> metrics = HashBiMap.create();

    /**
     * @param output  metric output
     * @param threads number of allocated threads
     */
    public MetricsManager(MetricOutput output, byte threads)
    {
        this.defaultOutput = output;
        this.threads = threads;
    }

    /**
     * Returns the currently registered providers
     *
     * @return the currently registered providers
     */
    synchronized Set<MetricProvider> getProviders()
    {
        return providers;
    }

    /**
     * Registers a new provider
     *
     * @param provider The provider to register
     */
    public void registerProvider(MetricProvider provider)
    {
        checkInitialisedState();
        Preconditions.checkNotNull(provider, "provider can't be null");

        synchronized (this) {
            providers.add(provider);
        }
    }

    @Override
    public void initialise()
    {
        if (initialised)
            return;

        metricsGroup = Commons.getInstance().getThreadsManager().newThreadGroup("Metrics");

        ThreadFactory factory = Commons.getInstance().getThreadsManager().newThreadFactory(metricsGroup);
        scheduler = threads <= 1 ? Executors.newSingleThreadScheduledExecutor(factory) :
                Executors.newScheduledThreadPool(Math.min(MAX_THREADS, threads), factory);

        metricTask = scheduler.scheduleAtFixedRate(new MetricTask(this), 2000L, 500L, TimeUnit.MILLISECONDS);

        // finally
        initialised = true;
    }

    @Override
    public void close() throws Exception
    {
        metricTask.cancel(false);
        scheduler.shutdownNow();
    }

    /**
     * Registers a metric
     *
     * @param metricName metric's name
     * @param metric     metric's class
     */
    public void declareMetric(String metricName, Class<? extends Metric> metric)
    {
        metrics.put(metricName, metric);
    }

    /**
     * Returns the metric associated to the given name
     *
     * @param metricName Metric's name
     * @return the metric associated to the given name
     */
    public Class<? extends Metric> getMetric(String metricName)
    {
        return metrics.get(metricName);
    }

    /**
     * Returns the metric's name associated to the given class
     *
     * @param clazz metric's class
     * @return the metric's name associated to the given class
     */
    public String getMetricName(Class<? extends Metric> clazz)
    {
        return metrics.inverse().get(clazz);
    }

    /**
     * Returns the metric default output
     *
     * @return the metric default output
     */
    MetricOutput getDefaultOutput()
    {
        return defaultOutput;
    }

    /**
     * Returns the thread group used by the metrics system
     *
     * @return the thread group used by the metrics system
     */
    ThreadGroup getMetricsGroup()
    {
        return metricsGroup;
    }

    /**
     * Throws an {@link IllegalStateException} if the metrics manager hasn't been initialised
     */
    private void checkInitialisedState()
    {
        Preconditions.checkState(initialised, "not initialised");
    }

}
