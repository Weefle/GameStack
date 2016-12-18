/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.util;

import fr.creart.gamestack.common.player.Party;
import fr.creart.gamestack.common.player.Player;
import fr.creart.gamestack.common.player.Queueable;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;

/**
 * Util class for {@link Queueable} objects
 *
 * @author Creart
 */
public final class Queueables {

    private Queueables()
    {
        // no instance
    }

    /**
     * Returns a {@link Queueable} object from the uuid array of a packet
     *
     * @param uuids    the UUIDs
     * @param priority priority
     * @return a {@link Queueable} object from the uuid array of a packet
     */
    public static Queueable fromUUIDArray(String[] uuids, byte priority)
    {
        Validate.notEmpty(uuids, "uuids can't be empty");

        return uuids.length == 1 ?
                new Player(uuids[0], priority) :
                new Party(new Player(uuids[0], priority),
                        Arrays.stream(Arrays.copyOfRange(uuids, 1, uuids.length - 1))
                                .map(uuid -> new Player(uuid, (byte) 0)).collect(Collectors.toList()).toArray(new Player[0]));
    }

}
