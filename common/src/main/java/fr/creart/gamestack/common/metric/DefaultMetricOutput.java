package fr.creart.gamestack.common.metric;

import fr.creart.gamestack.common.broking.BrokerManager;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.gamestack.common.protocol.packet.MetricPacket;

/**
 * Default metric output.
 * It publishes the packet on the network.
 *
 * @author Creart
 */
public class DefaultMetricOutput implements MetricOutput {

    private MetricPacket packet;
    private BrokerManager broker;

    public DefaultMetricOutput(BrokerManager broker)
    {
        this.broker = broker;
        packet = ProtocolWrap.getPacketById(0xFE);
    }

    @Override
    public void send(Metric metric)
    {
        if (metric == null) {
            CommonLogger.warn("Tried to publish a null metric.");
            return;
        }

        broker.publish(packet, metric);
    }

}
