/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.broking;

import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;
import java.util.Arrays;

/**
 * Manages the message broker.
 *
 * @author Creart
 */
public interface BrokerManager {

    /**
     * Registers a listener which will be called each time a packet of the precised id will be received.
     *
     * @param packetId packet's id
     * @param listener packet listener
     */
    void registerListener(int packetId, PacketListener listener);

    /**
     * Registers multiple listeners.
     *
     * @param packetId  packet's id
     * @param listeners packet listener
     * @see #registerListener(int, PacketListener)
     */
    default void registerListeners(int packetId, PacketListener... listeners)
    {
        Arrays.stream(listeners).forEach(listener -> registerListener(packetId, listener));
    }

    /**
     * Publishes the given packet
     *
     * @param packet Packet
     * @param output Packet's data
     * @param <T>    Type of the output
     */
    <T> void publish(ByteArrayPacket<T> packet, T output);

}
