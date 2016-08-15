/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.listener;

import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.packet.result.HostUpdate;
import fr.creart.gamestack.server.StackServer;

/**
 * Listens for host updates
 *
 * @author Creart
 */
public class HostUpdateListener implements PacketListener<HostUpdate> {

    private StackServer server;

    {
        server = StackServer.getInstance();
    }

    @Override
    public void handlePacket(int packetId, HostUpdate result)
    {
        server.updateServer(result);
    }

}
