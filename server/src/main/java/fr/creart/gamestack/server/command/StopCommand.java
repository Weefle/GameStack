package fr.creart.gamestack.server.command;

import fr.creart.gamestack.common.command.Command;
import fr.creart.gamestack.common.command.CommandSender;
import fr.creart.gamestack.server.StackServer;

/**
 * @author Creart
 */
public class StopCommand extends Command {

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
