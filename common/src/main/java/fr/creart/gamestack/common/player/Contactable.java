/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.player;

/**
 * Represents one or more users which can be contacted
 *
 * @author Creart
 */
public interface Contactable {

    /**
     * Sends the messagePath associated to the given path followed with all the replace arguments.
     * <p>
     * Example:
     * <code>
     * contactableInstance.sendMessage("friend_accepted;" + granter.getName()", MessageType.CHAT);
     * </code>
     *
     * @param messagePath the path to the message to send
     * @param type        the messagePath's type
     */
    void sendMessage(String messagePath, MessageType type);

}
