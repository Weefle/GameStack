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
