/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

/**
 * {@link fr.creart.gamestack.common.protocol.packet.ChatMessagePacket}'s result.
 *
 * @author Creart
 */
public class ChatResult extends MultiPlayerData {

    private final String message;

    public ChatResult(String[] playerUUIDs, String message)
    {
        super(playerUUIDs);
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

}
