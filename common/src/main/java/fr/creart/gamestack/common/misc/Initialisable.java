/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.misc;

/**
 * Represents an object which can be initialized
 *
 * @author Creart
 */
@FunctionalInterface
public interface Initialisable {

    /**
     * Initializes the object.
     */
    void initialize();

}
