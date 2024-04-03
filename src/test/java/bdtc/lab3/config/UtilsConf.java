package bdtc.lab3.config;


import bdtc.lab3.dao.TestServiceRepository;
import bdtc.lab3.utils.EntityUtils;
import org.apache.ignite.Ignite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UtilsConf {
    @Bean
    EntityUtils entityUtils(TestServiceRepository testServiceRepository, Ignite ignite){
        return new EntityUtils(testServiceRepository, ignite);
    }
}
