package fr.creart.gamestack.common.metric;

/**
 * Represents an object which propagates {@link Metric}s on the network.
 *
 * @author Creart
 */
public interface MetricOutput {

    /**
     * Propagates the metric to the network
     *
     * @param metric Metric to send
     */
    void send(Metric metric);

}
