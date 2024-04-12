package bdtc.lab3.config;

import bdtc.lab3.jobs.Scheduler;
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
     * Initializes IgniteConfiguration.
     * @param discoverySpi DiscoverySpi
     * @return IgniteConfiguration
     */
    @Bean
    public IgniteConfiguration igniteConfiguration(final DiscoverySpi discoverySpi) {
        JmxMetricExporterSpi jmxSpi = new JmxMetricExporterSpi();
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
     * @param configuration IgniteConfiguration
     * @return Scheduler
     */
    @Bean
    Scheduler scheduler(
            final IgniteConfiguration configuration) {
        return new Scheduler(configuration);
    }

    /**
     * Initializes CacheConfiguration.
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
