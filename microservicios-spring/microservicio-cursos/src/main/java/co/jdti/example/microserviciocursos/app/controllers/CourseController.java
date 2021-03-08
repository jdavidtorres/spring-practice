package co.jdti.example.microserviciocursos.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciocommons.models.entities.CourseEntity;
import co.jdti.example.microserviciocursos.app.services.ICourseServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CourseController extends CommonController<CourseEntity, ICourseServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody CourseEntity courseEntity, @PathVariable Long id) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseEntity.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(courseEntity));
    }
}
