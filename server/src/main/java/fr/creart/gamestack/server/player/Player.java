/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.player;

import com.google.common.base.Strings;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.gamestack.common.protocol.packet.PlayerTeleportPacket;
import fr.creart.gamestack.common.protocol.packet.result.PlayerTeleport;

/**
 * Represents a player connected to the network
 *
 * @author Creart
 */
public class Player implements Queueable {

    private static final PlayerTeleportPacket TELEPORT_PACKET = ProtocolWrap.getPacketById(0x04);

    private final String uuid;

    public Player(String uuid)
    {
        this.uuid = uuid;
    }

    /**
     * Returns player's uuid
     *
     * @return player's uuid
     */
    public String getUniqueId()
    {
        return uuid;
    }

    @Override
    public byte getWeight()
    {
        return 1;
    }

    @Override
    public void connect(String serverName)
    {
        if (Strings.isNullOrEmpty(serverName)) {
            CommonLogger.warn("Tried to teleport player (uuid=" + uuid + ") to an empty/null server name.");
            return;
        }

        Commons.getInstance().getMessageBroker().publish(TELEPORT_PACKET, new PlayerTeleport(serverName, uuid));
    }

    @Override
    public String toString()
    {
        return "Player[uuid=" + uuid + "]";
    }

    @Override
    public int hashCode()
    {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Player && hashCode() == obj.hashCode();
    }

}
