package fr.creart.gamestack.common.connection.rmq;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.broking.AbstractBrokerManager;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.ConnectionData;
import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;
import fr.creart.protocolt.data.DataResult;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Creart
 */
public class RabbitContainer extends AbstractBrokerManager<Connection> {

    private Channel channel;
    private Set<String> declaredExchanges = new HashSet<>();
    private Set<String> listenedQueues = new HashSet<>();

    public RabbitContainer(int threads)
    {
        super(threads);
    }

    @Override
    protected void connect(ConnectionData connectionData)
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(connectionData.getHost());
        factory.setPort(connectionData.getPort());
        factory.setUsername(connectionData.getUsername());
        factory.setPassword(connectionData.getPassword());
        factory.setThreadFactory(Commons.getThreadsManager().newThreadFactory("RabbitMQ"));

        while (!connection.isOpen())
            try {
                connection = factory.newConnection();
            } catch (Exception e) {
                CommonLogger.error("Could not connect to RabbitMQ server, trying again in 5 seconds.");
                try {
                    Thread.sleep(5000L);
                } catch (Exception ex) {
                }
            }

        CommonLogger.info("Successfully connected to the RabbitMQ server.");

        // Creating the channel
        try {
            channel = connection.createChannel();
        } catch (Exception e) {
            CommonLogger.fatal("Could not create RabbitMQ channel.", e);
        }
    }

    @Override
    protected void end()
    {
        if (isEstablished())
            try {
                connection.close();

                if (channel.isOpen())
                    channel.close();
            } catch (Exception e) {
                CommonLogger.error("Encountered an exception during the shutdown of the RabbitMQ connection.", e);
            }
    }

    @Override
    public <T> void publish(ByteArrayPacket<T> packet, T output)
    {
        super.publish(packet, output);

        ByteArrayDataWriter writer = new ByteArrayDataWriter();
        packet.write(writer, output);
        // send to channel
        String chann = packetChannelName(packet.getId());
        if (!declaredExchanges.contains(chann))
            try {
                channel.exchangeDeclare(chann, "fanout");
            } catch (Exception e) {
                CommonLogger.error("Could not declare exchange and because of that, publish a packet.", e);
                return;
            }

        try {
            channel.basicPublish(chann, "", null, writer.result().toByteArray());
        } catch (Exception e) {
            CommonLogger.error("Failed to publish packet.", e);
        }
    }

    @Override
    public void registerListener(int packetId, PacketListener listener)
    {
        super.registerListener(packetId, listener);

        String chann = packetChannelName(packetId);
        boolean exchange = declaredExchanges.contains(chann);
        boolean listened = listenedQueues.contains(chann);
        if (!exchange || !listened)
            try {
                if (!exchange) {
                    channel.exchangeDeclare(chann, "fanout");
                    declaredExchanges.add(chann);
                }

                if (!listened) {
                    String queue = channel.queueDeclare().getQueue();
                    channel.queueBind(queue, chann, "");
                    channel.basicConsume(queue, new RabbitConsumer(channel, ProtocolWrap.getPacketById(packetId)));
                    listenedQueues.add(chann);
                }
            } catch (Exception e) {
                CommonLogger.error("Could not create a new channel: " + chann + ".", e);
            }
    }

    private String packetChannelName(int packetId)
    {
        return "packet" + Integer.toHexString(packetId);
    }

    private class RabbitConsumer extends DefaultConsumer {

        private ByteArrayPacket<?> packet;

        public RabbitConsumer(Channel channel, ByteArrayPacket<?> packet)
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
            RabbitContainer.this.listeners.get(packet.getId()).stream().forEach(listener ->
                    listener.handlePacket(packet.getId(), result));
            source.release();
        }

    }

}
