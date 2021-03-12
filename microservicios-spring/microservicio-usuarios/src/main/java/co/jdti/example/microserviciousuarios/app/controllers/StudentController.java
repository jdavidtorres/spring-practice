package co.jdti.example.microserviciousuarios.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciousuarios.app.services.IStudentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class StudentController extends CommonController<StudentEntity, IStudentServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody StudentEntity studentEntity, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        Optional<StudentEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentEntity.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(studentEntity));
    }

    @GetMapping("/filter/{term}")
    public ResponseEntity<?> filterByNameOrLastname(@PathVariable String term) {
        return ResponseEntity.ok(iServices.findByNameOrLastname(term));
    }
}
