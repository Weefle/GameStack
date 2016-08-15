/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol;

import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.packet.HostUpdatePacket;
import fr.creart.gamestack.common.protocol.packet.MetricPacket;
import fr.creart.gamestack.common.protocol.packet.MinecraftServerStatusPacket;
import fr.creart.protocolt.ProtoColt;
import fr.creart.protocolt.Protocol;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Wraps {@link fr.creart.protocolt.ProtoColt} library.
 *
 * @author Creart
 */
public final class ProtocolWrap {

    private static Protocol protocol;

    static {
        ProtoColt.configure(true);
        ProtoColt proto = ProtoColt.getInstance();
        protocol = proto.getOrCreateProtocol("gamestack");

        // declare packets

        try {
            protocol.declarePacket(new HostUpdatePacket(0x01));
            protocol.declarePacket(new MinecraftServerStatusPacket(0x02));
            protocol.declarePacket(new MetricPacket(0x03));
        } catch (Exception e) {
            CommonLogger.error("Could not declare a packet.", e);
        }
    }

    private ProtocolWrap()
    {

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
