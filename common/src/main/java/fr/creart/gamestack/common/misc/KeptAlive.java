/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.misc;

/**
 * Represents an object which can/has to be kept alive
 *
 * @author Creart
 */
public abstract class KeptAlive {

    private volatile long lastKeepAlive;

    /**
     * Keeps the instance alive
     */
    public final void keepAlive()
    {
        lastKeepAlive += getAliveDifference();
    }

    /**
     * Returns <tt>true</tt> if the current instance has timed out
     *
     * @return <tt>true</tt> if the current instance has timed out
     */
    public final boolean hasTimedOut()
    {
        return lastKeepAlive + getAliveDifference() < System.currentTimeMillis();
    }

    /**
     * Returns the difference between each keep alive until the instance has timed out
     *
     * @return the difference between each keep alive until the instance has timed out
     */
    protected abstract long getAliveDifference();

}
