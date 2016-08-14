/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.protocol.packet.result.MinecraftServerUpdate;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Packet sent every second by Minecraft servers
 *
 * @author Creart
 */
public class MinecraftServerKeepAlivePacket extends ByteArrayPacket<MinecraftServerUpdate> {

    public MinecraftServerKeepAlivePacket(int id)
    {
        super(id);
    }

    @Override
    public MinecraftServerUpdate read(ByteArrayDataSource source)
    {
        return new MinecraftServerUpdate(source.readString(), source.readInt(), source.readShort(), source.readShort());
    }

    @Override
    public void write(ByteArrayDataWriter writer, MinecraftServerUpdate update)
    {
        writer.write(update.getHost());
        writer.write(update.getPort());
        writer.write(update.getOnlinePlayers());
        writer.write(update.getMaxPlayers());
    }

}
