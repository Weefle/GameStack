/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.player.MessageType;
import fr.creart.gamestack.common.protocol.packet.result.MessageResult;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Packet handled by the BungeeCord instances which allows to send a message to one or more players.
 *
 * @author Creart
 */
public class MessagePacket extends ByteArrayPacket<MessageResult> {

    public MessagePacket(int id)
    {
        super(id);
    }

    @Override
    public MessageResult read(ByteArrayDataSource src)
    {
        return new MessageResult(MessageType.fromId(src.readByte()), Util.getUUIDs(src.readString()), src.readString());
    }

    @Override
    public void write(ByteArrayDataWriter writer, MessageResult result)
    {
        writer.write(result.getType().getId());
        writer.write(Util.toStringFormat(result.getPlayerUUIDs()));
        writer.write(result.getMessage());
    }

}
