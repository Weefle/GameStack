/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.protocol.packet.result.ChatResult;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * @author Creart
 */
public class ChatMessagePacket extends ByteArrayPacket<ChatResult> {

    public ChatMessagePacket(int id)
    {
        super(id);
    }

    @Override
    public ChatResult read(ByteArrayDataSource src)
    {
        return new ChatResult(Util.getUUIDs(src.readString()), src.readString());
    }

    @Override
    public void write(ByteArrayDataWriter writer, ChatResult result)
    {
        writer.write(Util.toStringFormat(result.getPlayerUUIDs()));
        writer.write(result.getMessage());
    }

}
