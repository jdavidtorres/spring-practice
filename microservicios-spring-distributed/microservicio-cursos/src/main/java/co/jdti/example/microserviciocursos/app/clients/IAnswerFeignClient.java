package co.jdti.example.microserviciocursos.app.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas")
public interface IAnswerFeignClient {

    @GetMapping("/student/{studentId}/answered-exam")
    Iterable<Long> findExamsIdAnsweredByStudent(@PathVariable Long studentId);
}
