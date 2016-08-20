/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.conf;

/**
 * Exception thrown when a configuration file can't be loaded
 *
 * @author Creart
 */
public class ConfigurationLoadException extends Exception {

    /**
     * @param message exception message
     */
    public ConfigurationLoadException(String message)
    {
        super(message);
    }

}
