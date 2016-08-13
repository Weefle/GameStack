/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.database.sql;

/**
 * MariaDB implementation
 *
 * @author Creart
 */
public class MariaDB extends SQLDatabase {

    /**
     * {@inheritDoc}
     */
    public MariaDB(int threads)
    {
        super(threads);
        databaseSystemName = "mariadb";
        driver = "org.mariadb.jdbc.Driver";
    }

    @Override
    protected String getServiceName()
    {
        return "MariaDB";
    }

}
