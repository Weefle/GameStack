package fr.creart.gamestack.common.connection;

/**
 * Connection data (server's host, port, etc. + credentials)
 *
 * @author Creart
 */
public class ConnectionData {

    private final String username;
    private final String password;
    private final String host;
    private int port;

    public ConnectionData(String username, String password, String host, int port)
    {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
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

}
