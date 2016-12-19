/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

import fr.creart.gamestack.common.player.MessageType;
import fr.creart.gamestack.common.protocol.packet.MessagePacket;

/**
 * {@link MessagePacket}'s result.
 *
 * @author Creart
 */
public class MessageResult extends MultiPlayerData {

    private final String message;
    private final MessageType type;

    public MessageResult(MessageType type, String message, String... playerUUIDs)
    {
        super(playerUUIDs);
        this.type = type;
        this.message = message;
    }

    public MessageType getType()
    {
        return type;
    }

    public String getMessage()
    {
        return message;
    }

}
