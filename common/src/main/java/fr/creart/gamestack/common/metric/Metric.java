/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

import java.time.LocalDateTime;

/**
 * Represents an object which contains a measured data
 * and which is going to be propagated on the network.
 *
 * @author Creart
 */
public abstract class Metric implements Comparable<Metric> {

    protected transient MetricProvider provider;
    protected final LocalDateTime time;
    private transient final MetricPriority priority;

    /**
     * @param provider source provider
     * @param priority metric output priority
     */
    public Metric(MetricProvider provider, MetricPriority priority)
    {
        this(provider, priority, LocalDateTime.now());
    }

    /**
     * @param provider source provider
     * @param priority metric priority
     * @param when     time when the metric was measured
     */
    public Metric(MetricProvider provider, MetricPriority priority, LocalDateTime when)
    {
        this.provider = provider;
        this.priority = priority;
        this.time = when;
    }

    /**
     * Returns current metric's provider
     *
     * @return current metric's provider
     */
    public MetricProvider getProvider()
    {
        return provider;
    }

    /**
     * Returns current metric's priority. Higher priority metrics should be sent first
     *
     * @return current metric's priority
     */
    public MetricPriority getPriority()
    {
        return priority;
    }

    /**
     * Returns the moment when the metric was measured
     *
     * @return the moment when the metric was measured
     */
    public LocalDateTime getTime()
    {
        return time;
    }

    @Override
    public int compareTo(Metric o)
    {
        if (o.getPriority().getLevel() == priority.getLevel())
            return 0;
        return o.getPriority().getLevel() > priority.getLevel() ? 1 : -1;
    }

}
