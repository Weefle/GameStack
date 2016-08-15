/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.packet.result.KeepAliveStatus;
import fr.creart.gamestack.common.protocol.packet.result.MinecraftServerUpdate;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Packet sent every seconds which have for purpose either:
 * <ul>
 * <li>add a new Minecraft Server;</li>
 * <li>or update an existing Minecraft Server;</li>
 * <li>or delete an existing Minecraft Server.</li>
 * </ul>
 *
 * @author Creart
 */
public class MinecraftServerStatusPacket extends ByteArrayPacket<MinecraftServerUpdate> {

    public MinecraftServerStatusPacket(int id)
    {
        super(id);
    }

    @Override
    public MinecraftServerUpdate read(ByteArrayDataSource source)
    {
        byte statusId = source.readByte();
        KeepAliveStatus status = KeepAliveStatus.getById(statusId);

        if (status == null) {
            CommonLogger.error("Received a Minecraft server status packet with an unrecognized mode (" + statusId + ")!");
            return null;
        }

        MinecraftServerUpdate update = new MinecraftServerUpdate(source.readString(), source.readString(), source.readInt(), status);

        if (status == KeepAliveStatus.ADD || status == KeepAliveStatus.UPDATE) {
            update.setOnlinePlayers(source.readShort());
            update.setMaxPlayers(source.readShort());
        }

        return update;
    }

    @Override
    public void write(ByteArrayDataWriter writer, MinecraftServerUpdate data)
    {
        writer.write(data.getStatus().getId());
        writer.write(data.getAddress());
        writer.write(data.getGameName());
        writer.write(data.getPort());
        if (data.getStatus() == KeepAliveStatus.ADD || data.getStatus() == KeepAliveStatus.UPDATE) {
            writer.write(data.getOnlinePlayers());
            writer.write(data.getMaxPlayers());
        }
    }

}
