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
