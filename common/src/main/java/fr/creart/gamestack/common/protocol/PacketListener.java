/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol;

/**
 * Represents a listener which listens for a packet.
 *
 * @param <T> the type of data result
 * @author Creart
 */
@FunctionalInterface
public interface PacketListener<T> {

    /**
     * Called as an event when a packet is received for which the current listener has been registered.
     *
     * @param packetId Packet's id
     * @param result   Received data
     */
    void handlePacket(int packetId, T result);

}
