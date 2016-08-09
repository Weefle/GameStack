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

import fr.creart.gamestack.common.misc.Callback;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Creart
 */
class ConnectionTasksManager<T> {

    private ExecutorService service;
    private ConnectionContainer<T, ?> container;
    private AtomicInteger taskIdCount = new AtomicInteger();

    ConnectionTasksManager(ExecutorService service, ConnectionContainer<T, ?> container)
    {
        this.service = service;
        this.container = container;
    }

    /**
     * Creates a task, enqueues it and returns the id of the created task
     *
     * @param call the task to call
     * @return the id of the created task
     */
    int enqueueTask(Callback<T> call)
    {
        ConnectionTask<T> task = new ConnectionTask<>(taskIdCount.incrementAndGet(), call, this);
        service.submit(task);
        return task.getTaskId();
    }

    ConnectionContainer<T, ?> getContainer()
    {
        return container;
    }

}
