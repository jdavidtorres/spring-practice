package co.jdti.example.microserviciousuarios.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-cursos")
public interface ICourseFeignClient {

    @DeleteMapping("/delete-student/{id}")
    void deleteCourseStudentById(@PathVariable Long id);
}
