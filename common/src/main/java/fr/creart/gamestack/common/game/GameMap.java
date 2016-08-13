/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

import java.io.File;

/**
 * @author Creart
 */
public class GameMap {

    private int id;
    private String name;
    private float gamegrams;
    private File[] worldFolders; // may be only the "world" file, + optional "world_nether", "world_nether_end"

    public GameMap(int id, String name, float gamegrams, File[] worldFolders)
    {
        this.id = id;
        this.name = name;
        this.gamegrams = gamegrams;
        this.worldFolders = worldFolders;
    }

    public int getId()
    {
        return id;
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

}
