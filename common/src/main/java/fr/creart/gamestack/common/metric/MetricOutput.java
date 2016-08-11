/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

import java.io.Closeable;

/**
 * Represents an object which propagates {@link Metric}s on the network.
 *
 * @author Creart
 */
public interface MetricOutput extends Closeable {

    /**
     * Propagates the metric to the network
     *
     * @param metric Metric to send
     */
    void output(Metric metric) throws Exception;

    /**
     * Returns a textual representation of the metric output. (It may be its name, for instance.)
     *
     * @return Returns a textual representation of the metric output.
     */
    String toString();

}
