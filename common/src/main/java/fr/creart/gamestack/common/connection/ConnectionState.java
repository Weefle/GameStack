/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection;

/**
 * Represents primary connection states
 *
 * @author Creart
 */
public enum ConnectionState {

    OPENING(false),
    OPENED(true),
    CLOSING(false),
    CLOSED(false);

    private boolean usable;

    ConnectionState(boolean usable)
    {
        this.usable = usable;
    }

    /**
     * Returns {@code true} if at this connection state, the connection is usable
     * @return {@code true} if at this connection state, the connection is usable
     */
    public boolean isUsable()
    {
        return usable;
    }

}
