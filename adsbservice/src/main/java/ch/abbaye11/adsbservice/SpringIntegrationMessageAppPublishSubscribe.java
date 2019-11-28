package ch.abbaye11.adsbservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.http.config.EnableIntegrationGraphController;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableIntegrationGraphController

public class SpringIntegrationMessageAppPublishSubscribe  {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationMessageAppPublishSubscribe.class);
    }

}
