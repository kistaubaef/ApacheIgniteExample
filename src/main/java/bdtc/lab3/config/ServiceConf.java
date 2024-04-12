package bdtc.lab3.config;

import bdtc.lab3.controller.TestServiceController;
import bdtc.lab3.dao.TestServiceRepository;
import bdtc.lab3.model.PersonEntity;
import bdtc.lab3.service.TestBusinessLogicService;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@Configuration
@Import(IgniteConf.class)
public class ServiceConf {
    /**
     * Initializes TestServiceRepository.
     * @param ignite          Ignite
     * @param personCacheConf Cache configuration for persons
     * @return TestServiceRepository
     */
    @Bean
    TestServiceRepository testServiceRepository(
            final Ignite ignite,
            final CacheConfiguration<UUID, PersonEntity> personCacheConf) {
        return new TestServiceRepository(ignite, personCacheConf);
    }

    /**
     * Initializes TestBusinessLogicService.
     * @param testServiceRepository TestServiceRepository
     * @return TestBusinessLogicService
     */
    @Bean
    TestBusinessLogicService testBusinessLogicService(
            final TestServiceRepository testServiceRepository) {
        return new TestBusinessLogicService(testServiceRepository);
    }

    /**
     * Initializes TestServiceController.
     * @param testBusinessLogicService TestBusinessLogicService
     * @return TestServiceController
     */
    @Bean
    TestServiceController testServiceController(
            final TestBusinessLogicService testBusinessLogicService) {
        return new TestServiceController(testBusinessLogicService);
    }
}
