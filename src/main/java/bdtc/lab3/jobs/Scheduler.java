package bdtc.lab3.jobs;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.metric.ReadOnlyMetricRegistry;
import org.apache.ignite.spi.metric.jmx.JmxMetricExporterSpi;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is used to schedule the retrieval of Ignite metrics.
 */
@Component
public class Scheduler {
    private IgniteConfiguration configuration;

    /**
     * Creates a new instance of the Scheduler class.
     * @param configuration the Ignite configuration
     */
    public Scheduler(
            final IgniteConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Retrieves Ignite metrics every 10 seconds.
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void retrieveIgniteMetrics() {
        JmxMetricExporterSpi jmxSpi = (JmxMetricExporterSpi) configuration.getMetricExporterSpi()[0];
        ReadOnlyMetricRegistry ioReg = jmxSpi.getSpiContext().getOrCreateMetricRegistry("io.dataregion.default");

        System.out.println(
                "The total size of pages loaded to the RAM is " + ioReg.findMetric("PhysicalMemorySize").getAsString()
                        + " bytes");

    }

}
