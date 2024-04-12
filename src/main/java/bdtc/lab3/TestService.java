package bdtc.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class TestService {
    public static void main(final String[] args) {
        SpringApplication.run(TestService.class, args);
    }
}
