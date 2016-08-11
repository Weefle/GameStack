/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.conf;

/**
 * @author Creart
 */
public final class ConfigurationConstants {

    public static final String BROKER_SYSTEM = "broker.system";
    public static final String BROKER_HOST = "broker.host";
    public static final String BROKER_PORT = "broker.port";
    public static final String BROKER_USERNAME = "broker.username";
    public static final String BROKER_PASSWORD = "broker.password";
    public static final String BROKER_VIRTUAL_HOST = "broker.virtual_host";

    public static final String DATABASE_SYSTEM = "database.system";
    public static final String DATABASE_HOST = "database.host";
    public static final String DATABASE_PORT = "database.port";
    public static final String DATABASE_USERNAME = "database.username";
    public static final String DATABASE_PASSWORD = "database.password";
    public static final String DATABASE_DB = "database.db";

    private ConfigurationConstants()
    {
        // no instance
    }

}
