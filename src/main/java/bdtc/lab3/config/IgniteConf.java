package bdtc.lab3.config;

import bdtc.lab3.job.Scheduler;
import bdtc.lab3.model.PersonEntity;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.metric.jmx.JmxMetricExporterSpi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;
import java.util.UUID;

@Configuration
public class IgniteConf {
    /**
     * Node instance name.
     */
    public static final UUID INSTANCE_NAME = UUID.randomUUID();

    /**
     * Local node addresses.
     */
    @Value("${testservice.node.localAddr:127.0.0.1}")
    private String localAddr;

    @Value("${testservice.node.ipFinderAddresses:dummy}")
    private String ipFinderAddresses;

    @Value("${testservice.node.clientMode:false}")
    private boolean clientMode;

    /**
     * Initializes TcpDiscoveryIpFinder.
     *
     * @return TcpDiscoveryIpFinder
     */
    @Bean
    public TcpDiscoveryIpFinder tcpDiscoveryIpFinder() {
        TcpDiscoveryMulticastIpFinder tcpDiscoveryIpFinder = new TcpDiscoveryMulticastIpFinder();
        tcpDiscoveryIpFinder.setLocalAddress(localAddr);
        if (!"dummy".equals(ipFinderAddresses)) {
            tcpDiscoveryIpFinder.setAddresses(Collections.singletonList(ipFinderAddresses));
        }

        return tcpDiscoveryIpFinder;
    }

    /**
     * Initializes DiscoverySpi.
     *
     * @param tcpDiscoveryIpFinder TcpDiscoveryIpFinder
     * @return DiscoverySpi
     */
    @Bean
    public DiscoverySpi discoverySpi(final TcpDiscoveryIpFinder tcpDiscoveryIpFinder) {
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalAddress(localAddr);
        discoverySpi.setIpFinder(tcpDiscoveryIpFinder);

        return discoverySpi;
    }

    /**
     * Initializes JMX metrics exporter.
     *
     * @return JMX metrics exporter
     */
    @Bean
    public JmxMetricExporterSpi metricExporterSpi() {
        return new JmxMetricExporterSpi();
    }

    /**
     * Initializes IgniteConfiguration.
     *
     * @param discoverySpi DiscoverySpi
     * @param jmxSpi       JMX metrics exporter
     * @return IgniteConfiguration
     */
    @Bean
    public IgniteConfiguration igniteConfiguration(final DiscoverySpi discoverySpi,
            final JmxMetricExporterSpi jmxSpi) {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName(INSTANCE_NAME.toString());
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setClientMode(clientMode);
        igniteConfiguration.setDiscoverySpi(discoverySpi);
        igniteConfiguration.setMetricExporterSpi(jmxSpi);
        return igniteConfiguration;
    }

    /**
     * Initializes ignite.
     *
     * @param configuration IgniteConfiguration
     * @return IgniteConfiguration
     */
    @Bean
    public Ignite ignite(final IgniteConfiguration configuration) {
        IgniteSpringBean igniteSpringBean = new IgniteSpringBean();
        igniteSpringBean.setConfiguration(configuration);
        return igniteSpringBean;
    }

    /**
     * Initializes Scheduler.
     *
     * @param jmxSpi JMX metrics exporter
     * @return Scheduler
     */
    @Bean
    Scheduler scheduler(
            final JmxMetricExporterSpi jmxSpi) {
        return new Scheduler(jmxSpi);
    }

    /**
     * Initializes CacheConfiguration.
     *
     * @return CacheConfiguration<UUID, PersonEntity>
     */
    @Bean
    public CacheConfiguration<UUID, PersonEntity> ignitePersonCacheConfiguration() {
        CacheConfiguration<UUID, PersonEntity> personCacheCfg = new CacheConfiguration<>();
        personCacheCfg.setName("person");
        personCacheCfg.setCacheMode(CacheMode.REPLICATED);
        personCacheCfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        personCacheCfg.setIndexedTypes(UUID.class, PersonEntity.class);

        return personCacheCfg;
    }
}
