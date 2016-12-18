/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.protocol.packet.result.PullQueueData;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Packet sent by a BungeeCord instance when players should be removed from their queue.
 * Sent when:
 * <ul>
 * <li>A player disconnects.</li>
 * </ul>
 * <p>
 * If only one UUID is sent and the player is the leader, the whole party is removed; if he is not, only the given player will be removed.
 * <p>
 * So, in order to remove a party, you can either send all the UUIDs, or only the leader's UUID.
 *
 * @author Creart
 */
public class PullQueuePacket extends ByteArrayPacket<PullQueueData> {

    public PullQueuePacket(int id)
    {
        super(id);
    }

    @Override
    public PullQueueData read(ByteArrayDataSource src)
    {
        return new PullQueueData(Util.getUUIDs(src.readString()));
    }

    @Override
    public void write(ByteArrayDataWriter writer, PullQueueData data)
    {
        writer.write(Util.toStringFormat(data.getPlayerUUIDs()));
    }

}
