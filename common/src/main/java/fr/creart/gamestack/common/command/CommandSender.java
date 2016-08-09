/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.command;

/**
 * Represents the executor a command
 *
 * @author Creart
 */
public interface CommandSender {

    /**
     * Sends a message to the sender
     *
     * @param message   Message to send
     */
    void sendMessage(String message);

}
