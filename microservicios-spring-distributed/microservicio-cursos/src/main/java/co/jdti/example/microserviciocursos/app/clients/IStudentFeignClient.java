package co.jdti.example.microserviciocursos.app.clients;

import co.jdti.example.commons.student.models.entities.StudentEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-usuarios")
public interface IStudentFeignClient {

    @GetMapping("/students-course")
    List<StudentEntity> getStudentsByCourse(@RequestParam List<Long> ids);
}
