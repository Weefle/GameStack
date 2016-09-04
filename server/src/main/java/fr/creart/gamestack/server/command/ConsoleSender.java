/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.command;

import com.google.common.base.Strings;

/**
 * Represents a command executor from the console
 *
 * @author Creart
 */
public final class ConsoleSender implements CommandSender {

    static final CommandSender INSTANCE = new ConsoleSender();

    private ConsoleSender()
    {

    }

    @Override
    public void sendMessage(String message)
    {
        if (Strings.isNullOrEmpty(message))
            return;

        System.out.println("> ".concat(message));
    }

}
