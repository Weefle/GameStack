package fr.creart.gamestack.common.misc;

import fr.creart.protocolt.data.DataResult;

/**
 * Represents a listener which listens for a packet.
 *
 * @author Creart
 */
@FunctionalInterface
public interface PacketListener {

    /**
     * Called as an event when a packet is received for which the current listener has been registered.
     *
     * @param packetId Packet's id
     * @param result   Received data
     */
    void handlePacket(int packetId, DataResult<?> result);

}
