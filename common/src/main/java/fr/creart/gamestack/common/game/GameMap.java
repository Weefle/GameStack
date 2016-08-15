/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

import com.google.common.base.Charsets;
import fr.creart.protocolt.util.MoreArrays;
import java.io.File;

/**
 * @author Creart
 */
public class GameMap {

    private int id;
    private Game game;
    private String name;
    private float gamegrams; // the weight of the game may depend on the map
    private File[] worldFolders; // may be only the "world" file, + optional "world_nether", "world_nether_end"

    public GameMap(int id, Game game, String name, float gamegrams, File[] worldFolders)
    {
        this.id = id;
        this.game = game;
        this.name = name;
        this.gamegrams = gamegrams;
        this.worldFolders = worldFolders;
    }

    public int getId()
    {
        return id;
    }

    public Game getGame()
    {
        return game;
    }

    public String getName()
    {
        return name;
    }

    public float getGamegrams()
    {
        return gamegrams;
    }

    public File[] getWorldFolders()
    {
        return worldFolders;
    }

    @Override
    public int hashCode()
    {
        return 31 * Float.floatToIntBits(gamegrams) * MoreArrays.sum(name.getBytes(Charsets.UTF_8));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof GameMap))
            return false;
        GameMap other = (GameMap) obj;
        return game.equals(other.game) && name.equals(other.name);
    }

}
