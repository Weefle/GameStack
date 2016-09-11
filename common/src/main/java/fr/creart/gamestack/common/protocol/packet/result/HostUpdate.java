/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

import fr.creart.gamestack.common.protocol.packet.HostUpdatePacket;

/**
 * Data class which contains information about a new server and represents an update (created on the reception of the
 * {@link HostUpdatePacket}).
 *
 * @author Creart
 */
public class HostUpdate extends HostedData {

    private final float capacity;
    private final float usedCapacity;

    /**
     * @param address      server's address
     * @param capacity     server's max capacity
     * @param usedCapacity server's used capacity
     */
    public HostUpdate(String address, float capacity, float usedCapacity)
    {
        super(address);
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
    }

    public float getCapacity()
    {
        return capacity;
    }

    public float getUsedCapacity()
    {
        return usedCapacity;
    }

}
