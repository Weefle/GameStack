/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A task ran every x seconds which collects data on the network
 * and propagates it. (It may do something else than propagating the data if you override the {@link #getChosenOutput()} function.)
 *
 * @author Creart
 */
public abstract class MetricProvider {

    private volatile long lastUpdate = System.currentTimeMillis();
    private final short updateFrequency;
    private AtomicBoolean lastUpdateFailed = new AtomicBoolean(false);

    public MetricProvider(short updateFrequency)
    {
        this.updateFrequency = updateFrequency;
    }

    /**
     * Returns the update frequency, which is the time difference between the last update and the current time
     *
     * @return the update frequency
     */
    final short getUpdateFrequency()
    {
        return updateFrequency;
    }

    /**
     * Returns the last time that the provider has provided a metric
     *
     * @return the last time that the provider has provided a metric
     */
    synchronized final long getLastUpdate()
    {
        return lastUpdate;
    }

    /**
     * Sets the last update to current time
     */
    synchronized final void renewLastUpdate()
    {
        lastUpdate = System.currentTimeMillis();
    }

    void setLastUpdateFailed(boolean lastUpdateFailed)
    {
        this.lastUpdateFailed.set(lastUpdateFailed);
    }

    public boolean hasLastUpdateFailed()
    {
        return lastUpdateFailed.get();
    }

    /**
     * Called each x seconds.
     * Returns the collected data
     *
     * @return the collected data
     */
    public abstract Metric provide();

    /**
     * Returns the alternative output of the current provider. <tt>null</tt> if it has none
     *
     * @return the alternative output of the current provider
     */
    public MetricOutput getChosenOutput()
    {
        return null;
    }

    /**
     * Returns <tt>true</tt> if the current provider has a different output than the default propagation on the network
     *
     * @return <tt>true</tt> if the current provider has a different output than the default propagation on the network
     */
    public final boolean hasCustomOutput()
    {
        return getChosenOutput() != null;
    }

    /**
     * Returns the metric name
     *
     * @return the metric name
     */
    public abstract String getMetricName();

    /**
     * Please describe the metric
     */
    @Override
    public abstract String toString();

}
