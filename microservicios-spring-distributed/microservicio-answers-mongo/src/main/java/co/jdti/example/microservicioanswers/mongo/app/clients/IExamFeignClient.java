package co.jdti.example.microservicioanswers.mongo.app.clients;

import co.jdti.example.commons.exam.models.entities.ExamEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-examenes")
public interface IExamFeignClient {


    @GetMapping("/{id}")
    ExamEntity getExamById(@PathVariable Long id);
}
