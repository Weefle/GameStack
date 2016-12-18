/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.thread;

/**
 * Some threading utils
 *
 * @author Creart
 */
public class ThreadsUtil {

    private ThreadsUtil()
    {

    }

    /**
     * Sleeps the thread. If an {@link InterruptedException} is thrown it interrupts the thread.
     *
     * @param time Time to sleep
     */
    public static void sleep(long time)
    {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

}
