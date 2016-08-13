/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

import java.io.File;
import java.util.Map;

/**
 * Represents a game
 *
 * @author Creart
 */
public class Game {

    private final int id;
    private final String name;
    private final File[] requiredFiles;
    private final Map<Integer, GameMap> maps;

    /**
     * Default constructor
     *
     * @param id            game id
     * @param name          game name
     * @param requiredFiles game required files (assets)
     * @param maps          game maps
     */
    public Game(int id, String name, File[] requiredFiles, Map<Integer, GameMap> maps)
    {
        this.id = id;
        this.name = name;
        this.requiredFiles = requiredFiles;
        this.maps = maps;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public File[] getRequiredFiles()
    {
        return requiredFiles;
    }

    /**
     * Returns the <tt>GameMap</tt> associated to the given id
     *
     * @param mapId map's id
     * @return the <tt>GameMap</tt> associated to the given id
     */
    public GameMap getMap(int mapId)
    {
        return maps.get(mapId);
    }

}
