/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

import com.google.common.base.Charsets;
import fr.creart.gamestack.common.misc.FileDependingInstance;
import fr.creart.protocolt.util.MoreArrays;

/**
 * @author Creart
 */
public class GameMap extends FileDependingInstance {

    private int id;
    private Game game;
    private String name;
    // gamegrams
    private float gg;
    // additional gamegrams per player
    private float ggpp;

    /**
     * Default constructor
     *
     * @param id            map's id
     * @param game          the game
     * @param name          map's name
     * @param gg            gamegrams
     * @param ggpp          gamegrams added for each player
     * @param requiredFiles the required files
     */
    public GameMap(int id, Game game, String name, float gg, float ggpp, String... requiredFiles)
    {
        super(requiredFiles);
        this.id = id;
        this.game = game;
        this.name = name;
        this.gg = gg;
        this.ggpp = ggpp;
    }

    /**
     * Returns current map's id
     *
     * @return current map's id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Returns current map's game
     *
     * @return current map's game
     */
    public Game getGame()
    {
        return game;
    }

    /**
     * Returns current map's name
     *
     * @return current map's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the needed amount of gamegrams for this game depending on the number of players
     *
     * @param players the number of players
     * @return the needed amount of gamegrams for this game depending on the number of players
     */
    public float calculateGamegrams(short players)
    {
        return players * ggpp + gg;
    }

    @Override
    public int hashCode()
    {
        return 31 * MoreArrays.sum(name.getBytes(Charsets.UTF_8));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof GameMap))
            return false;
        GameMap other = (GameMap) obj;
        return game.equals(other.game) && obj.hashCode() == hashCode();
    }

}
