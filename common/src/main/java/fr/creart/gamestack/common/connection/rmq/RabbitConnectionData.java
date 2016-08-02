package fr.creart.gamestack.common.connection.rmq;

import fr.creart.gamestack.common.connection.ConnectionData;

/**
 * @author Creart
 */
public class RabbitConnectionData extends ConnectionData {

    private String virtualHost;

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
