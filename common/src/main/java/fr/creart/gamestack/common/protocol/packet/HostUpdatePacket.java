/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.protocol.packet.result.HostUpdate;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Packet sent every second by a host instance
 *
 * @author Creart
 */
public class HostUpdatePacket extends ByteArrayPacket<HostUpdate> {

    public HostUpdatePacket(int id)
    {
        super(id);
    }

    @Override
    public HostUpdate read(ByteArrayDataSource source)
    {
        return new HostUpdate(source.readString(), source.readFloat(), source.readFloat());
    }

    @Override
    public void write(ByteArrayDataWriter writer, HostUpdate update)
    {
        writer.write(update.getAddress());
        writer.write(update.getCapacity());
        writer.write(update.getUsedCapacity());
    }

}
