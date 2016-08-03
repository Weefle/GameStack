package fr.creart.gamestack.common.metric;

/**
 * Represents an object which contains a measured data
 * and which is going to be propagated on the network.
 *
 * @author Creart
 */
public abstract class Metric {

    protected MetricProvider provider;

    /**
     * @param provider source provider
     */
    public Metric(MetricProvider provider)
    {
        this.provider = provider;
    }

    public MetricProvider getProvider()
    {
        return provider;
    }

}
