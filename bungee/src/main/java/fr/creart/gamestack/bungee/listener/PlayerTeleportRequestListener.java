/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.bungee.listener;

import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.packet.result.PlayerTeleport;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * @author Creart
 */
public class PlayerTeleportRequestListener implements PacketListener<PlayerTeleport> {

    @Override
    public void handlePacket(int packetId, PlayerTeleport result)
    {
        ProxyServer server = ProxyServer.getInstance();
        ServerInfo info = server.getServerInfo(result.getTargetServer());
        if (info != null) {
            Arrays.stream(result.getPlayerUUIDs()).map(uuid -> server.getPlayer(UUID.fromString(uuid))).filter(Objects::nonNull)
                    .forEach(player -> player.connect(info));
        }
    }

}
