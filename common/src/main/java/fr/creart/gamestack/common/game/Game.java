/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

import fr.creart.gamestack.common.misc.FileDependingInstance;
import java.util.Map;

/**
 * Represents a game
 *
 * @author Creart
 */
public class Game extends FileDependingInstance {

    private static FileDependingInstance gameParent;

    static {
        // TODO: load the default required files for games
    }

    private final int id;
    private final String name;
    private final Map<Integer, GameMap> maps;

    /**
     * Default constructor
     *
     * @param id            game's id
     * @param name          game's name
     * @param maps          game's maps
     * @param requiredFiles game's required files
     */
    public Game(int id, String name, Map<Integer, GameMap> maps, String... requiredFiles)
    {
        this(id, name, requiredFiles, maps, gameParent);
    }

    /**
     * Constructor where the parent {@link FileDependingInstance} is explicit – which is {@link #gameParent} by default.
     *
     * @param id            game id
     * @param name          game name
     * @param requiredFiles game required files (assets)
     * @param maps          game maps
     * @param parent        the parent {@link FileDependingInstance}
     */
    public Game(int id, String name, String[] requiredFiles, Map<Integer, GameMap> maps, FileDependingInstance parent)
    {
        this.id = id;
        this.name = name;
        this.requiredFiles = requiredFiles;
        this.maps = maps;
        setParent(parent);
    }

    /**
     * Returns game's id
     *
     * @return game's id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Returns game's name
     *
     * @return game's name
     */
    public String getName()
    {
        return name;
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

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Game && obj.hashCode() == hashCode();
    }

    @Override
    public int hashCode()
    {
        return id;
    }

}
