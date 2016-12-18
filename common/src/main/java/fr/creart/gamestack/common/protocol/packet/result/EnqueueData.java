/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.common.protocol.packet.EnqueuePacket;

/**
 * {@link EnqueuePacket}'s result.
 *
 * @author Creart
 */
public class EnqueueData extends MultiPlayerData {

    private final byte priority;
    private final GameMap map;

    public EnqueueData(String[] playerUUIDs, byte priority, GameMap map)
    {
        super(playerUUIDs);
        this.priority = priority;
        this.map = map;
    }

    /**
     * Returns the priority of the queueable item. If the item is a party
     * the priority is the leader's one
     *
     * @return the priority of the queueable item.
     */
    public byte getPriority()
    {
        return priority;
    }

    /**
     * Returns the requested map.
     *
     * @return the requested map.
     */
    public GameMap getMap()
    {
        return map;
    }

}
