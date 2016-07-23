package fr.creart.gamestack.common.alerting;

import fr.creart.gamestack.common.alerting.AlertingManager.Priority;

/**
 * Represents a module which can alert the administrators/users of GameStack
 *
 * @author Creart
 */
public abstract class AlertingModule {

    /**
     * Alerts
     *
     * @param priority Priority of the message
     * @param message  Message to send
     */
    public abstract void alert(Priority priority, String message);

}
