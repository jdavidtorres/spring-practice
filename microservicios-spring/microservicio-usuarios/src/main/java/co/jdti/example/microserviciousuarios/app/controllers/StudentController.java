package co.jdti.example.microserviciousuarios.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciousuarios.app.models.entity.StudentEntity;
import co.jdti.example.microserviciousuarios.app.services.IStudentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StudentController extends CommonController<StudentEntity, IStudentServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody StudentEntity studentEntity, @PathVariable Long id) {
        Optional<StudentEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentEntity.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(studentEntity));
    }
}
