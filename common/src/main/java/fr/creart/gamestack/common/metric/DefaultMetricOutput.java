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
        if (metric == null)
            CommonLogger.warn("Tried to publish a null metric.");
        else
            broker.publish(packet, metric);
    }

}
