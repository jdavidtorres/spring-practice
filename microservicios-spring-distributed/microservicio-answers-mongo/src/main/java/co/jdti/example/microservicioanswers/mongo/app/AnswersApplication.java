package co.jdti.example.microservicioanswers.mongo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AnswersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnswersApplication.class, args);
    }
}
