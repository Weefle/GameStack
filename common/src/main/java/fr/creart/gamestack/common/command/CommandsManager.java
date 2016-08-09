package fr.creart.gamestack.common.command;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages commands.
 *
 * @author Creart
 */
public final class CommandsManager {

    private static CommandsManager instance;

    // KEY = Command's label
    // VALUE = Command (execution)
    private Map<String, Command> commands = new HashMap<>();

    private CommandsManager()
    {
        registerCommand(new HelpCommand());
    }

    /**
     * Registers a new command
     *
     * @param command Command to register
     */
    public void registerCommand(Command command)
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
    public void executeCommand(String line, CommandSender sender)
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

        String[] args = new String[]{};
        System.arraycopy(parts, 1, args, 0, parts.length - 1);
        command.execute(sender, args);
    }

    /**
     * Returns registered commands
     *
     * @return registered commands
     */
    Collection<Command> getCommands()
    {
        Set<Command> commands = new HashSet<>();
        commands.addAll(this.commands.values());
        return commands;
    }

    /**
     * Returns the command associated to the specified name
     *
     * @param name Name of the command
     * @return the command associated to the specified name
     */
    Command getCommandByName(String name)
    {
        return commands.get(name.toLowerCase());
    }

    /**
     * Returns the single instance of the commands manager
     *
     * @return the single instance of the commands manager
     */
    public static CommandsManager getInstance()
    {
        if (instance == null)
            instance = new CommandsManager();
        return instance;
    }

}
