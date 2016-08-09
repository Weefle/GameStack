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

package fr.creart.gamestack.common.connection;

import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Callback;

/**
 * A task which uses the connection of a container.
 *
 * @author Creart
 */
class ConnectionTask<T> implements Runnable {

    private final int taskId;
    private ConnectionTasksManager<T> tasksManager;
    private Callback<T> task;

    ConnectionTask(int taskId, Callback<T> task, ConnectionTasksManager<T> tasksManager)
    {
        this.taskId = taskId;
        this.task = task;
        this.tasksManager = tasksManager;
    }

    int getTaskId()
    {
        return taskId;
    }

    @Override
    public void run()
    {
        try {
            task.call(tasksManager.getContainer().getConnection());
        } catch (Exception e) {
            CommonLogger.error("An unhandled exception occurred during the execution of the task " + taskId + ".", e);
        }
    }

    @Override
    public int hashCode()
    {
        return taskId;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof ConnectionTask && obj.hashCode() == hashCode();
    }

}
