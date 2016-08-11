/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.misc;

import fr.creart.gamestack.common.connection.database.sql.MariaDB;
import fr.creart.gamestack.common.connection.database.sql.MySQL;
import fr.creart.gamestack.common.connection.database.sql.PostgreSQL;
import fr.creart.gamestack.common.connection.rmq.RabbitContainer;
import fr.creart.protocolt.util.ReflectionUtil;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Creart
 */
public class DependsManager {

    private Map<String, Constructor<?>> associations = new HashMap<>();

    /**
     * Creates associations (name <=> constructor)
     */
    public void createAssociations()
    {
        associations.put("mariadb", ReflectionUtil.getConstructor(MariaDB.class, int.class));
        associations.put("mysql", ReflectionUtil.getConstructor(MySQL.class, int.class));
        associations.put("postgres", ReflectionUtil.getConstructor(PostgreSQL.class, int.class));
        associations.put("rabbitmq", ReflectionUtil.getConstructor(RabbitContainer.class, int.class));
        associations.put("redis", null /*@TODO*/);
    }

    /**
     * Returns an instance created from the constructor associated to the given name
     *
     * @param name system name
     * @param args arguments
     * @return an instance created from the constructor
     */
    public <T> T getAssociation(String name, Object... args)
    {
        try {
            return (T) associations.get(name.toLowerCase()).newInstance(args);
        } catch (Exception e) {
            return null;
        }
    }

}
