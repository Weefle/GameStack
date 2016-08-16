/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.metric;

import fr.creart.gamestack.common.lang.BasicWrapper;
import fr.creart.gamestack.common.lang.Wrapper;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

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

        Set<Metric> output = new TreeSet<>();

        manager.getProviders().stream().filter(Objects::nonNull).forEach(provider -> {
            // approximately equals, a metric may have taken too much time to run.
            if (System.currentTimeMillis() - provider.getLastUpdate() <= 10 + runTime.get() + provider.getUpdateFrequency()) {
                long start = System.currentTimeMillis(); // it's all the process
                Thread thread = new Thread(manager.getMetricsGroup(), () -> output.add(provider.provide()), "Metric Task (" + provider.toString() + ")");
                thread.start();

                try {
                    thread.join(MAX_PROVIDER_RUN_TIME);
                } catch (InterruptedException e) {
                    MetricsManager.LOGGER.error(String.format("Interrupted execution on metric provider task (%s)!", provider.toString()), e);
                }

                // task takes too much time
                if (thread.isAlive()) {
                    thread.interrupt();
                    MetricsManager.
                            LOGGER.warn("The metric provider (" + provider.toString() + ") took too much time to provide data (> 500ms)! Interrupted it.");
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

        output.stream().filter(Objects::nonNull).forEach(this::sendMetric);
    }

    private void sendMetric(Metric metric)
    {
        if (metric.getProvider().hasCustomOutput())
            try {
                metric.getProvider().getChosenOutput().output(metric);
            } catch (Exception e) {
                MetricsManager.LOGGER.error("Could not output the metric " + metric.getProvider().getMetricName() + " in the chosen output!", e);
            }
        else
            try {
                manager.getDefaultOutput().output(metric);
            } catch (Exception e) {
                MetricsManager.LOGGER.error("Could not output the metric " + metric.getProvider().getMetricName() + " in the "
                        + metric.getProvider().getChosenOutput().toString() + " output.", e);
            }
    }

}
