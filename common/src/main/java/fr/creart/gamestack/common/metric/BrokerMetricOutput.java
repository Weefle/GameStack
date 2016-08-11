/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

import fr.creart.gamestack.common.broking.BrokerManager;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.gamestack.common.protocol.packet.MetricPacket;
import java.io.IOException;

/**
 * Default metric output.
 * It publishes the packet on the network.
 *
 * @author Creart
 */
public class BrokerMetricOutput implements MetricOutput {

    private MetricPacket packet;
    private BrokerManager broker;

    public BrokerMetricOutput(BrokerManager broker)
    {
        this.broker = broker;
        packet = ProtocolWrap.getPacketById(0xFE);
    }

    @Override
    public void output(Metric metric)
    {
        if (metric == null)
            CommonLogger.warn("Tried to publish a null metric.");
        else
            broker.publish(packet, metric);
    }

    @Override
    public void close() throws IOException
    {
        // nothing to do here, because the broker is closed with the commons
    }

    @Override
    public String toString()
    {
        return "Broker";
    }

}
