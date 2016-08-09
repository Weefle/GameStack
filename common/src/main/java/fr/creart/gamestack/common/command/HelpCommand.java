/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.command;

import com.google.common.base.Strings;

/**
 * Displays help for all commands or for the specified commands in the arguments.
 *
 * @author Creart
 */
public class HelpCommand extends Command {

    private static final String FIRST_LINE = "All GameStack commands:\n";

    HelpCommand()
    {
        super("help", "Displays help for one or all the commands", "?");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length == 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(FIRST_LINE);
            CommandsManager.getInstance().getCommands().stream().forEach(command ->
                    builder.append("\t").append(command.getLabel()).append(": ").append(Strings.isNullOrEmpty(command.getHelp()) ?
                            "No help found for this command" :
                            command.getHelp()).append("\n"));
            sender.sendMessage(builder.toString());
        }

        else {
            Command command = CommandsManager.getInstance().getCommandByName(args[0]);

            if (command == null) {
                sender.sendMessage("Help found for command \"" + args[0] + "\".");
                return;
            }

            sender.sendMessage("Help for \"" + args[0] + "\" command: " + command.getHelp());
        }
    }

}
