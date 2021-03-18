package co.jdti.example.microservicioanswers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EntityScan("co.jdti.example.microserviciocommons.models.entities")
@EnableEurekaClient
@SpringBootApplication
public class AnswersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnswersApplication.class, args);
    }
}
