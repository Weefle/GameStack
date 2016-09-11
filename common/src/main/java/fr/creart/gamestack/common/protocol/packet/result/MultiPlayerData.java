/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

/**
 * Represents data which is bound to one or more players
 *
 * @author Creart
 */
public abstract class MultiPlayerData {

    protected final String[] playerUUIDs;

    public MultiPlayerData(String[] playerUUIDs)
    {
        this.playerUUIDs = playerUUIDs;
    }

    public String[] getPlayerUUIDs()
    {
        return playerUUIDs;
    }

}
