package fr.creart.gamestack.common.command;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages commands.
 *
 * @author Creart
 */
public final class CommandsManager {

    // KEY = Command's label
    // VALUE = Command (execution)
    private static Map<String, Command> commands = new HashMap<>();

    static
    {
        registerCommand(new HelpCommand());
    }

    /**
     * Registers a new command
     *
     * @param command Command to register
     */
    public static void registerCommand(Command command)
    {
        Preconditions.checkNotNull(command, "command can't be null");
        Preconditions.checkNotNull(Strings.emptyToNull(command.getLabel()), "command label can't be null or empty");
        commands.put(command.getLabel().toLowerCase(), command);

        if (command.getAliases() == null || command.getAliases().length == 0)
            return;

        for (String alias : command.getAliases())
            commands.put(alias.toLowerCase(), command);
    }

    /**
     * Function to call when the command is executed
     *
     * @param line   Entered line by the user
     * @param sender Command's sender
     */
    public static void executeCommand(String line, CommandSender sender)
    {
        if (line == null || line.length() == 0)
            return;

        if (sender == null)
            sender = ConsoleSender.INSTANCE;

        String[] parts = line.split(" ");
        Command command = getCommandByName(parts[0]);

        if (command == null) {
            sender.sendMessage("Command " + parts[0] + " not found");
            return;
        }

        String[] args = new String[] {};
        System.arraycopy(parts, 1, args, 0, parts.length - 1);
        command.execute(sender, args);
    }

    /**
     * Returns registered commands
     *
     * @return registered commands
     */
    static Collection<Command> getCommands()
    {
        return commands.values();
    }

    /**
     * Returns the command associated to the specified name
     *
     * @param name  Name of the command
     * @return the command associated to the specified name
     */
    static Command getCommandByName(String name)
    {
        return commands.get(name.toLowerCase());
    }

}
