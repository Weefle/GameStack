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

import java.net.InetSocketAddress;

/**
 * Represents data provided by a socket (with a known host and port)
 *
 * @author Creart
 */
public abstract class SocketHostData extends HostedData {

    private final int port;

    /**
     * @param address socket's address
     * @param port    socket's port
     */
    public SocketHostData(String address, int port)
    {
        super(address);
        this.port = port;
    }

    public int getPort()
    {
        return port;
    }

    public InetSocketAddress getSocketAddress()
    {
        return new InetSocketAddress(getAddress(), port);
    }

}
