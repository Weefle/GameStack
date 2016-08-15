/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

/**
 * Represents a status update
 *
 * @author Creart
 */
public enum KeepAliveStatus {

    ADD((byte) 0),
    UPDATE((byte) 1),
    DELETE((byte) 2);

    private byte id;

    KeepAliveStatus(byte id)
    {
        this.id = id;
    }

    public byte getId()
    {
        return id;
    }

    /**
     * Returns the <tt>status</tt> associated to the given id
     *
     * @param id status' id
     * @return the <tt>status</tt> associated to the given id
     */
    public static KeepAliveStatus getById(int id)
    {
        for (KeepAliveStatus status : values())
            if (status.id == id)
                return status;
        return null;
    }

}
