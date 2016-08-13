/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.rmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fr.creart.gamestack.common.log.CommonLogger;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Contains RMQ's connection and channel.
 * I had to wrap up these two values in order to make the RMQ client compatible with my {@link fr.creart.gamestack.common.connection.ConnectionContainer}
 * system.
 *
 * @author Creart
 */
class Rabbit implements Closeable {

    private Connection connection;
    private Channel channel;

    Rabbit(Connection connection, Channel channel)
    {
        this.connection = connection;
        this.channel = channel;
    }

    Connection getConnection()
    {
        return connection;
    }

    Channel getChannel()
    {
        return channel;
    }

    void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    @Override
    public void close() throws IOException
    {
        if (channel.isOpen())
            try {
                channel.close();
            } catch (TimeoutException e) {
                CommonLogger.error("Connection with the RabbitMQ server timed out.", e);
            }

        if (connection.isOpen())
            connection.close();
    }

}
