/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.rmq;

import fr.creart.gamestack.common.connection.ConnectionData;

/**
 * @author Creart
 */
public class RabbitConnectionData extends ConnectionData {

    private String virtualHost;

    /**
     * {@inheritDoc}
     *
     * @param virtualHost the rabbitmq virtual host
     */
    public RabbitConnectionData(String username, String password, String host, int port, String virtualHost)
    {
        super(username, password, host, port);
        this.virtualHost = virtualHost;
    }

    public String getVirtualHost()
    {
        return virtualHost;
    }

}
