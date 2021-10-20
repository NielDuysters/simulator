package be.kdg.sa.simulator.REST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author LukasVandenBossche on 20/10/2021
 * @project simulator
 */
@Configuration
public class RestConfiguration {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}


