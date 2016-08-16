/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.game;

/**
 * Represents game statuses
 *
 * @author Creart
 */
public enum GameStatus {

    LOBBY((byte) 0),
    STARTING((byte) 1),
    STARTED((byte) 2),
    FINISHED((byte) 3);

    private byte id;

    GameStatus(byte id)
    {
        this.id = id;
    }

    public byte getId()
    {
        return id;
    }

    /**
     * Returns the GameStatus associated to the given id
     *
     * @param id game status' id
     * @return the GameStatus associated to the given id
     */
    public static GameStatus getById(int id)
    {
        for (GameStatus status : values())
            if (status.id == id)
                return status;
        return null;
    }

}
