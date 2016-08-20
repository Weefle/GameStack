/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

import java.util.UUID;

/**
 * @author Creart
 */
public class PlayerTeleport {

    private UUID playersUniqueId;
    private String targetServer;

    public PlayerTeleport(UUID playersUniqueId, String targetServer)
    {
        this.playersUniqueId = playersUniqueId;
        this.targetServer = targetServer;
    }

    /**
     * Returns player's unique id
     *
     * @return player's unique id
     */
    public UUID getPlayersUniqueId()
    {
        return playersUniqueId;
    }

    /**
     * Returns the name of the server to which the player has to be teleported
     *
     * @return the name of the server to which the player has to be teleported
     */
    public String getTargetServer()
    {
        return targetServer;
    }

}
