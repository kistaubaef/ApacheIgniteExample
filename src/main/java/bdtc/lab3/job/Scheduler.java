package bdtc.lab3.job;
import org.apache.ignite.spi.metric.ReadOnlyMetricRegistry;
import org.apache.ignite.spi.metric.jmx.JmxMetricExporterSpi;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is used to schedule the retrieval of Ignite metrics.
 */
@Component
public class Scheduler {
    private JmxMetricExporterSpi jmxSpi;


    /**
     * Constructs Scheduler.
     * @param jmxSpi JMX metrics exporter
     */
    public Scheduler(
            final JmxMetricExporterSpi jmxSpi) {
        this.jmxSpi = jmxSpi;
    }

    /**
     * Retrieves Ignite metrics every 10 seconds.
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void retrieveIgniteMetrics() {
        ReadOnlyMetricRegistry ioReg = jmxSpi.getSpiContext().getOrCreateMetricRegistry("io.dataregion.default");
        System.out.println(
                "The total size of pages loaded to the RAM is " + ioReg.findMetric("PhysicalMemorySize").getAsString()
                        + " bytes");
    }


}
