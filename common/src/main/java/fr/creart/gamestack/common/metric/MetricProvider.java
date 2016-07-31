package fr.creart.gamestack.common.metric;

/**
 * @author Creart
 */
public interface MetricProvider {

    Metric provide();

    default MetricOutput getChosenOutput()
    {
        return null;
    }

    default boolean hasCustomOutput()
    {
        return getChosenOutput() != null;
    }

}
