package fr.creart.gamestack.common.command;

import com.google.common.base.Strings;

/**
 * Represents a command executor from the console
 *
 * @author Creart
 */
public final class ConsoleSender implements CommandSender {

    private ConsoleSender()
    {

    }

    static final CommandSender INSTANCE = new ConsoleSender();

    @Override
    public void sendMessage(String message)
    {
        if (Strings.isNullOrEmpty(message))
            return;

        System.out.println("> ".concat(message));
    }

}
