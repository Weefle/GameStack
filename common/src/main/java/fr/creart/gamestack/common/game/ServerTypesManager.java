/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

import fr.creart.gamestack.common.connection.database.Database;
import fr.creart.gamestack.common.connection.database.sql.SQLRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Creart
 */
public class ServerTypesManager {

    private Map<GameIdentifier, Game> games = new HashMap<>();

    public void loadGames(Database<SQLRequest> database)
    {
    }

    private class GameIdentifier {

        private short id;
        private String name;

        public GameIdentifier(short id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public short getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }

        @Override
        public int hashCode()
        {
            return id;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof GameIdentifier))
                return false;
            GameIdentifier other = (GameIdentifier) obj;
            return other.id == id || name.equals(other.name);
        }

    }

}
