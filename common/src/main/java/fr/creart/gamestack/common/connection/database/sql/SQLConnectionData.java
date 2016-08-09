/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
