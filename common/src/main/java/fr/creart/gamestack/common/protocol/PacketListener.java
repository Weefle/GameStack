/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol;

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
