/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

/**
 * {@link Runnable}-like class. Used for unsafe operations which can throw exceptions.
 *
 * @author Creart
 */
public interface UnsafeRunnable {

    /**
     * The unsafe operation
     *
     * @throws Exception
     */
    void run() throws Exception;

}
