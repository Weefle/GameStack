/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol;

import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.packet.EnqueuePacket;
import fr.creart.gamestack.common.protocol.packet.HostUpdatePacket;
import fr.creart.gamestack.common.protocol.packet.MessagePacket;
import fr.creart.gamestack.common.protocol.packet.MetricPacket;
import fr.creart.gamestack.common.protocol.packet.MinecraftServerStatusPacket;
import fr.creart.gamestack.common.protocol.packet.PlayerTeleportPacket;
import fr.creart.gamestack.common.protocol.packet.PullQueuePacket;
import fr.creart.protocolt.ProtoColt;
import fr.creart.protocolt.Protocol;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Wraps {@link fr.creart.protocolt.ProtoColt} library.
 *
 * @author Creart
 */
public final class ProtocolWrap {

    public static final short HOST_UPDATE_PACKET_ID = 0x01;
    public static final short MINECRAFT_SERVER_STATUS_PACKET_ID = 0x02;
    public static final short METRIC_PACKET_ID = 0x03;
    public static final short PLAYER_TELEPORT_PACKET_ID = 0x04;
    public static final short ENQUEUE_PACKET_ID = 0x05;
    public static final short CHAT_MESSAGE_PACKET_ID = 0x06;
    public static final short PULL_QUEUE_PACKET_ID = 0x07;

    private static Protocol protocol;

    static {
        ProtoColt.configure(true);
        ProtoColt proto = ProtoColt.getInstance();
        protocol = proto.getOrCreateProtocol("gamestack");

        // declare packets

        try {
            protocol.declarePacket(new HostUpdatePacket(HOST_UPDATE_PACKET_ID));
            protocol.declarePacket(new MinecraftServerStatusPacket(MINECRAFT_SERVER_STATUS_PACKET_ID));
            protocol.declarePacket(new MetricPacket(METRIC_PACKET_ID));
            protocol.declarePacket(new PlayerTeleportPacket(PLAYER_TELEPORT_PACKET_ID));
            protocol.declarePacket(new EnqueuePacket(ENQUEUE_PACKET_ID));
            protocol.declarePacket(new MessagePacket(CHAT_MESSAGE_PACKET_ID));
            protocol.declarePacket(new PullQueuePacket(PULL_QUEUE_PACKET_ID));
        } catch (Exception e) {
            CommonLogger.error("Could not declare a packet.", e);
        }
    }

    private ProtocolWrap()
    {
        // no instance
    }

    /**
     * Returns the packet associated to the id
     *
     * @param id id of the packet
     * @return the packet associated to the id
     */
    @SuppressWarnings("unchecked")
    public static <T extends ByteArrayPacket<?>> T getPacketById(int id)
    {
        return (T) protocol.getPacketById(id);
    }

    /**
     * Returns {@code true} if the packet has been declared
     *
     * @param packetId packet's id
     * @return {@code true} if the packet has been declared
     */
    public static boolean hasPacket(int packetId)
    {
        return protocol.getPacketById(packetId) != null;
    }

}
