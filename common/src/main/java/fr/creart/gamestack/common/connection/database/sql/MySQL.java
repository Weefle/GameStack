/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.database.sql;

/**
 * MySQL implementation
 *
 * @author Creart
 */
public class MySQL extends SQLDatabase {

    public MySQL(int threads)
    {
        super(threads);
        driver = "com.mysql.jdbc.Driver";
    }

    @Override
    protected String getServiceName()
    {
        return "MySQL";
    }

}
