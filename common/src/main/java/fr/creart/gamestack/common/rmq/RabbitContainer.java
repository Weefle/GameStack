package fr.creart.gamestack.common.rmq;

import com.rabbitmq.client.Connection;
import fr.creart.gamestack.common.connection.ConnectionContainer;

/**
 * @author Creart
 */
public class RabbitContainer extends ConnectionContainer<Connection> {

    public RabbitContainer(int threads)
    {
        super(threads);
    }

    @Override
    protected void connect()
    {

    }

    @Override
    protected void end()
    {

    }

}
