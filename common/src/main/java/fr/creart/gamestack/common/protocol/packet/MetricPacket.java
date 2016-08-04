package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.metric.Metric;
import fr.creart.gamestack.common.misc.JsonUtil;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;
import fr.creart.protocolt.data.DataResult;

/**
 * Metric packet propagated on the network
 *
 * @author Creart
 */
public class MetricPacket extends ByteArrayPacket<Metric> {

    public MetricPacket(int id)
    {
        super(id);
    }

    @Override
    public DataResult<Metric> read(ByteArrayDataSource src)
    {
        return new DataResult<>(JsonUtil.fromJson(Commons.getMetricsManager().getMetric(src.readString()), src.readString()));
    }

    @Override
    public void write(ByteArrayDataWriter writer, Metric metric)
    {
        writer.write(Commons.getMetricsManager().getMetricName(metric.getClass()));
        writer.write(JsonUtil.toJson(metric));
    }

}
