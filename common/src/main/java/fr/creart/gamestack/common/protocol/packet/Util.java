/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Creart
 */
final class Util {

    private static final String UUID_SPLITTER = ";";

    private Util()
    {
        // no instance
    }

    static String[] getUUIDs(String uuids)
    {
        return uuids.split(UUID_SPLITTER);
    }

    static String toStringFormat(String[] uuids)
    {
        return StringUtils.join(uuids, UUID_SPLITTER);
    }

}
