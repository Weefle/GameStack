package fr.creart.gamestack.common.command;

import com.google.common.base.Strings;
import java.util.Collection;

/**
 * Displays help for all commands or for the specified commands in the arguments.
 *
 * @author Creart
 */
public class HelpCommand extends Command {

    private static final String FIRST_LINE = "-~= Help =~-\n";

    HelpCommand()
    {
        super("help", "Displays help for one or all the commands", "?");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length == 0) {
            Collection<Command> commands = CommandsManager.getCommands();
            StringBuilder builder = new StringBuilder();
            builder.append(FIRST_LINE);
            commands.stream().forEach(command ->
                    builder.append("\t").append(command.getLabel()).append(": ").append(Strings.isNullOrEmpty(command.getHelp()) ?
                            "No help found for this command" :
                            command.getHelp()).append("\n"));
            sender.sendMessage(builder.toString());
        }

        else {
            Command command = CommandsManager.getCommandByName(args[0]);

            if (command == null) {
                sender.sendMessage("Help found for command \"" + args[0] + "\".");
                return;
            }

            sender.sendMessage("Help for \"" + args[0] + "\" command: " + command.getHelp());
        }
    }

}
