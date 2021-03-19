package co.jdti.example.microserviciocursos.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EntityScan({"co.jdti.example.commons.exam.models.entities",
        "co.jdti.example.microserviciocursos.app.models.entities"})
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class CoursesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesApplication.class, args);
    }
}
