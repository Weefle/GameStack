/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.command;

import fr.creart.gamestack.common.command.Command;
import fr.creart.gamestack.common.command.CommandSender;
import fr.creart.gamestack.server.StackServer;

/**
 * @author Creart
 */
public class StopCommand extends Command {

    /**
     * Constructor
     */
    public StopCommand()
    {
        super("stop", "Shuts GameStack down.");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        sender.sendMessage("The server is going to be shut down.");
        StackServer.getInstance().stop();
    }

}
