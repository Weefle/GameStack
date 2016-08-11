/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

/**
 * Represents the priority of a metric
 *
 * @author Creart
 */
public enum MetricPriority {

    LOW((short) 1),
    NORMAL((short) 2),
    HIGH((short) 3);

    private short level;

    MetricPriority(short level)
    {
        this.level = level;
    }

    /**
     * Returns the level of importance of the priority. Higher the level is and more it is important
     *
     * @return the level of importance of the priority
     */
    public short getLevel()
    {
        return level;
    }

}
