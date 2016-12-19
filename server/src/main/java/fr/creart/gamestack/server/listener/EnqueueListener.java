/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.listener;

import fr.creart.gamestack.common.player.MessageType;
import fr.creart.gamestack.common.player.Queueable;
import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.packet.result.EnqueueData;
import fr.creart.gamestack.server.StackServer;
import fr.creart.gamestack.server.util.Queueables;

/**
 * Handles the {@link fr.creart.gamestack.common.protocol.packet.EnqueuePacket}
 *
 * @author Creart
 */
public class EnqueueListener implements PacketListener<EnqueueData> {

    private StackServer instance;

    {
        instance = StackServer.getInstance();
    }

    @Override
    public void handlePacket(int packetId, EnqueueData result)
    {
        Queueable queueable = Queueables.fromUUIDArray(result.getPlayerUUIDs(), result.getPriority());

        if (instance.getQueuesManager().enqueueObject(queueable, result.getMap()))
            queueable.sendMessage("#enqueue-success", MessageType.CHAT_MESSAGE);
        else
            queueable.sendMessage("enqueue-fail", MessageType.CHAT_MESSAGE);
    }

}
