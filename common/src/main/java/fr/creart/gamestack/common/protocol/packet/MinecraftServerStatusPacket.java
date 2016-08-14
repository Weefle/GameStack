/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.log.CommonLogger;
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
        int statusId = source.readInt();
        Status status = Status.getById(statusId);
        MinecraftServerUpdate update = new MinecraftServerUpdate(source.readString(), source.readInt(), status);

        if (status == null) {
            CommonLogger.error("Received a Minecraft server status packet with an unrecognized mode (" + statusId + ")!");
            return null;
        }

        if (status == Status.ADD || status == Status.UPDATE) {
            update.setOnlinePlayers(source.readShort());
            update.setMaxPlayers(source.readShort());
        }

        return update;
    }

    @Override
    public void write(ByteArrayDataWriter writer, MinecraftServerUpdate data)
    {
        writer.write(data.getStatus().id);
        writer.write(data.getAddress());
        writer.write(data.getPort());
        if (data.getStatus() == Status.ADD || data.getStatus() == Status.UPDATE) {
            writer.write(data.getOnlinePlayers());
            writer.write(data.getMaxPlayers());
        }
    }

    /**
     * Represents a status update
     */
    public enum Status {

        ADD((byte) 0),
        UPDATE((byte) 1),
        DELETE((byte) 2);

        private byte id;

        Status(byte id)
        {
            this.id = id;
        }

        public byte getId()
        {
            return id;
        }

        /**
         * Returns the <tt>Status</tt> associated to the given id
         *
         * @param id status' id
         * @return the <tt>Status</tt> associated to the given id
         */
        public static Status getById(int id)
        {
            for (Status status : values())
                if (status.id == id)
                    return status;
            return null;
        }

    }

}
