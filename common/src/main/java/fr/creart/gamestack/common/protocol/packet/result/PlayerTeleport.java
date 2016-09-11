/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

/**
 * {@link fr.creart.gamestack.common.protocol.packet.PlayerTeleportPacket}'s result.
 *
 * @author Creart
 */
public class PlayerTeleport extends MultiPlayerData {

    private final String targetServer;

    public PlayerTeleport(String targetServer, String... playerUUIDs)
    {
        super(playerUUIDs);
        this.targetServer = targetServer;
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
