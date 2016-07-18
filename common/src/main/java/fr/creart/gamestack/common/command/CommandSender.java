package fr.creart.gamestack.common.command;

/**
 * Represents the executor a command
 *
 * @author Creart
 */
public interface CommandSender {

    /**
     * Sends a message to the sender
     *
     * @param message   Message to send
     */
    void sendMessage(String message);

}
