package fr.creart.gamestack.common.misc;

/**
 * Connection data (server's host, port, etc. + credentials)
 *
 * @author Creart
 */
public final class ConnectionData {

    private final String username;
    private final String password;
    private final String host;
    private int port;
    private String virtualHost;

    public ConnectionData(String username, String password, String host, int port)
    {
        this(username, password, host, port, "");
    }

    public ConnectionData(String username, String password, String host, int port, String virtualHost)
    {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public String getVirtualHost()
    {
        return virtualHost;
    }

}
