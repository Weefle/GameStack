/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

/**
 * Represents an object which contains a measured data
 * and which is going to be propagated on the network.
 *
 * @author Creart
 */
public abstract class Metric {

    protected MetricProvider provider;

    /**
     * @param provider source provider
     */
    public Metric(MetricProvider provider)
    {
        this.provider = provider;
    }

    public MetricProvider getProvider()
    {
        return provider;
    }

}
