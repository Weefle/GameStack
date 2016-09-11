/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.protocol.packet.result.EnqueueData;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * This packet is sent when a queueable item (composed of its players which are represented by their UUID)
 * should be enqueued to the given.
 * <p>
 * The player UUIDs are written in a string and separated by a semi-colon.
 *
 * @author Creart
 */
public class EnqueuePacket extends ByteArrayPacket<EnqueueData> {

    public EnqueuePacket(int id)
    {
        super(id);
    }

    @Override
    public EnqueueData read(ByteArrayDataSource src)
    {
        return new EnqueueData(Util.getUUIDs(src.readString()), src.readShort(), null);
        /*TODO: Create a manager from which it will be possible to retrieve the demanded map*/
    }

    @Override
    public void write(ByteArrayDataWriter writer, EnqueueData data)
    {
        writer.write(Util.toStringFormat(data.getPlayerUUIDs()));
        writer.write(data.getPriority());
        writer.write(data.getMap().getGame().getId());
        writer.write(data.getMap().getId());
    }

}
