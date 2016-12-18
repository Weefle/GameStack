/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.player;

/**
 * Represents different ways to contact players
 *
 * @author Creart
 */
public enum MessageType {

    ACTION_BAR((byte) 0),
    CHAT_MESSAGE((byte) 1),
    /**
     * Actually a subtitle
     */
    TITLE((byte) 2);

    private byte id;

    MessageType(byte id)
    {
        this.id = id;
    }

    public byte getId()
    {
        return id;
    }

    /**
     * Returns the message type associated to the given id
     *
     * @param id the given id
     * @return the message type associated to the given id
     */
    public static MessageType fromId(byte id)
    {
        for (MessageType type : values())
            if (type.id == id)
                return type;

        return null;
    }

}
