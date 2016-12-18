/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.listener;

import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.packet.result.PullQueueData;
import fr.creart.gamestack.server.StackServer;
import fr.creart.gamestack.server.util.Queueables;

/**
 * @author Creart
 */
public class PullQueueListener implements PacketListener<PullQueueData> {

    private StackServer server;

    {
        server = StackServer.getInstance();
    }

    @Override
    public void handlePacket(int packetId, PullQueueData result)
    {
        server.getQueuesManager().dequeueObject(
                Queueables.fromUUIDArray(result.getPlayerUUIDs(), (byte) 0 /*we don't need the priority here, so we can just put a random value*/)
        );
    }

}
