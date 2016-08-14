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

/**
 * Represents data which is provided by a known host
 *
 * @author Creart
 */
public abstract class HostedData {

    private String address;

    /**
     * @param address host's address
     */
    public HostedData(String address)
    {
        this.address = address;
    }

    /**
     * Returns server's address
     *
     * @return server's address
     */
    public String getAddress()
    {
        return address;
    }

}
