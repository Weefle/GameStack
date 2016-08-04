package fr.creart.gamestack.common.connection.rmq;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.broking.AbstractBrokerManager;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;
import fr.creart.protocolt.data.DataResult;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * RabbitMQ implementation
 *
 * @author Creart
 */
public class RabbitContainer extends AbstractBrokerManager<Rabbit, RabbitConnectionData> {

    private Set<String> declaredExchanges = Sets.newConcurrentHashSet();
    private Set<String> listenedQueues = Sets.newConcurrentHashSet();

    public RabbitContainer(int threads)
    {
        super(threads);
        connection = new Rabbit(null, null);
    }

    @Override
    protected boolean connect(RabbitConnectionData connectionData)
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(connectionData.getHost());
        factory.setPort(connectionData.getPort());
        factory.setUsername(connectionData.getUsername());
        factory.setPassword(connectionData.getPassword());
        if (!Strings.isNullOrEmpty(connectionData.getVirtualHost()))
            factory.setVirtualHost(connectionData.getVirtualHost());
        factory.setThreadFactory(Commons.getThreadsManager().newThreadFactory("RabbitMQ"));

        if (connection.getConnection() == null || !connection.getConnection().isOpen())
            try {
                connection.setConnection(factory.newConnection());
                return true;
            } catch (Exception e) {
                CommonLogger.error("Failed connection to the RabbitMQ server.", e);
                return false;
            }

        // Creating the channel
        try {
            connection.setChannel(connection.getConnection().createChannel());
            return true;
        } catch (Exception e) {
            CommonLogger.fatal("Could not create RabbitMQ channel.", e);
            return false;
        }
    }

    @Override
    protected void end()
    {
        if (isConnectionEstablished())
            try {
                connection.close();
            } catch (Exception e) {
                CommonLogger.error("Encountered an exception during the shutdown of the RabbitMQ connection.", e);
            }
    }

    @Override
    protected <T> void doPublish(ByteArrayPacket<T> packet, T output)
    {
        super.publish(packet, output);

        ByteArrayDataWriter writer = new ByteArrayDataWriter();
        packet.write(writer, output);

        String chann = packetChannelName(packet.getId());
        if (!declaredExchanges.contains(chann))
            try {
                connection.getChannel().exchangeDeclare(chann, "fanout");
            } catch (Exception e) {
                CommonLogger.error("Could not declare exchange and because of that, publish a packet.", e);
                return;
            }

        enqueueTask((rabbit) -> rabbit.getChannel().basicPublish(chann, "", null, writer.result().toByteArray()));
    }

    @Override
    public void registerListener(int packetId, PacketListener listener)
    {
        super.registerListener(packetId, listener);

        String chann = packetChannelName(packetId);

        if (!declaredExchanges.contains(chann))
            try {
                connection.getChannel().exchangeDeclare(chann, "fanout");
                declaredExchanges.add(chann);
            } catch (Exception e) {
                CommonLogger.error("Could not declare exchange: " + chann + ".", e);
            }

        if (!listenedQueues.contains(chann))
            try {
                String queue = connection.getChannel().queueDeclare().getQueue();
                connection.getChannel().queueBind(queue, chann, "");
                connection.getChannel().basicConsume(queue, new RabbitConsumer(connection.getChannel(), ProtocolWrap.getPacketById(packetId)));
                listenedQueues.add(chann);
            } catch (Exception e) {
                CommonLogger.error("Could not create queue: " + chann + ".", e);
            }
    }

    @Override
    protected String getServiceName()
    {
        return "RabbitMQ";
    }

    @Override
    public boolean isConnectionEstablished()
    {
        return super.isConnectionEstablished() && connection.getConnection() != null && connection.getConnection().isOpen();
    }

    private class RabbitConsumer extends DefaultConsumer {

        private ByteArrayPacket<?> packet;

        RabbitConsumer(Channel channel, ByteArrayPacket<?> packet)
        {
            super(channel);
            this.packet = packet;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException
        {
            ByteArrayDataInput input = ByteStreams.newDataInput(body);
            ByteArrayDataSource source = new ByteArrayDataSource(input);
            DataResult<?> result = packet.read(source);
            Collection<PacketListener> found = listeners.get(packet.getId());
            if (found != null && !found.isEmpty())
                found.stream().forEach(listener -> listener.handlePacket(packet.getId(), result));
            source.release();
        }

    }

}
