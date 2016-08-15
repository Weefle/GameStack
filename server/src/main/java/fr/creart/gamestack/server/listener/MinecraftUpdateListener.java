/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.listener;

import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.packet.result.MinecraftServerUpdate;
import fr.creart.gamestack.server.StackServer;
import fr.creart.gamestack.server.server.HostServer;

/**
 * Listens for Minecraft updates
 *
 * @author Creart
 */
public class MinecraftUpdateListener implements PacketListener<MinecraftServerUpdate> {

    private StackServer server;

    {
        server = StackServer.getInstance();
    }

    @Override
    public void handlePacket(int packetId, MinecraftServerUpdate result)
    {
        HostServer host = server.getServer(result.getAddress());

        if (host == null) {
            CommonLogger.error("No host server for the address: " + result.getAddress() + ". Could not add Minecraft server.");
            return;
        }

        host.updateMinecraftServer(result.getPort(), result);
    }

}
