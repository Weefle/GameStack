package fr.creart.gamestack.common.metric;

import fr.creart.gamestack.common.lang.BasicWrapper;
import fr.creart.gamestack.common.lang.Streams;
import fr.creart.gamestack.common.lang.Wrapper;
import fr.creart.gamestack.common.log.CommonLogger;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Creart
 */
public class MetricTask implements Runnable {

    // it is quite a lot, I may reduce it
    private static final short MAX_PROVIDER_RUN_TIME = 500;

    private MetricsManager manager;

    public MetricTask(MetricsManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void run()
    {
        final Wrapper<Long> runTime = new BasicWrapper<>(0L);

        Set<Metric> output = new HashSet<>();

        manager.getProviders().stream().filter(Streams.nonNullFilter()).forEach(provider -> {
            // approximately equals, a metric may have taken too much time to run.
            if (System.currentTimeMillis() - provider.getLastUpdate() <= 10 + runTime.get() + provider.getUpdateFrequency()) {
                long start = System.currentTimeMillis(); // it's all the process
                Thread thread = new Thread(manager.getMetricsGroup(), () -> output.add(provider.provide()), "Metric Task (" + provider.toString() + ")");
                thread.start();

                try {
                    thread.join(MAX_PROVIDER_RUN_TIME);
                } catch (InterruptedException e) {
                    CommonLogger.error(String.format("Interrupted execution on metric provider task (%s)!", provider.toString()), e);
                }

                // task takes too much time
                if (thread.isAlive()) {
                    thread.interrupt();
                    CommonLogger.warn("The metric provider (" + provider.toString() + ") took too much time to provide data (> 500ms)! Interrupted it.");
                    provider.setLastUpdateFailed(true);
                }
                // don't update the metric's last execution if it failed.
                else {
                    provider.renewLastUpdate();
                    provider.setLastUpdateFailed(false);
                }

                runTime.set(runTime.get() + System.currentTimeMillis() - start);
            }
        });

        output.stream().filter(Streams.nonNullFilter()).forEach(this::sendMetric);
    }

    private void sendMetric(Metric metric)
    {
        if (metric.getProvider().hasCustomOutput())
            metric.getProvider().getChosenOutput().send(metric);
        else
            manager.getDefaultOutput().send(metric);
    }

}
