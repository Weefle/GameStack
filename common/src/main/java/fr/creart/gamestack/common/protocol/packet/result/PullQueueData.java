/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

/**
 * @author Creart
 */
public class PullQueueData extends MultiPlayerData {

    public PullQueueData(String[] playerUUIDs)
    {
        super(playerUUIDs);
    }

}
