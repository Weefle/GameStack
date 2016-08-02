package fr.creart.gamestack.common.connection.database.sql;

import fr.creart.gamestack.common.connection.ConnectionData;

/**
 * @author Creart
 */
public class SQLConnectionData extends ConnectionData {

    private String database;

    public SQLConnectionData(String username, String password, String host, int port, String database)
    {
        super(username, password, host, port);
        this.database = database;
    }

    public String getDatabase()
    {
        return database;
    }

}
