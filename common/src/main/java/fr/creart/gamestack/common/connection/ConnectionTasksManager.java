/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection;

import fr.creart.gamestack.common.misc.Callback;
import fr.creart.gamestack.common.misc.Destroyable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Creart
 */
class ConnectionTasksManager<T> implements Destroyable {

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

    /**
     * Destroys the tasks manager
     */
    @Override
    public void destroy()
    {
        taskIdCount = null;
        try {
            container.getLogger().info("Shutting down " + getContainer().getServiceName() + "'s tasks");
            service.shutdown();
            if (!service.awaitTermination(2, TimeUnit.SECONDS)) {
                StringBuilder builder = new StringBuilder();
                builder.append("Could not await termination of all ").append(getContainer().getServiceName())
                        .append("tasks. The following tasks could not be terminated:\n");
                for (Runnable runnable : service.shutdownNow())
                    builder.append("\t- Class: ").append(runnable.getClass().getName());
                container.getLogger().error(builder.toString());
            }
        } catch (Exception e) {
            container.getLogger().error("Could not close thread pool.", e);
        }
    }

    ConnectionContainer<T, ?> getContainer()
    {
        return container;
    }

}
